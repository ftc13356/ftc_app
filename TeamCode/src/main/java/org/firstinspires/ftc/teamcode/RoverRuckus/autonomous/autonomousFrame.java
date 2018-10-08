package org.firstinspires.ftc.teamcode.RoverRuckus.autonomous;

import android.graphics.Color;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
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
import static org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer.CameraDirection.FRONT;
import static org.firstinspires.ftc.teamcode.key.key;

///////////////////////////////////////////////////////////////////////////////
// Purpose: Basic Autonomous Frame
// Author: Ansh Gandhi, Jonathan Ma
///////////////////////////////////////////////////////////////////////////////

@Autonomous(name="18-19 Season Basic Autonomous Frame")
@Disabled
public abstract class autonomousFrame extends LinearOpMode {

    // VERSION NUMBER(MAJOR.MINOR) - DATE
    // DO BEFORE EVERY COMMIT!
    private final String autonomousVersionNumber = "1.0 - 10/7/18 ";

    // Initialize Hardware variables
    protected DcMotor motorLeftfront;
    protected DcMotor motorRightfront;
    protected DcMotor motorLeftback;
    protected DcMotor motorRightback;

    protected ColorSensor colorSensor;
    
    protected VuforiaTrackables relicTrackables;
    protected VuforiaTrackable relicTemplate;

    // Initialize Drive Variables
    private final double counts_per_motor_rev = 1680 ;
    private final double robot_diameter = 23.0;
    private final double drive_gear_reduction = 0.75;
    private final double wheel_diameter_inches = 4.0 ;
    private final double counts_per_inch = (counts_per_motor_rev * drive_gear_reduction) / (wheel_diameter_inches * Math.PI);
    private final double counts_per_degree = counts_per_inch * robot_diameter * Math.PI / 360;

    // Initialize Jewel Variables
    private boolean detectJewel = false;
    float allianceColor;
    double distanceJewel;
    String displayJewel = "";
    private ElapsedTime jewelTime = new ElapsedTime();

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
        motorLeftfront = hardwareMap.dcMotor.get("motorLeftfront");
        motorRightfront = hardwareMap.dcMotor.get("motorRightfront");
        motorLeftback = hardwareMap.dcMotor.get("motorLeftback");
        motorRightback = hardwareMap.dcMotor.get("motorRightback");
        colorSensor = hardwareMap.colorSensor.get("colorSensor");
    }

    // Set Motor Direction
    public void setMotorDirection() {
        motorLeftfront.setDirection(DcMotor.Direction.FORWARD);
        motorRightfront.setDirection(DcMotor.Direction.FORWARD);
        motorLeftback.setDirection(DcMotor.Direction.FORWARD);
        motorRightback.setDirection(DcMotor.Direction.FORWARD);
    }

    // Print Version Number
    public void versionPrint() {
        telemetry.addData("Autonomous Program Version", autonomousVersionNumber);
        telemetry.update();
    }

    // Vuforia Initialization
    public void initializeVuforia() {
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);
        parameters.vuforiaLicenseKey = key;
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;
        this.vuforia = ClassFactory.createVuforiaLocalizer(parameters);
        relicTrackables = this.vuforia.loadTrackablesFromAsset("RelicVuMark");
        relicTemplate = relicTrackables.get(0);
        relicTemplate.setName("relicVuMarkTemplate");
        telemetry.addData("Vuforia Status", "Initialized");
        telemetry.update();
    }

    public void blah() {
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

        final int CAMERA_FORWARD_DISPLACEMENT  = 0;   // eg: Camera is 110 mm in front of robot center
        final int CAMERA_VERTICAL_DISPLACEMENT = 0;   // eg: Camera is 200 mm above ground
        final int CAMERA_LEFT_DISPLACEMENT     = 0;     // eg: Camera is ON the robot's center line

        OpenGLMatrix phoneLocationOnRobot = OpenGLMatrix
                .translation(CAMERA_FORWARD_DISPLACEMENT, CAMERA_LEFT_DISPLACEMENT, CAMERA_VERTICAL_DISPLACEMENT)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, YZX, DEGREES,
                        CAMERA_CHOICE == FRONT ? 90 : -90, 0, 0));

        for (VuforiaTrackable trackable : allTrackables)
        {
            ((VuforiaTrackableDefaultListener)trackable.getListener()).setPhoneInformation(phoneLocationOnRobot, parameters.cameraDirection);
        }
    }

    // Combined Drive Function (Drivetrain, Encoder)
    public void encoderDrive(double driveFB, double driveS, double turnDegrees, double speed) {

        // Defines Variables
        int newLeftfrontTarget;
        int newRightfrontTarget;
        int newLeftbackTarget;
        int newRightbackTarget;

        turnDegrees = turnDegrees * counts_per_degree / counts_per_inch;

        // Calculates Target Position
        double motorLeftfrontEncoder = (-driveFB + driveS + turnDegrees) * counts_per_inch;
        double motorRightfrontEncoder = (driveFB + driveS + turnDegrees) * counts_per_inch;
        double motorLeftbackEncoder = (-driveFB - driveS + turnDegrees) * counts_per_inch;
        double motorRightbackEncoder = (driveFB - driveS + turnDegrees) * counts_per_inch;

        // Ensures OpMode is Active
        if (opModeIsActive()) {

            // Calculates Target Position Based on Current Position
            newLeftfrontTarget = motorLeftfront.getCurrentPosition() + (int) (motorLeftfrontEncoder);
            newRightfrontTarget = motorRightfront.getCurrentPosition() + (int) (motorRightfrontEncoder);
            newLeftbackTarget = motorLeftback.getCurrentPosition() + (int) (motorLeftbackEncoder);
            newRightbackTarget = motorRightback.getCurrentPosition() + (int) (motorRightbackEncoder);

            // Sets Target Position for Motors
            motorLeftfront.setTargetPosition(newLeftfrontTarget);
            motorRightfront.setTargetPosition(newRightfrontTarget);
            motorLeftback.setTargetPosition(newLeftbackTarget);
            motorRightback.setTargetPosition(newRightbackTarget);

            // Changes Motor Mode
            motorLeftfront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            motorRightfront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            motorLeftback.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            motorRightback.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            // Sets Power to Motors
            motorLeftfront.setPower(Math.abs(speed));
            motorRightfront.setPower(Math.abs(speed));
            motorLeftback.setPower(Math.abs(speed));
            motorRightback.setPower(Math.abs(speed));

            // Displays Target and Current Position When Active OpMode and Active Motor(s)
            while (opModeIsActive() && (motorLeftfront.isBusy() || motorRightfront.isBusy() || motorLeftback.isBusy() || motorRightback.isBusy())) {

                // Displays Target and Current Positions
                telemetry.addData("Target Value", "Running to %7d :%7d :%7d :%7d", newLeftfrontTarget, newRightfrontTarget, newLeftbackTarget, newRightbackTarget);
                telemetry.addData("Current Value", "Running at %7d :%7d: %7d :%7d",
                        motorLeftfront.getCurrentPosition(),
                        motorRightfront.getCurrentPosition(),
                        motorLeftback.getCurrentPosition(),
                        motorRightback.getCurrentPosition());
                telemetry.update();
            }

            // Stops Motors
            motorLeftfront.setPower(0);
            motorRightfront.setPower(0);
            motorLeftback.setPower(0);
            motorRightback.setPower(0);

            // Changes Motor Mode
            motorLeftfront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            motorRightfront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            motorLeftback.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            motorRightback.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
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
            newLeftfrontTarget = motorLeftfront.getCurrentPosition() + (int)(leftFrontinches * counts_per_inch);
            newRightfrontTarget = motorRightfront.getCurrentPosition() + (int)(rightFrontinches * counts_per_inch);
            newLeftbackTarget = motorLeftback.getCurrentPosition() + (int)(leftBackinches * counts_per_inch);
            newRightbackTarget = motorRightback.getCurrentPosition() + (int)(rightBackinches * counts_per_inch);

            motorLeftfront.setTargetPosition(newLeftfrontTarget);
            motorRightfront.setTargetPosition(newRightfrontTarget);
            motorLeftback.setTargetPosition(newLeftbackTarget);
            motorRightback.setTargetPosition(newRightbackTarget);

            // Sets Target Position for Motors
            motorLeftfront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            motorRightfront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            motorLeftback.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            motorRightback.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            // Sets Power to Motors
            motorLeftfront.setPower(Math.abs(speed));
            motorRightfront.setPower(Math.abs(speed));
            motorLeftback.setPower(Math.abs(speed));
            motorRightback.setPower(Math.abs(speed));

            // Displays Target and Current Position When Active OpMode and Active Motor(s)
            while (opModeIsActive() && (motorLeftfront.isBusy() || motorRightfront.isBusy() || motorLeftback.isBusy() || motorRightback.isBusy())) {

                // Displays Target and Current Positions
                telemetry.addData("Target Value",  "Running to %7d :%7d :%7d :%7d", newLeftfrontTarget,  newRightfrontTarget, newLeftbackTarget, newRightbackTarget);
                telemetry.addData("Current Value",  "Running at %7d :%7d: %7d :%7d",
                        motorLeftfront.getCurrentPosition(),
                        motorRightfront.getCurrentPosition(),
                        motorLeftback.getCurrentPosition(),
                        motorRightback.getCurrentPosition());
                telemetry.update();
            }

            // Stops Motors
            motorLeftfront.setPower(0);
            motorRightfront.setPower(0);
            motorLeftback.setPower(0);
            motorRightback.setPower(0);

            // Changes Motor Mode
            motorLeftfront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            motorRightfront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            motorLeftback.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            motorRightback.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
    }

    // Prints color (red/blue) and returns value
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
                    encoderDrive(0,0,-distance,0.2);
                    distanceJewel = distance;
                }
                else {
                    encoderDrive(0,0,distance,0.2);
                    distanceJewel = -distance;
                }
            }
            if (colorValue == 2) {
                displayJewel = "Blue";
                detectJewel = true;

                if (colorValue == allianceColor) {
                    encoderDrive(0,0,-distance,0.2);
                    distanceJewel = distance;
                }
                else {
                    encoderDrive(0,0,distance,0.2);
                    distanceJewel = -distance;
                }
            }
        }
        if (colorValue == 0) {
            displayJewel = "Unknown";
        }
        telemetry.addData("Color Identified:", displayJewel);
        telemetry.update();
    }
}