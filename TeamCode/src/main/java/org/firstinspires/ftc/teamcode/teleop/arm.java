package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
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

    private Servo glyphClawLeft;
    private Servo glyphClawRight;

    private double armLeftPosition = 0;
    private double armRightPosition = 1;

    private double glyphLeftPosition = 1;
    private double glyphRightPosition = 0;

    private double armSpeedControl = 0.5;

    // Creates OpMode
    private OpMode op;
    arm(OpMode opmode){
        op = opmode;
    }

    public void init() {

        op.telemetry.addData("Arm", "Initializing");

        // This is initializing the hardware variables
        armMotor = op.hardwareMap.get(DcMotor.class, "armMotor");
        armClawLeft = op.hardwareMap.servo.get("clawLeft");
        armClawRight = op.hardwareMap.servo.get("clawRight");
        glyphClawLeft = op.hardwareMap.servo.get("glyphClawLeft");
        glyphClawRight = op.hardwareMap.servo.get("glyphClawRight");

        // This tells the direction of the motor
        armMotor.setDirection(DcMotor.Direction.REVERSE);

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

        // Some variables are being defined
        double armMotorPower;

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
        // This opens the arm claw partially when the "Y" button is pressed
        else if (op.gamepad2.y) {
            armLeftPosition = 0.5;
            armRightPosition = 0.5;
            op.telemetry.addData("Arm Servo Status", "Open Slightly");
        }

        // This closes the glyph claw when the "X" button is pressed
        if (op.gamepad2.x) {
            glyphLeftPosition = 0.3;
            glyphRightPosition = 0.7;
            op.telemetry.addData("Glyph Servo Status", "Closed");
        }
        // This closes the glyph claw when the "A" button is pressed
        else if (op.gamepad2.a) {
            glyphLeftPosition = 1;
            glyphRightPosition = 0;
            op.telemetry.addData("Glyph Servo Status", "Open Completely");
        }
        // This closes the glyph claw when the "B" button is pressed
        else if (op.gamepad2.b) {
            glyphLeftPosition = 0.4;
            glyphRightPosition = 0.6;
            op.telemetry.addData("Glyph Servo Status", "Open Slightly");
        }

        // The left stick is used to raise and lower the arm
        armMotorPower = op.gamepad2.left_stick_y * armSpeedControl;

        // The calculated power is then applied to the motors
        armMotor.setPower(armMotorPower);

        // Sets Left Position and Right Position
        // Reads Left Position and Reads Right Position
        armClawLeft.setPosition(armLeftPosition);
        armClawRight.setPosition(armRightPosition);
        armLeftPosition = armClawLeft.getPosition();
        armRightPosition = armClawRight.getPosition();

        glyphClawLeft.setPosition(glyphLeftPosition);
        glyphClawRight.setPosition(glyphRightPosition);
        glyphLeftPosition = glyphClawLeft.getPosition();
        glyphRightPosition = glyphClawRight.getPosition();

        //This prints servo positions on the screen
        op.telemetry.addData("Left Arm Servo Position", armLeftPosition);
        op.telemetry.addData("Right Arm Servo Position", armRightPosition);

        op.telemetry.addData("Left Glyph Servo Position", glyphLeftPosition);
        op.telemetry.addData("Right Glyph Servo Position", glyphClawRight);
    }
}
