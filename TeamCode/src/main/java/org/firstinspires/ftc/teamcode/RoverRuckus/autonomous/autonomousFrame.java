package org.firstinspires.ftc.teamcode.RoverRuckus.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

import java.util.ArrayList;
import java.util.List;

import static org.firstinspires.ftc.robotcore.external.navigation.AngleUnit.DEGREES;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesOrder.XYZ;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesOrder.YZX;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesReference.EXTRINSIC;
import static org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer.CameraDirection.BACK;
import static org.firstinspires.ftc.teamcode.key.key;

/**
 * <h2>Autonomous Frame</h2>
 * Purpose:
 * <p> Contains all the common variables and functions in autonomous programs
 * so that they are neat and organized
 * <p>
 * <p> Current Version: {@value autonomousVersionNumber}
 */

@Autonomous(name="Basic Autonomous Frame")
@Disabled
public abstract class autonomousFrame extends LinearOpMode {

    // VERSION NUMBER(MAJOR.MINOR) - DATE
    // DO BEFORE EVERY COMMIT!
    private static final String autonomousVersionNumber = "4.5 - 12/9/18 ";

    // Initialize Motors, Servos, and Sensor Variables
    private hexChassis chassis = new hexChassis();
    //private andyMarkChassis chassis = new andyMarkChassis();

    private CRServo leftIntake;
    private CRServo rightIntake;
    private DcMotor intakeAngleMotor;

    public ColorSensor colorSensor;

    // Intake Encoder Variables
    protected int intakeDown = -1300;
    protected int intakeUp = 0;

    // Initialize Sampling Variables
    private ElapsedTime sampleTime = new ElapsedTime();
    private boolean samplingDone = false;

    // Initialize Vuforia Navigation Variables
    private static final String vuforia_key = key;
    private VuforiaLocalizer vuforia;
    private static final VuforiaLocalizer.CameraDirection camera_choice = BACK;

    private static final float mmPerInch = 25.4f;
    // the width of the FTC field (from the center point to the outer panels)
    private static final float mmFTCFieldWidth = (12 * 6) * mmPerInch;
    // the height of the center of the target image above the floor
    private static final float mmTargetHeight = (6) * mmPerInch;

    /**
     * Prints version number of autonomous program
     * <p> Current Version: {@value autonomousVersionNumber}
     */
    public void versionPrint() {
        telemetry.addData("Autonomous Program Version", autonomousVersionNumber);
        telemetry.update();
    }

    /**
     * Maps motors, servos, and sensors to their names in the robot config file
     */
    public void initializeHardwareMap() {
        chassis.initializeHardwareMap(hardwareMap);
        leftIntake = hardwareMap.crservo.get("leftIntake");
        rightIntake = hardwareMap.crservo.get("rightIntake");
        intakeAngleMotor = hardwareMap.dcMotor.get("intakeAngle");

        //colorSensor = hardwareMap.colorSensor.get("colorSensor");
    }

    /**
     * Sets chassis specific motor direction
     * <p> Tells motors to not apply brakes when power is 0
     */
    public void initializeMotors() {
        chassis.initializeMotors();
        intakeAngleMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        intakeAngleMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    public void expelMarker() {
        leftIntake.setPower(1);
        rightIntake.setPower(-1);

        sleep(2000);

        leftIntake.setPower(0);
        rightIntake.setPower(0);
    }

    public void moveIntake(int newAngleMotorTarget) {

        if (opModeIsActive()) {
            intakeAngleMotor.setTargetPosition(newAngleMotorTarget);
            intakeAngleMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            intakeAngleMotor.setPower(0.25);
            while (opModeIsActive() && intakeAngleMotor.isBusy()) {
                if ((intakeAngleMotor.getCurrentPosition()<= newAngleMotorTarget - 50) && (intakeAngleMotor.getCurrentPosition()>= -newAngleMotorTarget + 50)) {
                    newAngleMotorTarget = intakeAngleMotor.getCurrentPosition();
                }
                telemetry.addData("Target Value", newAngleMotorTarget);
                telemetry.addData("Current Value", intakeAngleMotor.getCurrentPosition());
                telemetry.update();
            }
            intakeAngleMotor.setPower(0);
            intakeAngleMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
    }

    /**
     * Vuforia Navigation
     * <p> This contains some of the Vuforia initialization code.
     * The function also calculates the location of the robot based on the the VuMarks detected.
     *
     * @param camera_forward_displacement
     *        This is the forward location of the phone relative to the center of the robot.
     * @param camera_vertical_displacement
     *        This is the vertical location of the phone relative to the center of the robot.
     * @param camera_left_displacement
     *        This is the left location of the phone relative to the center of the robot.
     */
    public void vuforiaNavigation(final int camera_forward_displacement, final int camera_vertical_displacement,
                                  final int camera_left_displacement) {

        // Camera is 110 mm in front of robot center
        // Camera is 200 mm above ground
        // Camera is ON the robot's center line

        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);

        parameters.vuforiaLicenseKey = vuforia_key;
        parameters.cameraDirection   = camera_choice;

        vuforia = ClassFactory.getInstance().createVuforia(parameters);

        VuforiaTrackables targetsRoverRuckus = this.vuforia.loadTrackablesFromAsset("RoverRuckus");
        VuforiaTrackable blueRover = targetsRoverRuckus.get(0);
        blueRover.setName("Blue-Rover");
        VuforiaTrackable redFootprint = targetsRoverRuckus.get(1);
        redFootprint.setName("Red-Footprint");
        VuforiaTrackable frontCraters = targetsRoverRuckus.get(2);
        frontCraters.setName("Front-Craters");
        VuforiaTrackable backSpace = targetsRoverRuckus.get(3);
        backSpace.setName("Back-Space");

        List<VuforiaTrackable> allTrackables = new ArrayList<VuforiaTrackable>();
        allTrackables.addAll(targetsRoverRuckus);

        OpenGLMatrix blueRoverLocationOnField = OpenGLMatrix
                .translation(0, mmFTCFieldWidth, mmTargetHeight)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0, 0));
        blueRover.setLocation(blueRoverLocationOnField);

        OpenGLMatrix redFootprintLocationOnField = OpenGLMatrix
                .translation(0, -mmFTCFieldWidth, mmTargetHeight)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0, 180));
        redFootprint.setLocation(redFootprintLocationOnField);

        OpenGLMatrix frontCratersLocationOnField = OpenGLMatrix
                .translation(-mmFTCFieldWidth, 0, mmTargetHeight)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0 , 90));
        frontCraters.setLocation(frontCratersLocationOnField);

        OpenGLMatrix backSpaceLocationOnField = OpenGLMatrix
                .translation(mmFTCFieldWidth, 0, mmTargetHeight)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0, -90));
        backSpace.setLocation(backSpaceLocationOnField);

        OpenGLMatrix phoneLocationOnRobot = OpenGLMatrix
                .translation(camera_forward_displacement, camera_left_displacement, camera_vertical_displacement)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, YZX, DEGREES,
                        camera_choice == BACK ? 90 : -90, 0, 0));

        for (VuforiaTrackable trackable : allTrackables)
        {
            ((VuforiaTrackableDefaultListener)trackable.getListener()).setPhoneInformation(phoneLocationOnRobot, parameters.cameraDirection);
        }
    }


    /**
     * {@linkplain /baseChassis#encoderDriveBasic(double, double, double, double, double, boolean) Documentation Here}
     */
    public void encoderDriveBasic(double leftFrontInches, double rightFrontInches,
                                  double leftBackInches, double rightBackInches, double speed) {
        // get function from chassis
        chassis.encoderDriveBasic(leftFrontInches, rightFrontInches, leftBackInches, rightBackInches,
                speed, opModeIsActive());
    }

    /**
     * Allows robot to be programmed to go forward, backward a certain amount of inches
     * <p> Allows robot to be programmed to turn left and right a certain number of degrees
     * @param driveFB Inches to move forward or backward (forward: +, backward: -)
     * @param turnDegrees Degrees to turn left or right (right: +, left: -)
     * @param speed Speed of robot (min: 0, max: 1)
     * @param turning Whether robot is currently turning
     */
    public void encoderDrive(double driveFB, double turnDegrees, double speed,
                             boolean turning, boolean timerRequested, int timerRequestTime) {

        // get function from current chassis
        chassis.encoderDrive(driveFB, turnDegrees, speed,
                turning, timerRequested, timerRequestTime,
                opModeIsActive(), this);
    }

    /**
     * Moves robot forward
     * @param distance Inches forward
     * @param speed Speed of robot
     */
    public void forward(double distance, double speed) {
        encoderDrive(distance, 0, speed, false, false, 0);
    }

    public void timedForward(double distance, double speed, int timeMilliseconds) {
        encoderDrive(distance, 0, speed, false, true, timeMilliseconds);
    }

    /**
     * Moves robot backward
     * @param distance Inches backward
     * @param speed Speed of robot
     */
    public void backward(double distance, double speed) {
        encoderDrive(-distance, 0, speed, false, false, 0);
    }

    public void timedBackward(double distance, double speed, int timeMilliseconds) {
        encoderDrive(-distance, 0, speed, false, true, timeMilliseconds);
    }

    /**
     * Turns robot left
     * @param degrees Degrees left
     * @param speed Speed of robot
     */
    public void left(double degrees, double speed) {
        encoderDrive(0, -degrees, speed, true, false, 0);
    }

    /**
     * Turns robot right
     * @param degrees Degrees right
     * @param speed Speed of robot
     */
    public void right(double degrees, double speed) {
        encoderDrive(0, degrees, speed, true, false, 0);
    }

    /**
     * Checks if the mineral is gold or silver during sampling
     * <ul>
     *     <li>If the mineral is gold, the robot will knock it and head to the depot</li>
     *     <li>If the mineral is silver, the robot will scan the next mineral</li>
     * </ul>
     * @param sampleId Tells robot which position the robot is currently checking (Left: 1, Center: 2, Right: 3)
     */
    private void sample(int sampleId) {

        sampleTime.reset();

        colorChecker samplingDetector = new colorChecker(this);

        while (sampleTime.milliseconds() <= 5000) {
            if (samplingDetector.detectObject() == 1) {
                samplingDone = true;
                telemetry.addData("Status", samplingDone);
                telemetry.update();
            }
            break;
        }

        if (samplingDone) {
            /* knock jewel here
            left(45, 0.5);
            forward(-3, 0.5);
            right(45, 0.5);
            */

            if (sampleId == 1) {
                forward(15,0.5);
            }
            else if (sampleId == 2) {
                forward(30,0.5);
            }
            else if (sampleId == 3) {
                forward(45,0.5);
            }
        }
    }

    /**
     * Preforms sampling-
     * <ul>
     *     <li>Robot scans left mineral</li>
     *     <li>If it is gold, it goes to the depot</li>
     *     <li>If it is not gold, it backs up to the next mineral to measure it (and so on)</li>
     * </ul>
     * <p> See {@linkplain #sample(int)} for more detail
     */
    public void sampling() {
        sample(1);

        telemetry.addData("Status", samplingDone);
        telemetry.update();

        if (!samplingDone){
            backward(-15,0.5);
            sample(2);

            telemetry.addData("Status", samplingDone);
            telemetry.update();

            if (!samplingDone)
                backward(-15,0.5);
                sample(3);

                telemetry.addData("Status", samplingDone);
                telemetry.update();
            }
    }

    public void oldCode() {
    /*// Initialize Jewel Variables
    private boolean detectJewel = false;
    float allianceColor;
    double distanceJewel;
    String displayJewel = "";
    private ElapsedTime jewelTime = new ElapsedTime();
    // Moves depending on relationship between jewel and alliance color,
    // detects color again if red/blue not detected
    public void reactToJewelDetect(double distance) {

        float colorValue = checkColor();
        jewelTime.reset();
        while (opModeIsActive() && detectJewel == false && jewelTime.milliseconds() <= 3000) {
            colorValue = checkColor();
            if (colorValue == 1) {
                displayJewel = "Red";
                detectJewel = true;

                if (colorValue == allianceColor) {
                    encoderDrive(0,0,0.2);
                    distanceJewel = distance;
                }
                else {
                    encoderDrive(0,0,0.2);
                    distanceJewel = -distance;
                }
            }
            if (colorValue == 2) {
                displayJewel = "Blue";
                detectJewel = true;

                if (colorValue == allianceColor) {
                    encoderDrive(0,0,0.2);
                    distanceJewel = distance;
                }
                else {
                    encoderDrive(0,0,0.2);
                    distanceJewel = -distance;
                }
            }
        }
        if (colorValue == 0) {
            displayJewel = "Unknown";
        }
        telemetry.addData("Color Identified:", displayJewel);
        telemetry.update();
    }*/
    }
}