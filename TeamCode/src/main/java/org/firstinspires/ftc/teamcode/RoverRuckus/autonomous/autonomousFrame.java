package org.firstinspires.ftc.teamcode.RoverRuckus.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
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
 * <p> Contains all the common variables and functions
 * so that we can easily create organized autonomous programs
 * <p>
 * <p> Contributors: Jonathan Ma, Ansh Gandhi
 * <p> Current Version: {@value autonomousVersionNumber}
 *
 * <p> {@linkplain org.firstinspires.ftc.teamcode.RoverRuckus.autonomous.colorChecker Color Checker}
 *      used to detect minerals
 */

@Autonomous(name="Basic Autonomous Frame")
@Disabled
public abstract class autonomousFrame extends LinearOpMode {

    // VERSION NUMBER(MAJOR.MINOR) - DATE
    // DO BEFORE EVERY COMMIT!
    private static final String autonomousVersionNumber = "3.1 - 11/12/18 ";

    // Initialize Motors, Servos, and Sensor Variables
    private DcMotor motorLeftFront;
    private DcMotor motorRightFront;
    private DcMotor motorLeftBack;
    private DcMotor motorRightBack;

    public ColorSensor colorSensor;

    // Initialize Sampling Variables
    private ElapsedTime sampleTime = new ElapsedTime();
    private boolean samplingDone = false;

    // Initialize Encoder Drive Variables
    private final double counts_per_motor_rev = 1680 ;
    private final double robot_diameter = 30.0; //needs to change, robot not turning 90 anymore
    private final double drive_gear_reduction = 0.75;
    private final double wheel_diameter_inches = 4.0 ;
    private final double counts_per_inch = (counts_per_motor_rev * drive_gear_reduction) / (wheel_diameter_inches * Math.PI);
    private final double counts_per_degree = counts_per_inch * robot_diameter * Math.PI / 360;
    //cpi = 100, cpd needs to be 44.5

    // Initialize Vuforia Navigation Variables
    private static final String VUFORIA_KEY = key;
    private VuforiaLocalizer vuforia;
    private static final VuforiaLocalizer.CameraDirection CAMERA_CHOICE = BACK;

    private static final float mmPerInch        = 25.4f;
    private static final float mmFTCFieldWidth  = (12 * 6) * mmPerInch; // the width of the FTC field (from the center point to the outer panels)
    private static final float mmTargetHeight   = (6) * mmPerInch; // the height of the center of the target image above the floor

    /**
     * Prints version number of autonomous program
     * <p> Current Version: {@value autonomousVersionNumber}
     */
    public void versionPrint() {
        telemetry.addData("Autonomous Program Version", autonomousVersionNumber);
        telemetry.update();
    }

    /**
     * Initializes Hardware Map-
     * Maps motors, servos, and sensors to their names in the robot config file
     */
    public void initializeHardwareMap() {
        motorLeftFront = hardwareMap.dcMotor.get("motorLeftFront");
        motorRightFront = hardwareMap.dcMotor.get("motorRightFront");
        motorLeftBack = hardwareMap.dcMotor.get("motorLeftBack");
        motorRightBack = hardwareMap.dcMotor.get("motorRightBack");
        colorSensor = hardwareMap.colorSensor.get("colorSensor");
    }

    /**
     * Sets motor direction to forward and tells motors to not apply brakes when power is 0
     */
    public void setMotorDirection() {
        motorLeftFront.setDirection(DcMotor.Direction.FORWARD);
        motorRightFront.setDirection(DcMotor.Direction.FORWARD);
        motorLeftBack.setDirection(DcMotor.Direction.FORWARD);
        motorRightBack.setDirection(DcMotor.Direction.FORWARD);

        motorLeftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        motorLeftBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        motorRightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        motorRightBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
    }

    /**
     * Vuforia Navigation
     * <p> This contains some of the Vuforia initialization code. The function also calculates the location of the robot based on the the VuMarks detected.
     *
     * @param CAMERA_FORWARD_DISPLACEMENT This is the forward location of the phone relative to the center of the robot.
     * @param CAMERA_VERTICAL_DISPLACEMENT This is the vertical location of the phone relative to the center of the robot.
     * @param CAMERA_LEFT_DISPLACEMENT This is the left location of the phone relative to the center of the robot.
     */
    public void vuforiaNavigation(final int CAMERA_FORWARD_DISPLACEMENT, final int CAMERA_VERTICAL_DISPLACEMENT,
                                  final int CAMERA_LEFT_DISPLACEMENT) {

        // Camera is 110 mm in front of robot center
        // Camera is 200 mm above ground
        // Camera is ON the robot's center line

        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);

        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraDirection   = CAMERA_CHOICE;

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
                .translation(CAMERA_FORWARD_DISPLACEMENT, CAMERA_LEFT_DISPLACEMENT, CAMERA_VERTICAL_DISPLACEMENT)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, YZX, DEGREES,
                        CAMERA_CHOICE == BACK ? 90 : -90, 0, 0));

        for (VuforiaTrackable trackable : allTrackables)
        {
            ((VuforiaTrackableDefaultListener)trackable.getListener()).setPhoneInformation(phoneLocationOnRobot, parameters.cameraDirection);
        }
    }

    /**
     * Allows each individual motor to be programmed to go forward (+), backward (-) a certain amount of inches
     * @param leftFrontInches Inches to move motorLeftFront
     * @param rightFrontInches Inches to move motorRightFront
     * @param leftBackInches Inches to move motorLeftBack
     * @param rightBackInches Inches to move motorRightBack
     */
    public void encoderDriveBasic(double leftFrontInches, double rightFrontInches,
                                  double leftBackInches, double rightBackInches, double speed) {

        // Defines Target Position Variables
        int newLeftFrontTarget;
        int newRightFrontTarget;
        int newLeftBackTarget;
        int newRightBackTarget;

        // Ensure that the OpMode is still active
        if (opModeIsActive()) {

            // Calculates Target Position by Adding Current Position and Distance To Target Position
            newLeftFrontTarget = motorLeftFront.getCurrentPosition() + (int)(leftFrontInches * counts_per_inch);
            newRightFrontTarget = motorRightFront.getCurrentPosition() + (int)(rightFrontInches * counts_per_inch);
            newLeftBackTarget = motorLeftBack.getCurrentPosition() + (int)(leftBackInches * counts_per_inch);
            newRightBackTarget = motorRightBack.getCurrentPosition() + (int)(rightBackInches * counts_per_inch);

            // Sets Target Position for Motors
            motorLeftFront.setTargetPosition(newLeftFrontTarget);
            motorRightFront.setTargetPosition(newRightFrontTarget);
            motorLeftBack.setTargetPosition(newLeftBackTarget);
            motorRightBack.setTargetPosition(newRightBackTarget);

            // Changes Motor Mode So They Can Move to Target Position
            motorLeftFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            motorRightFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            motorLeftBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            motorRightBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            // Sets Power to Motors
            motorLeftFront.setPower(Math.abs(speed));
            motorRightFront.setPower(Math.abs(speed));
            motorLeftBack.setPower(Math.abs(speed));
            motorRightBack.setPower(Math.abs(speed));

            // Displays Target and Current Position When Active OpMode and Active Motor(s)
            while (opModeIsActive() && (motorLeftFront.isBusy() || motorRightFront.isBusy() ||
                   motorLeftBack.isBusy() || motorRightBack.isBusy())) {

                // Displays Target and Current Positions
                telemetry.addData("Target Value",  "Running to %7d :%7d :%7d :%7d",
                        newLeftFrontTarget,  newRightFrontTarget, newLeftBackTarget, newRightBackTarget);
                telemetry.addData("Current Value",  "Running at %7d :%7d: %7d :%7d",
                        motorLeftFront.getCurrentPosition(),
                        motorRightFront.getCurrentPosition(),
                        motorLeftBack.getCurrentPosition(),
                        motorRightBack.getCurrentPosition());
                telemetry.update();
            }

            // Stops Motors
            motorLeftFront.setPower(0);
            motorRightFront.setPower(0);
            motorLeftBack.setPower(0);
            motorRightBack.setPower(0);

            // Changes Motor Mode
            motorLeftFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            motorRightFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            motorLeftBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            motorRightBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
    }

    /**
     * Allows robot to be programmed to go forward, backward a certain amount of inches
     * <p> Allows robot to be programmed to turn left and right a certain number of degrees
     * @param driveFB Inches to move forward or backward (forward: +, backward: -)
     * @param turnDegrees Degrees to turn left or right (right: +, left: -)
     * @param speed Speed of robot (min: 0, max: 1)
     */
    public void encoderDrive(double driveFB, double turnDegrees, double speed) {

        // Defines Target Position Variables
        int newLeftFrontTarget;
        int newRightFrontTarget;
        int newLeftBackTarget;
        int newRightBackTarget;

        turnDegrees = turnDegrees * counts_per_degree / counts_per_inch;

        // Calculates Target Position
        double motorLeftFrontEncoder = (-driveFB - turnDegrees) * counts_per_inch;
        double motorRightFrontEncoder = (driveFB - turnDegrees) * counts_per_inch;
        double motorLeftBackEncoder = (-driveFB - turnDegrees) * counts_per_inch;
        double motorRightBackEncoder = (driveFB - turnDegrees) * counts_per_inch;

        if (opModeIsActive()) {

            // Calculates Target Position by Adding Current Position and Distance To Target Position
            newLeftFrontTarget = motorLeftFront.getCurrentPosition() + (int) (motorLeftFrontEncoder);
            newRightFrontTarget = motorRightFront.getCurrentPosition() + (int) (motorRightFrontEncoder);
            newLeftBackTarget = motorLeftBack.getCurrentPosition() + (int) (motorLeftBackEncoder);
            newRightBackTarget = motorRightBack.getCurrentPosition() + (int) (motorRightBackEncoder);

            // Sets Target Position for Motors
            motorLeftFront.setTargetPosition(newLeftFrontTarget);
            motorRightFront.setTargetPosition(newRightFrontTarget);
            motorLeftBack.setTargetPosition(newLeftBackTarget);
            motorRightBack.setTargetPosition(newRightBackTarget);

            // Changes Motor Mode So They Can Move to Target Position
            motorLeftFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            motorRightFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            motorLeftBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            motorRightBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            // Sets Power to Motors
            motorLeftFront.setPower(Math.abs(speed));
            motorRightFront.setPower(Math.abs(speed));
            motorLeftBack.setPower(Math.abs(speed));
            motorRightBack.setPower(Math.abs(speed));

            // Displays Target and Current Position When Active OpMode and Active Motor(s)
            while (opModeIsActive() && (motorLeftFront.isBusy() || motorRightFront.isBusy() ||
                   motorLeftBack.isBusy() || motorRightBack.isBusy())) {
                // telemetry for debug only
                /*// Displays Target and Current Positions
                telemetry.addData("Target Value", "Running to %7d :%7d :%7d :%7d",
                        newLeftFrontTarget, newRightFrontTarget, newLeftBackTarget, newRightBackTarget);
                telemetry.addData("Current Value", "Running at %7d :%7d: %7d :%7d",
                        motorLeftFront.getCurrentPosition(),
                        motorRightFront.getCurrentPosition(),
                        motorLeftBack.getCurrentPosition(),
                        motorRightBack.getCurrentPosition());
                telemetry.update();*/
            }

            // Stops Motors
            motorLeftFront.setPower(0);
            motorRightFront.setPower(0);
            motorLeftBack.setPower(0);
            motorRightBack.setPower(0);

            // Changes Motor Mode
            motorLeftFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            motorRightFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            motorLeftBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            motorRightBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
    }

    /**
     * Moves robot forward
     * @param distance Inches forward
     * @param speed Speed of robot
     */
    public void forward(double distance, double speed) {
        encoderDrive(distance, 0, speed);
    }

    /**
     * Moves robot backward
     * @param distance Inches backward
     * @param speed Speed of robot
     */
    public void backward(double distance, double speed) {
        encoderDrive(-distance, 0, speed);
    }

    /**
     * Turns robot left
     * @param degrees Degrees left
     * @param speed Speed of robot
     */
    public void left(double degrees, double speed) {
        encoderDrive(0, -degrees, speed);
    }

    /**
     * Turns robot right
     * @param degrees Degrees right
     * @param speed Speed of robot
     */
    public void right(double degrees, double speed) {
        encoderDrive(0, degrees, speed);
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