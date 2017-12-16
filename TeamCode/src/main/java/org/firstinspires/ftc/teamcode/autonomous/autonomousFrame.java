package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;

///////////////////////////////////////////////////////////////////////////////
// Purpose: Basic Autonomous Frame
// Author: Jonathan Ma, Ansh Gandhi
///////////////////////////////////////////////////////////////////////////////

@Autonomous(name="Basic Autonomous Frame")
@Disabled
public abstract class autonomousFrame extends LinearOpMode {

    // Initialize variables
    public DcMotor motorLeftfront;
    public DcMotor motorRightfront;
    public DcMotor motorLeftback;
    public DcMotor motorRightback;
    
    public DcMotor armMotor;
    
    public Servo armClawLeft;
    public Servo armClawRight;
    
    public Servo glyphClawLeft;
    public Servo glyphClawRight;
    
    double leftPosition = 0;
    double rightPosition = 1;

    public VuforiaLocalizer vuforia;
    
    int targetValue = 0;
    static final double counts_per_motor_rev = 1680 ;
    static final double robot_diameter = 23.0;
    static final double drive_gear_reduction = 0.75;
    static final double wheel_diameter_inches = 4.0 ;
    static final double counts_per_inch = (counts_per_motor_rev * drive_gear_reduction) /
                                          (wheel_diameter_inches * Math.PI);
    static final double counts_per_degree = counts_per_inch * robot_diameter * Math.PI / 360;

    // Initialize Hardware Map
    public void initializeHardwareMap() {
        motorLeftfront = hardwareMap.dcMotor.get("motorLeftfront");
        motorRightfront = hardwareMap.dcMotor.get("motorRightfront");
        motorLeftback = hardwareMap.dcMotor.get("motorLeftback");
        motorRightback = hardwareMap.dcMotor.get("motorRightback");
        armMotor = hardwareMap.dcMotor.get("armMotor");
        armClawLeft = hardwareMap.servo.get("clawLeft");
        armClawRight = hardwareMap.servo.get("clawRight");
        glyphClawLeft = hardwareMap.servo.get("glyphClawLeft");
        glyphClawRight = hardwareMap.servo.get("glyphClawRight");
    }

    // Set Motor Direction
    public void setMotorDirection() {
        motorLeftfront.setDirection(DcMotor.Direction.FORWARD);
        motorRightfront.setDirection(DcMotor.Direction.FORWARD);
        motorLeftback.setDirection(DcMotor.Direction.FORWARD);
        motorRightback.setDirection(DcMotor.Direction.FORWARD);
        armMotor.setDirection(DcMotor.Direction.REVERSE);
    }

    /*

    Following Functions are NOT OPERATIONAL (12/15/17) - Jonathan

    // Arm Position Function (Arm, Encoder)
    public void armPosition(int position) {
        armMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        if (position == 0){
            targetValue = -100;
        }
        else if (position == 1){
            targetValue = -1900;
        }
        else if (position == 2){
            targetValue = -3000;
        }
        else if (position == 3){
            targetValue = -4400;
        }
        else if (position == 4){
            targetValue = -5700;
        }
        armMotor.setTargetPosition(targetValue);
        armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        armMotor.setPower(Math.abs(0.5));
        telemetry.addData("Goal Position", "%7d",targetValue);
    }

    // Claw Functions (Arm)
    public void closeClaw() {
        leftPosition = 0.7;
        rightPosition = 0.3;
        armClawLeft.setPosition(leftPosition);
        armClawRight.setPosition(rightPosition);
        telemetry.addData("Arm Servo Status", "Closed");
    }

    public void partialClaw() {
        leftPosition = 0.5;
        rightPosition = 0.5;
        armClawLeft.setPosition(leftPosition);
        armClawRight.setPosition(rightPosition);
        telemetry.addData("Arm Servo Status", "Partially Open");
    }

    public void openClaw() {
        leftPosition = 0.0;
        rightPosition = 1.0;
        armClawLeft.setPosition(leftPosition);
        armClawRight.setPosition(rightPosition);
        telemetry.addData("Arm Servo Status", "Open");
    }

    // Claw Functions (Glyph Claw)
    public void openGlyphClaw(){
        leftPosition = 1.0;
        rightPosition = 0.0;
        glyphClawLeft.setPosition(leftPosition);
        glyphClawRight.setPosition(rightPosition);
        telemetry.addData("Glyph Servo Status", "Open");
    }

    public void closeGlyphClaw(){
        leftPosition = 0.3;
        rightPosition = 0.7;
        glyphClawLeft.setPosition(leftPosition);
        glyphClawRight.setPosition(rightPosition);
        telemetry.addData("Glyph Servo Status", "Closed");
    }

    public void partialGlyphClaw(){
        leftPosition = 0.4;
        rightPosition = 0.6;
        glyphClawLeft.setPosition(leftPosition);
        glyphClawRight.setPosition(rightPosition);
        telemetry.addData("Glyph Servo Status", "Partially Open");
    }

    */

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
}