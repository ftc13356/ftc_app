package org.firstinspires.ftc.teamcode.autonomous;

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
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

import static org.firstinspires.ftc.teamcode.key.key;

///////////////////////////////////////////////////////////////////////////////
// Purpose: Basic Autonomous Frame
// Author: Jonathan Ma, Ansh Gandhi
///////////////////////////////////////////////////////////////////////////////

@Autonomous(name="Basic Autonomous Frame")
@Disabled
public abstract class autonomousFrame extends LinearOpMode {

    // VERSION NUMBER(MAJOR.MINOR) - DATE
    // DO BEFORE EVERY COMMIT!
    private final String autonomousVersionNumber = "13.3- 2/4/18 ";

    // Initialize Hardware variables
    protected DcMotor motorLeftfront;
    protected DcMotor motorRightfront;
    protected DcMotor motorLeftback;
    protected DcMotor motorRightback;
    
    public DcMotor armMotor;
    public DigitalChannel touchSensor;
    
    public Servo glyphClawLeft;
    public Servo glyphClawRight;

    CRServo colorArm;
    protected ColorSensor colorSensor;

    private VuforiaLocalizer vuforia;
    protected VuforiaTrackables relicTrackables;
    protected VuforiaTrackable relicTemplate;

    // Initialize Drive Variables
    private final double counts_per_motor_rev = 1680 ;
    private final double robot_diameter = 23.0;
    private final double drive_gear_reduction = 0.75;
    private final double wheel_diameter_inches = 4.0 ;
    private final double counts_per_inch = (counts_per_motor_rev * drive_gear_reduction) /
                                          (wheel_diameter_inches * Math.PI);
    private final double counts_per_degree = counts_per_inch * robot_diameter * Math.PI / 360;

    // Initialize Jewel Variables
    private boolean detectJewel = false;
    float allianceColor;
    double distanceJewel;
    private String displayJewel = "";
    private ElapsedTime jewelTime = new ElapsedTime();

    // Initialize VuMark Variables
    private boolean detectVuMark = false;
    double distanceVuMark = 0;
    private String displayVuMark = "";
    private ElapsedTime vuMarkTime = new ElapsedTime();

    // Initialize Hardware Map
    public void initializeHardwareMap() {
        motorLeftfront = hardwareMap.dcMotor.get("motorLeftfront");
        motorRightfront = hardwareMap.dcMotor.get("motorRightfront");
        motorLeftback = hardwareMap.dcMotor.get("motorLeftback");
        motorRightback = hardwareMap.dcMotor.get("motorRightback");
        armMotor = hardwareMap.dcMotor.get("armMotor");
        glyphClawLeft = hardwareMap.servo.get("glyphClawLeft");
        glyphClawRight = hardwareMap.servo.get("glyphClawRight");
        colorArm = hardwareMap.crservo.get("colorArm");
        touchSensor = hardwareMap.digitalChannel.get("touchSensor");
        colorSensor = hardwareMap.colorSensor.get("colorSensor");
    }

    // Set Motor Direction
    public void setMotorDirection() {
        motorLeftfront.setDirection(DcMotor.Direction.FORWARD);
        motorRightfront.setDirection(DcMotor.Direction.FORWARD);
        motorLeftback.setDirection(DcMotor.Direction.FORWARD);
        motorRightback.setDirection(DcMotor.Direction.FORWARD);
        armMotor.setDirection(DcMotor.Direction.REVERSE);
        touchSensor.setMode(DigitalChannel.Mode.INPUT);
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

    // Basic Drive Functions (Drivetrain)
    public void moveForward (double power, long time) {
        motorLeftfront.setPower(-power);
        motorRightfront.setPower(power);
        motorLeftback.setPower(-power);
        motorRightback.setPower(power);
        sleep(time);
    }

    public void moveLeft (double power, long time) {
        motorLeftfront.setPower(power);
        motorRightfront.setPower(power);
        motorLeftback.setPower(-power);
        motorRightback.setPower(-power);
        sleep(time);
    }

    public void  moveRight (double power, long time) {
        moveLeft(-power, time);
    }

    public void  moveBackward (double power, long time) {
        moveForward(-power, time);
    }

    // Combined Drive Functions (Drivetrain)
    public void drive(double driveFB, double driveS, double turn, long time, double speedfactor) {
        // Define Speed Variables
        double motorLeftfrontPower;
        double motorRightfrontPower;
        double motorLeftbackPower;
        double motorRightbackPower;

        // Calculating Power
        motorLeftfrontPower = Range.clip((-driveFB + driveS + turn) * speedfactor, -1.0, 1.0);
        motorRightfrontPower = Range.clip((driveFB + driveS + turn) * speedfactor, -1.0, 1.0);
        motorLeftbackPower = Range.clip((-driveFB - driveS + turn) * speedfactor, -1.0, 1.0);
        motorRightbackPower = Range.clip((driveFB - driveS + turn) * speedfactor, -1.0, 1.0);

        // Set Motor Power to Calculated Power
        motorLeftfront.setPower(motorLeftfrontPower);
        motorRightfront.setPower(motorRightfrontPower);
        motorLeftback.setPower(motorLeftbackPower);
        motorRightback.setPower(motorRightbackPower);

        sleep(time);

        telemetry.addData("Motors", "Leftfront (%.2f), Rightfront (%.2f), Leftback (%.2f), Rightback (%.2f)", motorLeftfrontPower, motorRightfrontPower, motorLeftbackPower, motorRightbackPower);
        telemetry.update();
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
    public void encoderDriveBasic(double speed, double leftFrontinches, double rightFrontinches, double leftBackinches, double rightBackinches) {

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
        Color.RGBToHSV((int) (colorSensor.red()), (int) (colorSensor.green()), (int) (colorSensor.blue()), hsvValues);
        telemetry.addData("Hue", hsvValues[0]);
        if (hsvValues[0] >= 340 || hsvValues[0] <= 20) {
            masterValue = 1;
        }
        if (hsvValues[0] >= 210 && hsvValues[0] <= 275) {
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
                    encoderDrive(distance,0,0,0.3);
                    distanceJewel = distance;
                }
                else {
                    encoderDrive(-distance,0,0,0.3);
                    distanceJewel = -distance;
                }
            }
            if (colorValue == 2) {
                displayJewel = "Blue";
                detectJewel = true;

                if (colorValue == allianceColor) {
                    encoderDrive(distance,0,0,0.3);
                    distanceJewel = distance;
                }
                else {
                    encoderDrive(-distance,0,0,0.3);
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

    // Changes drive distance depending on VuMark
    public void detectVuMark (double leftDistance, double centerDistance, double rightDistance) {
        relicTrackables.activate();
        vuMarkTime.reset();
        while (opModeIsActive() && detectVuMark == false && vuMarkTime.milliseconds() <= 3000) {
            RelicRecoveryVuMark vuMark = RelicRecoveryVuMark.from(relicTemplate);
            if (vuMark == RelicRecoveryVuMark.LEFT) {
                displayVuMark = "Left";
                distanceVuMark = leftDistance;
                detectVuMark = true;
            }
            if (vuMark == RelicRecoveryVuMark.CENTER) {
                displayVuMark = "Center";
                distanceVuMark = centerDistance;
                detectVuMark = true;
            }
            if (vuMark == RelicRecoveryVuMark.RIGHT) {
                displayVuMark = "Right";
                distanceVuMark = rightDistance;
                detectVuMark = true;
            }
        }
        if (detectVuMark == false){
            displayVuMark = "Unknown";
            distanceVuMark = -11.5;
        }
        telemetry.addData("VuMark Identified:", displayVuMark);
        telemetry.update();
    }
}