package org.firstinspires.ftc.teamcode.RoverRuckus.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.hardware.camera.CameraName;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;
import org.firstinspires.ftc.robotcore.internal.system.AppUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.firstinspires.ftc.robotcore.external.navigation.AngleUnit.DEGREES;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesOrder.XYZ;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesReference.EXTRINSIC;
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
    private static final String autonomousVersionNumber = "7.0 - 2/1/19 ";

    // Initialize Motors, Servos, and Sensor Variables
    private hexChassisA chassis = new hexChassisA();
    //private andyMarkChassisA chassis = new andyMarkChassisA();

    private CRServo leftIntake;
    private CRServo rightIntake;
    private DcMotor intakeAngleMotor;
    private DcMotor winchMotor;

    public ColorSensor colorSensor;

    WebcamName webcamTensor;

    // Intake Encoder Variables
    protected int intakeDown = -1300;
    protected int intakeUp = 0;

    // Initialize Sampling Variables
    private tensorFlow sampling;
    private int goldLocation;

    // Initialize Vuforia Navigation Variables
    final String vuforia_key = key;
    CameraName webcamVuforia;
    private VuforiaLocalizer vuforia;
    int captureCounter = 0;
    File captureDirectory = AppUtil.ROBOT_DATA_DIR;
    private OpenGLMatrix lastLocation = null;
    private boolean targetVisible = false;
    private static final float mmPerInch = 25.4f;
    // width of the FTC field (from the center point to the outer panels)
        private static final float mmFTCFieldWidth = (12 * 6) * mmPerInch;
    // height of the center of the target image above the floor
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
     *
     * Sets chassis specific motor direction
     * <p> Tells motors to not apply brakes when power is 0
     */
    public void initializeRobot() {
        chassis.initializeMotors(hardwareMap);
        leftIntake = hardwareMap.crservo.get("leftIntake");
        rightIntake = hardwareMap.crservo.get("rightIntake");
        intakeAngleMotor = hardwareMap.dcMotor.get("intakeAngle");
        winchMotor = hardwareMap.dcMotor.get("winchMotor");

        intakeAngleMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        intakeAngleMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        webcamTensor = hardwareMap.get(WebcamName.class, "Webcam");
        webcamVuforia = hardwareMap.get(WebcamName.class, "Webcam");
    }

    public void initializeTensorFlow() {
        sampling = new tensorFlow(this);
        sampling.initialize();
    }

    /**
     * Allows robot to be programmed to go forward, backward a certain amount of inches
     * <p> Allows robot to be programmed to turn left and right a certain number of degrees
     * @param driveFB Inches to move forward or backward (forward: +, backward: -)
     * @param turnDegrees Degrees to turn left or right (right: +, left: -)
     * @param speed Speed of robot (min: 0, max: 1)
     * @param turning Whether robot is currently turning
     */
    private void encoderDrive(double driveFB, double turnDegrees, double speed,
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
     * {@linkplain /baseChassis#encoderDriveBasic(double, double, double, double, double, boolean) Documentation Here}
     */
    public void encoderDriveBasic(double leftFrontInches, double rightFrontInches,
                                  double leftBackInches, double rightBackInches, double speed) {
        // get function from chassis
        chassis.encoderDriveBasic(leftFrontInches, rightFrontInches, leftBackInches, rightBackInches,
                speed, opModeIsActive());
    }

    public void moveIntake(int newAngleMotorTarget) {

        if (opModeIsActive()) {
            intakeAngleMotor.setTargetPosition(newAngleMotorTarget);
            intakeAngleMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            intakeAngleMotor.setPower(0.75);
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

    public void expelMarker() {
        leftIntake.setPower(1);
        rightIntake.setPower(-1);

        sleep(2000);

        leftIntake.setPower(0);
        rightIntake.setPower(0);
    }

    /**
     * Winch Motor
     * <p> This is a simple function that controls the movement of the winch motor.
     * @param power This is the power given to the winch motor. Negative power means that the winch will
     *              retract, lifting the robot. Negative power means that the winch will lower, lowering the robot.
     * @param time This is the time, in milliseconds, that the motor needs to spin.
     */
    public void moveWinch(double power, long time) {
        winchMotor.setPower(power);
        sleep(time);
        winchMotor.setPower(0);
    }

    public void descend() {
        moveWinch(1, 5000);
        //timedForward(15, 0.2, 1500);
        //moveWinch(-1, 2000);
        moveWinch(-1, 1500);
    }

    public void scanMinerals() {
        goldLocation = sampling.scan(2);
        telemetry.addData("Gold Location", goldLocation);
        telemetry.update();
    }

    protected double primaryBase = 0;
    protected double secondaryBase = 0;
    protected double turnCorrection = 0;
    private double turnCorrectionTwo = 0;

    public void samplingDepot(boolean primary) {
        moveIntake(intakeDown);
        scanMinerals();

        if (goldLocation == 1) { // gold in left position
            left(23, 0.75);
            timedForward(38, 0.75, 3000);
            right(60, 0.75);
            timedForward(24, 0.75, 2000);
            primaryBase = 90; // base for 1
            double secondaryLeft = 0;
            turnCorrectionTwo = secondaryBase - secondaryLeft; // only for 2
        }
        if (goldLocation == 0 || goldLocation == 2) { // gold not detected or in center position
            timedForward(50,0.75, 3000);
            double primaryMiddle = 55;
            double secondaryMiddle = 55;
            turnCorrection = primaryBase - primaryMiddle;
            turnCorrectionTwo = secondaryBase - secondaryMiddle;
        }
        if (goldLocation == 3) { // gold in right position
            right(23, 0.75);
            timedForward(35, 0.75, 3000);
            left(55, 0.75);
            timedForward(22, 0.75, 2000);
            double primaryRight = 0;
            turnCorrection = primaryBase - primaryRight; // only for 1
            secondaryBase = 90; // base for 2
        }
        if (!primary) {
            turnCorrection = turnCorrectionTwo;
        }
    }

    protected boolean gotoDepot = false;

    public void samplingCrater(boolean primary) {
        moveIntake(intakeDown);
        scanMinerals();

        if (goldLocation == 1) { // gold in left position
            left(23, 0.75);
            timedForward(27, 0.75, 3000);
        }
        if (goldLocation == 0 || goldLocation == 2) { // gold not detected or in center position
            if (!primary) {
                gotoDepot = true;
            }
            timedForward(23, 0.75, 3000);
        }
        if (goldLocation == 3) { // gold in right position
            right(23, 0.75);
            timedForward(27, 0.75, 3000);
        }

        if (primary) {
            moveIntake(intakeUp);
        }
    }

    /* NEEDS to be REWRITTEN to support Webcam.. sorry ansh
     *
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

    public void vuforiaNavigation(final int camera_forward_displacement, final int camera_vertical_displacement, final int camera_left_displacement) {

        // Camera is 110 mm in front of robot center
        // Camera is 200 mm above ground
        // Camera is ON the robot's center line

        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);

        parameters.vuforiaLicenseKey = vuforia_key;
        parameters.cameraName = webcamTensor;

        vuforia = ClassFactory.getInstance().createVuforia(parameters);

        VuforiaTrackables targetsRoverRuckus = vuforia.loadTrackablesFromAsset("RoverRuckus");
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
        blueRover.setLocationFtcFieldFromTarget(blueRoverLocationOnField);

        OpenGLMatrix redFootprintLocationOnField = OpenGLMatrix
                .translation(0, -mmFTCFieldWidth, mmTargetHeight)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0, 180));
        redFootprint.setLocationFtcFieldFromTarget(redFootprintLocationOnField);

        OpenGLMatrix frontCratersLocationOnField = OpenGLMatrix
                .translation(-mmFTCFieldWidth, 0, mmTargetHeight)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0 , 90));
        frontCraters.setLocationFtcFieldFromTarget(frontCratersLocationOnField);

        OpenGLMatrix backSpaceLocationOnField = OpenGLMatrix
                .translation(mmFTCFieldWidth, 0, mmTargetHeight)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0, -90));
        backSpace.setLocationFtcFieldFromTarget(backSpaceLocationOnField);

        OpenGLMatrix robotFromCamera = OpenGLMatrix
                .translation(camera_forward_displacement, camera_left_displacement, camera_vertical_displacement)
                .multiplied(Orientation.getRotationMatrix(
                        AxesReference.EXTRINSIC, AxesOrder.XZY,
                        AngleUnit.DEGREES, 90, 90, 0));

        for (VuforiaTrackable trackable : allTrackables)
        {
            ((VuforiaTrackableDefaultListener)trackable.getListener()).setPhoneInformation(robotFromCamera, parameters.cameraDirection);
        }
    }

    public void DetectLocationWebcam() {
    //public List DetectLocationWebcam() {

        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);

        parameters.vuforiaLicenseKey = vuforia_key;
        parameters.cameraName = webcamTensor;

        vuforia = ClassFactory.getInstance().createVuforia(parameters);

        VuforiaTrackables targetsRoverRuckus = vuforia.loadTrackablesFromAsset("RoverRuckus");
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
        blueRover.setLocationFtcFieldFromTarget(blueRoverLocationOnField);

        OpenGLMatrix redFootprintLocationOnField = OpenGLMatrix
                .translation(0, -mmFTCFieldWidth, mmTargetHeight)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0, 180));
        redFootprint.setLocationFtcFieldFromTarget(redFootprintLocationOnField);

        OpenGLMatrix frontCratersLocationOnField = OpenGLMatrix
                .translation(-mmFTCFieldWidth, 0, mmTargetHeight)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0 , 90));
        frontCraters.setLocationFtcFieldFromTarget(frontCratersLocationOnField);

        OpenGLMatrix backSpaceLocationOnField = OpenGLMatrix
                .translation(mmFTCFieldWidth, 0, mmTargetHeight)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0, -90));
        backSpace.setLocationFtcFieldFromTarget(backSpaceLocationOnField);

        OpenGLMatrix robotFromCamera = OpenGLMatrix.translation(0,0,0).multiplied(Orientation.getRotationMatrix(AxesReference.EXTRINSIC, AxesOrder.XZY, AngleUnit.DEGREES, 90, 90, 0));

        for (VuforiaTrackable trackable : allTrackables) {
            ((VuforiaTrackableDefaultListener)trackable.getListener()).setPhoneInformation(robotFromCamera, parameters.cameraDirection);
        }

        List returnList = new ArrayList();

        targetsRoverRuckus.activate();
        while (opModeIsActive()) {

            targetVisible = false;
            for (VuforiaTrackable trackable : allTrackables) {
                if (((VuforiaTrackableDefaultListener)trackable.getListener()).isVisible()) {
                    telemetry.addData("Visible Target", trackable.getName());
                    targetVisible = true;

                    OpenGLMatrix robotLocationTransform = ((VuforiaTrackableDefaultListener)trackable.getListener()).getUpdatedRobotLocation();
                    if (robotLocationTransform != null) {
                        lastLocation = robotLocationTransform;
                    }
                    break;
                }
            }

            if (targetVisible) {

                VectorF translation = lastLocation.getTranslation();
                returnList.add(translation.get(0) / mmPerInch);
                returnList.add(translation.get(1) / mmPerInch);
                returnList.add(translation.get(2) / mmPerInch);
                telemetry.addData("Pos (in)", "{X, Y, Z} = %.1f, %.1f, %.1f", translation.get(0) / mmPerInch, translation.get(1) / mmPerInch, translation.get(2) / mmPerInch);

                Orientation rotation = Orientation.getOrientation(lastLocation, EXTRINSIC, XYZ, DEGREES);
                returnList.add(rotation.firstAngle);
                returnList.add(rotation.secondAngle);
                returnList.add(rotation.thirdAngle);
                telemetry.addData("Rot (deg)", "{Roll, Pitch, Heading} = %.0f, %.0f, %.0f", rotation.firstAngle, rotation.secondAngle, rotation.thirdAngle);
            }
            else {
                telemetry.addData("Visible Target", "none");
            }
            telemetry.update();

        }

        //return returnList;
    }

    public void MoveToLocationWebcam(final int desiredX, final int desiredY, final int desiredHeading) {

        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);

        parameters.vuforiaLicenseKey = vuforia_key;
        parameters.cameraName = webcamTensor;

        vuforia = ClassFactory.getInstance().createVuforia(parameters);

        VuforiaTrackables targetsRoverRuckus = vuforia.loadTrackablesFromAsset("RoverRuckus");
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
        blueRover.setLocationFtcFieldFromTarget(blueRoverLocationOnField);

        OpenGLMatrix redFootprintLocationOnField = OpenGLMatrix
                .translation(0, -mmFTCFieldWidth, mmTargetHeight)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0, 180));
        redFootprint.setLocationFtcFieldFromTarget(redFootprintLocationOnField);

        OpenGLMatrix frontCratersLocationOnField = OpenGLMatrix
                .translation(-mmFTCFieldWidth, 0, mmTargetHeight)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0 , 90));
        frontCraters.setLocationFtcFieldFromTarget(frontCratersLocationOnField);

        OpenGLMatrix backSpaceLocationOnField = OpenGLMatrix
                .translation(mmFTCFieldWidth, 0, mmTargetHeight)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0, -90));
        backSpace.setLocationFtcFieldFromTarget(backSpaceLocationOnField);

        OpenGLMatrix robotFromCamera = OpenGLMatrix.translation(0,0,0).multiplied(Orientation.getRotationMatrix(AxesReference.EXTRINSIC, AxesOrder.XZY, AngleUnit.DEGREES, 90, 90, 0));

        for (VuforiaTrackable trackable : allTrackables) {
            ((VuforiaTrackableDefaultListener)trackable.getListener()).setPhoneInformation(robotFromCamera, parameters.cameraDirection);
        }

        targetsRoverRuckus.activate();
        while (opModeIsActive()) {

            targetVisible = false;
            for (VuforiaTrackable trackable : allTrackables) {
                if (((VuforiaTrackableDefaultListener)trackable.getListener()).isVisible()) {
                    telemetry.addData("Visible Target", trackable.getName());
                    targetVisible = true;

                    OpenGLMatrix robotLocationTransform = ((VuforiaTrackableDefaultListener)trackable.getListener()).getUpdatedRobotLocation();
                    if (robotLocationTransform != null) {
                        lastLocation = robotLocationTransform;
                    }
                    break;
                }
            }

            double x1 = 0;
            double y1 = 0;
            double z1 = 0;

            if (targetVisible) {

                VectorF translation = lastLocation.getTranslation();
                x1 = translation.get(0) / mmPerInch;
                y1 = translation.get(1) / mmPerInch;
                telemetry.addData("Pos (in)", "{X, Y, Z} = %.1f, %.1f, %.1f", translation.get(0) / mmPerInch, translation.get(1) / mmPerInch, translation.get(2) / mmPerInch);

                Orientation rotation = Orientation.getOrientation(lastLocation, EXTRINSIC, XYZ, DEGREES);
                z1 = rotation.thirdAngle;
                telemetry.addData("Rot (deg)", "{Roll, Pitch, Heading} = %.0f, %.0f, %.0f", rotation.firstAngle, rotation.secondAngle, rotation.thirdAngle);
            }
            else {
                telemetry.addData("Visible Target", "none");
            }
            telemetry.update();

            int x2 = desiredX;
            int y2 = desiredY;
            int z2 = desiredHeading;

            double CG = Math.sqrt(Math.pow(x1-x2, 2) + Math.pow(y1-y2, 2));
            double theta = Math.asin((x1-x2)/CG);
            double firstTurn = z1+90-theta;
            double Straight = CG;
            double secondTurn = z2+90-theta;
            right(firstTurn,0.25);
            forward(Straight,0.3);
            left(secondTurn,0.25);

        }

    }

}