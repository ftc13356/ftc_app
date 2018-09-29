package org.firstinspires.ftc.teamcode.RelicRecovery.teleop.debug;

import android.graphics.Color;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.Servo;

///////////////////////////////////////////////////////////////////////////////
// Purpose: Class for controlling debugging, testing different parts of the robot
// Author: Jonathan Ma, Ansh Gandhi
///////////////////////////////////////////////////////////////////////////////

@TeleOp(name = "Robot Debug")
@Disabled
public class robotDebug extends OpMode{

    // VERSION NUMBER(MAJOR.MINOR) - DATE
    // DO BEFORE EVERY COMMIT!
    private final String robotDebugVersionNumber = "5.1 - 3/9/18 ";

    // Initialize the variables
    private DcMotor armMotor;
    private Servo armClawLeft;
    private Servo armClawRight;
    private DigitalChannel touchSensor;
    private CRServo colorArm;
    private ColorSensor colorSensor;

    private final double armSpeedControl = 0.5;

    private double armMotorPower = 0;
    private double colorArmPower = 0;

    private double armLeftPosition = 0.35;
    private double armRightPosition = 0.65;

    private float hsvValues[] = {0F, 0F, 0F};

    @Override
    public void init() {

        telemetry.addData("Debug Program Version", robotDebugVersionNumber);
        telemetry.addData("Arm", "Initializing");

        // Initialize hardware variables
        armMotor = hardwareMap.dcMotor.get("armMotor");
        armClawLeft = hardwareMap.servo.get("clawLeft");
        armClawRight = hardwareMap.servo.get("clawRight");
        touchSensor = hardwareMap.digitalChannel.get("touchSensor");
        colorSensor = hardwareMap.colorSensor.get("colorSensor");
        colorArm = hardwareMap.crservo.get("colorArm");
        touchSensor.setMode(DigitalChannel.Mode.INPUT);

        armMotor.setDirection(DcMotor.Direction.REVERSE);
        armMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        telemetry.addData("Arm", "Initialized");
    }

    @Override
    public void loop() {

        // Right bumper opens claw, Left bumper closes claw, B opens claw slightly
        if (gamepad1.left_bumper) {
            armLeftPosition = 0.7;
            armRightPosition = 0.3;
            telemetry.addData("Arm Servo Status", "Closed");
        }

        if (gamepad1.right_bumper) {
            armLeftPosition = 0.35;
            armRightPosition = 0.65;
            telemetry.addData("Arm Servo Status", "Open Completely");
        }

        if (gamepad1.a) {
            armLeftPosition = 0.5;
            armRightPosition = 0.5;
            telemetry.addData("Arm Servo Status", "Open Slightly");
        }

        // Reset encoder
        if (gamepad1.b) {
            armMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            armMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }

        // Left stick used to control arm, Right stick used to control color arm
        armMotorPower = gamepad1.left_stick_y * armSpeedControl;
        colorArmPower = gamepad1.right_stick_x;

        // Stop arm's motion if it hits touch sensor and moving downward
        if (!touchSensor.getState()) {
            telemetry.addData("Touch Sensor", "Is Pressed");

            if (armMotorPower > 0) {
                armMotorPower = 0;
                telemetry.addData("Arm", "Is Stopped");
            }
        }

        // Scans for red or blue color
        Color.RGBToHSV((colorSensor.red()), (colorSensor.green()), (colorSensor.blue()), hsvValues);
        telemetry.addData("Hue", hsvValues[0]);
        if (hsvValues[0] >= 340 || hsvValues[0] <= 20) {
            telemetry.addData("Color Sensor Status", "Red");
        }
        if (hsvValues[0] >= 200 && hsvValues[0] <= 275) {
            telemetry.addData("Color Sensor Status", "Blue");
        }
        else {
            telemetry.addData("Color Sensor Status", "Unknown");
        }

        // Set arm, color arm power, servo position
        armMotor.setPower(armMotorPower);
        colorArm.setPower(colorArmPower);

        armClawLeft.setPosition(armLeftPosition);
        armClawRight.setPosition(armRightPosition);

        telemetry.addData("Left Arm Servo Position", armClawLeft.getPosition());
        telemetry.addData("Right Arm Servo Position", armClawRight.getPosition());
        telemetry.addData("Arm Position", armMotor.getCurrentPosition());

    }
}
