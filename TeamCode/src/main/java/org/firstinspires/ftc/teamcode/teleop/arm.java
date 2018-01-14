package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.Servo;

///////////////////////////////////////////////////////////////////////////////
// Purpose: Class for controlling the arm and claw
// Author: Jonathan Ma, Ansh Gandhi
///////////////////////////////////////////////////////////////////////////////

@TeleOp(name = "Arm")
@Disabled
public class arm {

    // GO TO
    // teleop.JAVA TO
    // UPDATE VERSION NUMBER
    // BEFORE EVERY COMMIT

    // Initialize motor/servo variables
    private DcMotor armMotor;
    private Servo armClawLeft;
    private Servo armClawRight;
    private Servo rotateServo;
    private DigitalChannel touchSensor;

    // Initialize arm claw variables
    private double armLeftPosition = 0;
    private double armRightPosition = 1;
    private double rotatePosition = 0.5;

    // Initialize arm motor variables
    private final double armSpeedControl = 0.5;
    private double armMotorPower = 0;
    private double currentArmPosition = 0;

    // between 1900 and 1500
    private final int glyphLevelOne = -1700;
    private final int glyphLevelTwo = -3340;
    private final int encoderPositionError = 50;

    // Create OpMode
    private OpMode op;
    arm(OpMode opmode) {
        op = opmode;
    }

    public void init() {

        op.telemetry.addData("Arm", "Initializing");

        // Initialize hardware variables
        armMotor = op.hardwareMap.dcMotor.get("armMotor");
        armClawLeft = op.hardwareMap.servo.get("clawLeft");
        armClawRight = op.hardwareMap.servo.get("clawRight");
        rotateServo = op.hardwareMap.servo.get("rotateServo");
        touchSensor = op.hardwareMap.digitalChannel.get("touchSensor");

        // Set the digital channel to input
        touchSensor.setMode(DigitalChannel.Mode.INPUT);

        // Sets direction/zero power behavior of motor
        armMotor.setDirection(DcMotor.Direction.REVERSE);
        armMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        // Reset encoder
        armMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        op.telemetry.addData("Arm", "Initialized");
    }

    // This code is just waiting for the Play button to be pressed
    public void init_loop() {
    }

    // This code will do something once when the Play button is pressed
    public void start() {
    }

    // This code will run constantly after the previous part has been run
    public void loop() {

        /*if ((currentArmPosition <= -1152) && (currentArmPosition >= -5888)) {
            rotatePosition = 0.5+ Math.abs(currentArmPosition);
        }*/

        int targetValue = 0;

        // This closes the arm claw when the left bumper is pressed
        if (op.gamepad2.left_bumper) {
            armLeftPosition = 0.7;
            armRightPosition = 0.3;
            op.telemetry.addData("Arm Servo Status", "Closed");
        }
        // This opens the arm claw completely when the right bumper is pressed
        else if (op.gamepad2.right_bumper) {
            armLeftPosition = 0;
            armRightPosition = 1;
            op.telemetry.addData("Arm Servo Status", "Open Completely");
        }
        // This opens the arm claw partially when the "A" button is pressed
        else if (op.gamepad2.a) {
            armLeftPosition = 0.35;
            armRightPosition = 0.65;
            op.telemetry.addData("Arm Servo Status", "Open Slightly");
        }

        // The stick can also fine tune the rotational servo
        rotatePosition = (op.gamepad2.right_stick_x / 2) + 0.5;

        // This moves the rotational servo such that no rod sticks out
        if (op.gamepad2.dpad_left) {
            rotatePosition = 0;
            op.telemetry.addData("Rotate Servo Status", "Back");
        }

        // This moves the rotational servo such that the top rod sticks out
        if (op.gamepad2.dpad_right) {
            rotatePosition = 1;
            op.telemetry.addData("Rotate Servo Status", "Up");
        }

        // Moves arm to height needed to pick up glyph on the ground
        if (op.gamepad2.dpad_down) {
            armMotor.setTargetPosition(glyphLevelOne);
            targetValue = glyphLevelOne;
            armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            op.telemetry.addData("Goal Position", "%7d", glyphLevelOne);
            armMotorPower = 0.4;
        }

        if (op.gamepad2.dpad_up) {
            armMotor.setTargetPosition(glyphLevelTwo);
            targetValue = glyphLevelTwo;
            armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            op.telemetry.addData("Goal Position", "%7d", glyphLevelTwo);
            armMotorPower = 0.4;
        }

        // The left stick is used to raise and lower the arm
        if ((targetValue - encoderPositionError <= armMotor.getCurrentPosition() &&
                armMotor.getCurrentPosition() <= targetValue + encoderPositionError)
                || !armMotor.isBusy()) {
            armMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            armMotorPower = op.gamepad2.left_stick_y * armSpeedControl;
        }

        // If arm's motion is downward(+) and it hits touch sensor, stop its movement,
        // otherwise (motion is upward(-)), allow it to move
        if (!touchSensor.getState()) {
            op.telemetry.addData("Touch Sensor", "Is Pressed");

            if (armMotorPower > 0) {
                armMotorPower = 0;
                op.telemetry.addData("Arm", "Is Stopped");
            }
        }

        // The calculated power is then applied to the motors
        armMotor.setPower(armMotorPower);

        // Sets/Reads Servo Positions
        armClawLeft.setPosition(armLeftPosition);
        armClawRight.setPosition(armRightPosition);
        armLeftPosition = armClawLeft.getPosition();
        armRightPosition = armClawRight.getPosition();

        rotateServo.setPosition(rotatePosition);
        rotatePosition = rotateServo.getPosition();

        currentArmPosition = armMotor.getCurrentPosition();

        //Prints servo/encoder positions
        op.telemetry.addData("Left Arm Servo Position", armLeftPosition);
        op.telemetry.addData("Right Arm Servo Position", armRightPosition);
        op.telemetry.addData("Rotate Servo Position", rotatePosition);
        op.telemetry.addData("Arm Current Position", "%7d", armMotor.getCurrentPosition());
    }
}