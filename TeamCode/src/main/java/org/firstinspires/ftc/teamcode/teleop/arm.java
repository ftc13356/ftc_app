package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

///////////////////////////////////////////////////////////////////////////////
// Purpose: Class for controlling the arm and claw
// Developers: RoboTech Inc. Software Development Team
///////////////////////////////////////////////////////////////////////////////
@TeleOp(name = "Arm")
@Disabled
public class arm {

    // Initialize the variables
    private DcMotor armMotor;
    private Servo clawLeft;
    private Servo clawRight;

    private double leftPosition = 0;
    private double rightPosition = 1;
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
        clawLeft = op.hardwareMap.servo.get("clawLeft");
        clawRight = op.hardwareMap.servo.get("clawRight");

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

        // This closes the arm when the left bumper is pressed
        if (op.gamepad2.left_bumper) {
            leftPosition = 0.6;
            op.telemetry.addData("Servo Status", "Closed");
        }
        // This opens the arm completely when the right bumper is pressed
        else if (op.gamepad2.right_bumper) {
            leftPosition = 0;
            op.telemetry.addData("Servo Status", "Open Completely");
        }
        // This opens the arm partially when the "A" button is pressed
        else if (op.gamepad2.a) {
            leftPosition = 0.48;
            op.telemetry.addData("Servo Status", "Open Slightly");
        }

        // The left stick is used to raise and lower the arm
        armMotorPower = op.gamepad2.left_stick_y * armSpeedControl;

        // The calculated power is then applied to the motors
        armMotor.setPower(armMotorPower);

        // Sets Left Position and Calculates Right Position Based on Left Position (Sum is 1)
        // Sets Right Position
        // Reads Left Position and Reads Right Position
        clawLeft.setPosition(leftPosition);
        rightPosition = 1 - leftPosition;
        clawRight.setPosition(rightPosition);
        leftPosition = clawLeft.getPosition();
        rightPosition = clawRight.getPosition();

        //This prints servo positions on the screen
        op.telemetry.addData("Left Servo Position", leftPosition);
        op.telemetry.addData("Right Servo Position", rightPosition);
    }
}
