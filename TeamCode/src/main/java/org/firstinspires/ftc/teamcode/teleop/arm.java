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

    // Initialize the variables
    private DcMotor armMotor;
    private Servo armClawLeft;
    private Servo armClawRight;
    private Servo rotateServo;

    private DigitalChannel touchSensor;

    private double armLeftPosition = 0;
    private double armRightPosition = 1;

    private double armSpeedControl = 0.5;
    private double armMotorPower = 0;

    private final int targetValue = -1500;
    private final int encoderPositionError = 50;

    // Creates OpMode
    private OpMode op;
    arm(OpMode opmode) {
        op = opmode;
    }

    public void init() {

        op.telemetry.addData("Arm", "Initializing");

        // Initializing the hardware variables
        armMotor = op.hardwareMap.dcMotor.get("armMotor");
        armClawLeft = op.hardwareMap.servo.get("clawLeft");
        armClawRight = op.hardwareMap.servo.get("clawRight");
        rotateServo = op.hardwareMap.servo.get("rotateServo");

        touchSensor = op.hardwareMap.digitalChannel.get("touchSensor");

        // Set the digital channel to input
        touchSensor.setMode(DigitalChannel.Mode.INPUT);

        // This tells the direction of the motor
        armMotor.setDirection(DcMotor.Direction.REVERSE);

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

    // This code will run constantly after the previous part is ran
    public void loop() {

        // This closes the arm claw when the left bumper is pressed
        if (op.gamepad2.left_bumper) {
            armLeftPosition = 0.45;
            armRightPosition = 0.55;
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

<<<<<<< HEAD
        // This closes the glyph claw when the "X" button is pressed
        if (op.gamepad2.x) {
            glyphLeftPosition = 0.2;
            glyphRightPosition = 0.8;
            op.telemetry.addData("Glyph Servo Status", "Closed");
        }
        // This opens the glyph claw when the "B" button is pressed
        else if (op.gamepad2.b) {
            glyphLeftPosition = 1;
            glyphRightPosition = 0;
            op.telemetry.addData("Glyph Servo Status", "Open Completely");
        }
        // This closes the glyph claw partially when the "Y" button is pressed
        else if (op.gamepad2.y) {
            glyphLeftPosition = 0.4;
            glyphRightPosition = 0.6;
            op.telemetry.addData("Glyph Servo Status", "Open Slightly");
=======
        // Moves arm to height needed to pick up glyph on the ground
        if (op.gamepad2.dpad_down) {
            armMotor.setTargetPosition(targetValue);
            armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            op.telemetry.addData("Goal Position", "%7d", targetValue);
            armMotorPower = 0.4;
>>>>>>> 02d13288494ffa573b3f86e9d945a21aa6568ad0
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
        if (touchSensor.getState() == false && armMotorPower > 0) {
            armMotorPower = 0;
            op.telemetry.addData("Touch Sensor", "Is Pressed, Arm Stopping");
        }

        // The calculated power is then applied to the motors
        armMotor.setPower(armMotorPower);

        // Sets Left Position and Right Position
        // Reads Left Position and Reads Right Position
        armClawLeft.setPosition(armLeftPosition);
        armClawRight.setPosition(armRightPosition);
        armLeftPosition = armClawLeft.getPosition();
        armRightPosition = armClawRight.getPosition();

        //This prints servo positions on the screen
        op.telemetry.addData("Left Arm Servo Position", armLeftPosition);
        op.telemetry.addData("Right Arm Servo Position", armRightPosition);

        op.telemetry.addData("Current Position", "%7d", armMotor.getCurrentPosition());
    }
}
