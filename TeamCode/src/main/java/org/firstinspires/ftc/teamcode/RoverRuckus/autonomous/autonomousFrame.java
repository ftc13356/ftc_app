package org.firstinspires.ftc.teamcode.RoverRuckus.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
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

///////////////////////////////////////////////////////////////////////////////
// Purpose: Basic Autonomous Frame with Common Autonomous Functions
// Author: Ansh Gandhi, Jonathan Ma
///////////////////////////////////////////////////////////////////////////////

@Autonomous(name="Basic Autonomous Frame")
@Disabled
public abstract class autonomousFrame extends LinearOpMode {

    // VERSION NUMBER(MAJOR.MINOR) - DATE
    // DO BEFORE EVERY COMMIT!
    private final String autonomousVersionNumber = "2.0 - 10/12/18 ";

    // Initialize Hardware variables
    protected DcMotor motorLeftFront;
    protected DcMotor motorRightFront;
    protected DcMotor motorLeftBack;
    protected DcMotor motorRightBack;

    //protected ColorSensor colorSensor;

    // Initialize Drive Variables
    private final double counts_per_motor_rev = 1680 ;
    private final double robot_diameter = 30.0;
    private final double drive_gear_reduction = 0.75;
    private final double wheel_diameter_inches = 4.0 ;
    private final double counts_per_inch = (counts_per_motor_rev * drive_gear_reduction) / (wheel_diameter_inches * Math.PI);
    private final double counts_per_degree = counts_per_inch * robot_diameter * Math.PI / 360;

    //cpi = 100, cpd needs to be 44.5,

    /*// Initialize Jewel Variables
    private boolean detectJewel = false;
    float allianceColor;
    double distanceJewel;
    String displayJewel = "";
    private ElapsedTime jewelTime = new ElapsedTime();*/

    private static final String VUFORIA_KEY = key;

    private static final float mmPerInch        = 25.4f;
    private static final float mmFTCFieldWidth  = (12*6) * mmPerInch;       // the width of the FTC field (from the center point to the outer panels)
    private static final float mmTargetHeight   = (6) * mmPerInch;          // the height of the center of the target image above the floor

    private static final VuforiaLocalizer.CameraDirection CAMERA_CHOICE = BACK;

    private OpenGLMatrix lastLocation = null;
    private boolean targetVisible = false;

    private VuforiaLocalizer vuforia;

    // Initialize Hardware Map
    public void initializeHardwareMap() {
        motorLeftFront = hardwareMap.dcMotor.get("motorLeftFront");
        motorRightFront = hardwareMap.dcMotor.get("motorRightFront");
        motorLeftBack = hardwareMap.dcMotor.get("motorLeftBack");
        motorRightBack = hardwareMap.dcMotor.get("motorRightBack");
        //colorSensor = hardwareMap.colorSensor.get("colorSensor");
    }

    // Set Motor Direction
    public void setMotorDirection() {
        motorLeftFront.setDirection(DcMotor.Direction.FORWARD);
        motorRightFront.setDirection(DcMotor.Direction.FORWARD);
        motorLeftBack.setDirection(DcMotor.Direction.FORWARD);
        motorRightBack.setDirection(DcMotor.Direction.FORWARD);
    }

    // Print Version Number
    public void versionPrint() {
        telemetry.addData("Autonomous Program Version", autonomousVersionNumber);
        telemetry.update();
    }

    // Does Vuforia Navigation
    public void vuforiaNavigation(final int CAMERA_FORWARD_DISPLACEMENT, final int CAMERA_VERTICAL_DISPLACEMENT, final int CAMERA_LEFT_DISPLACEMENT) {
        // Camera is 110 mm in front of robot center
        // Camera is 200 mm above ground
        // Camera is ON the robot's center line

        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);

        parameters.vuforiaLicenseKey = VUFORIA_KEY ;
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

    // Combined Drive Function (Drivetrain, Encoder)
    public void encoderDrive(double driveFB, double turnDegrees, double speed) {

        // Defines Variables
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

        // Ensures OpMode is Active
        if (opModeIsActive()) {

            // Calculates Target Position Based on Current Position
            newLeftFrontTarget = motorLeftFront.getCurrentPosition() + (int) (motorLeftFrontEncoder);
            newRightFrontTarget = motorRightFront.getCurrentPosition() + (int) (motorRightFrontEncoder);
            newLeftBackTarget = motorLeftBack.getCurrentPosition() + (int) (motorLeftBackEncoder);
            newRightBackTarget = motorRightBack.getCurrentPosition() + (int) (motorRightBackEncoder);

            // Sets Target Position for Motors
            motorLeftFront.setTargetPosition(newLeftFrontTarget);
            motorRightFront.setTargetPosition(newRightFrontTarget);
            motorLeftBack.setTargetPosition(newLeftBackTarget);
            motorRightBack.setTargetPosition(newRightBackTarget);

            // Changes Motor Mode
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
            while (opModeIsActive() && (motorLeftFront.isBusy() || motorRightFront.isBusy() || motorLeftBack.isBusy() || motorRightBack.isBusy())) {

                // Displays Target and Current Positions
                telemetry.addData("Target Value", "Running to %7d :%7d :%7d :%7d", newLeftFrontTarget, newRightFrontTarget, newLeftBackTarget, newRightBackTarget);
                telemetry.addData("Current Value", "Running at %7d :%7d: %7d :%7d",
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

    // Individual Motor Drive Function (Drivetrain, Encoder)
    public void encoderDriveBasic(double speed, double leftFrontinches, double rightFrontinches,
                                  double leftBackinches, double rightBackinches) {

        // Defines Variables
        int newLeftfrontTarget;
        int newRightfrontTarget;
        int newLeftbackTarget;
        int newRightbackTarget;

        // Ensure that the OpMode is still active
        if (opModeIsActive()) {

            // Calculates Target Position Based on Current Position
            newLeftfrontTarget = motorLeftFront.getCurrentPosition() + (int)(leftFrontinches * counts_per_inch);
            newRightfrontTarget = motorRightFront.getCurrentPosition() + (int)(rightFrontinches * counts_per_inch);
            newLeftbackTarget = motorLeftBack.getCurrentPosition() + (int)(leftBackinches * counts_per_inch);
            newRightbackTarget = motorRightBack.getCurrentPosition() + (int)(rightBackinches * counts_per_inch);

            motorLeftFront.setTargetPosition(newLeftfrontTarget);
            motorRightFront.setTargetPosition(newRightfrontTarget);
            motorLeftBack.setTargetPosition(newLeftbackTarget);
            motorRightBack.setTargetPosition(newRightbackTarget);

            // Sets Target Position for Motors
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
            while (opModeIsActive() && (motorLeftFront.isBusy() || motorRightFront.isBusy() || motorLeftBack.isBusy() || motorRightBack.isBusy())) {

                // Displays Target and Current Positions
                telemetry.addData("Target Value",  "Running to %7d :%7d :%7d :%7d", newLeftfrontTarget,  newRightfrontTarget, newLeftbackTarget, newRightbackTarget);
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

    /*// Prints color (red/blue) and returns value
    public float checkColor() {

        float masterValue = 0;
        float hsvValues[] = {0F, 0F, 0F};
        Color.RGBToHSV((colorSensor.red()), (colorSensor.green()), (colorSensor.blue()), hsvValues);
        telemetry.addData("Hue", hsvValues[0]);
        if (hsvValues[0] >= 340 || hsvValues[0] <= 20) {
            masterValue = 1;
        }
        if (hsvValues[0] >= 200 && hsvValues[0] <= 275) {
            masterValue = 2;
        }

        telemetry.update();

        return masterValue;
    }

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