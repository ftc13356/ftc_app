package org.firstinspires.ftc.teamcode.RelicRecovery.autonomous.tests.multiThread;

import android.annotation.SuppressLint;
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
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

import java.util.Vector;

import static org.firstinspires.ftc.teamcode.key.key;

///////////////////////////////////////////////////////////////////////////////
// Purpose: Basic Autonomous Frame
// Author: Jonathan Ma, Ansh Gandhi
///////////////////////////////////////////////////////////////////////////////

@Autonomous(name="Basic Autonomous Frame")
@Disabled
public abstract class autonomousFrame2 extends LinearOpMode {

    // VERSION NUMBER(MAJOR.MINOR) - DATE
    // DO BEFORE EVERY COMMIT!
    private final String autonomousVersionNumber = "14.2 - 3/30/18 ";

    // Initialize Hardware variables
    protected DcMotor motorLeftfront;
    protected DcMotor motorRightfront;
    protected DcMotor motorLeftback;
    protected DcMotor motorRightback;

    public DcMotor armMotor;
    public DigitalChannel touchSensor;

    public Servo glyphClawSwerveLeft;
    public Servo glyphClawSwerveRight;

    private CRServo colorArm;
    private ColorSensor colorSensor;
    private final int armExtendTime = 4400;
    private final int armRetractTime = 4200;

    private VuforiaLocalizer vuforia;
    private VuforiaTrackables relicTrackables;
    private VuforiaTrackable relicTemplate;

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

    protected Vector<String> queue = new Vector<String>();
    // Need to be synchronized anymore?
    public synchronized void print2(String caption, Object value) {
        queue.add(caption + " : " + value);
    }

    // Initialize Hardware Map
    public void initializeHardwareMap() {
        motorLeftfront = hardwareMap.dcMotor.get("motorLeftfront");
        motorRightfront = hardwareMap.dcMotor.get("motorRightfront");
        motorLeftback = hardwareMap.dcMotor.get("motorLeftback");
        motorRightback = hardwareMap.dcMotor.get("motorRightback");
        armMotor = hardwareMap.dcMotor.get("armMotor");
        glyphClawSwerveLeft = hardwareMap.servo.get("glyphClawSwerveLeft");
        glyphClawSwerveRight = hardwareMap.servo.get("glyphClawSwerveRight");
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
        print2("Vuforia Status", "Initialized");
    }

    // Moves the arm
    public void moveArm(double power, long time) {
        armMotor.setPower(power);
        sleep(time);
        armMotor.setPower(0);
    }

    // Grips/releases glyph, change between holonomic drivetrain
    public void gripGlyphHolonomic() {
        glyphClawSwerveLeft.setPosition(0.5);
        glyphClawSwerveRight.setPosition(0.5);
    }
    public void releaseGlyphSwerve() {
        glyphClawSwerveLeft.setPosition(0);
        glyphClawSwerveRight.setPosition(1);
    }

    // Extends/retracts the color arm
    public void extendColorArm() {
        colorArm.setPower(-1);
        sleep(armExtendTime);
        colorArm.setPower(0);
    }
    public void retractColorArm() {
        colorArm.setPower(1);
        sleep(armRetractTime);
        colorArm.setPower(0);
    }

    // Combined Drive Function (Drivetrain, Encoder)
    @SuppressLint("DefaultLocale")
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
                String encoderValues;
                encoderValues = String.format("%7d :%7d :%7d :%7d",
                        newLeftfrontTarget, newRightfrontTarget,
                        newLeftbackTarget, newRightbackTarget);
                print2("Target Value" , encoderValues);
                encoderValues = String.format("%7d :%7d: %7d :%7d",
                        motorLeftfront.getCurrentPosition(),
                        motorRightfront.getCurrentPosition(),
                        motorLeftback.getCurrentPosition(),
                        motorRightback.getCurrentPosition());
                print2("Current Value", encoderValues);
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
            while (opModeIsActive() && (motorLeftfront.isBusy() || motorRightfront.isBusy()
                    || motorLeftback.isBusy() || motorRightback.isBusy())) {

                // Displays Target and Current Positions
                telemetry.addData("Target Value",  "Running to %7d :%7d :%7d :%7d",
                        newLeftfrontTarget, newRightfrontTarget,
                        newLeftbackTarget, newRightbackTarget);
                telemetry.addData("Current Value",  "Running at %7d :%7d: %7d :%7d",
                        motorLeftfront.getCurrentPosition(),
                        motorRightfront.getCurrentPosition(),
                        motorLeftback.getCurrentPosition(),
                        motorRightback.getCurrentPosition());
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
    private float checkColor() {

        float masterValue = 0;
        float hsvValues[] = {0F, 0F, 0F};
        Color.RGBToHSV((colorSensor.red()), (colorSensor.green()), (colorSensor.blue()), hsvValues);
        print2("Hue", hsvValues[0]);
        if (hsvValues[0] >= 340 || hsvValues[0] <= 20) {
            masterValue = 1;
        }
        if (hsvValues[0] >= 200 && hsvValues[0] <= 275) {
            masterValue = 2;
        }

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
        print2("Color Identified:", displayJewel);
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
            distanceVuMark = centerDistance;
        }
        print2("VuMark Identified:", displayVuMark);
    }
}