package org.firstinspires.ftc.teamcode.teleop.teleopV2;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name = "Arm")
@Disabled
public class Arm {

    private DcMotor armMotor;
    private Servo clawLeft;
    private Servo clawRight;

    private ElapsedTime runtime = new ElapsedTime();

    private OpMode op;

    Arm(OpMode opmode){
        op = opmode;
    }

    public void init() {
        // Tell the driver that the initialization is starting.
        op.telemetry.addData("Status", "Initializing");

        // This is initializing the hardware variables.
        // The strings must be the same used when configuring the hardware using the FTC app.
        armMotor = op.hardwareMap.get(DcMotor.class, "armMotor");
        clawLeft = op.hardwareMap.servo.get("clawLeft");
        clawRight = op.hardwareMap.servo.get("clawRight");

        // This is just telling the direction of the motors.
        armMotor.setDirection(DcMotor.Direction.REVERSE);

        // Tell the driver that initialization is complete.
        op.telemetry.addData("Status", "Initialized");
    }

    // This code is just waiting for the PLAY button to be pressed.
    public void init_loop() {
    }

    // This code will do something once when the PLAY button is pressed.
    public void start() {
        runtime.reset();
    }

    // This code is just defining the variables needed for the loop.
    private double leftposition = 0;
    private double rightposition = 1;
    private double armSpeedControl = 0.5;

    // This code will run constantly after the previous part is runned.
    public void loop() {
        // Some variables are being defined.
        double armMotorPower;
        double timeleft;

        // This closes the arm when the "A" button is pressed.
        if(op.gamepad2.a) {
            leftposition = 0.6;
            op.telemetry.addData("Servo Status", "Closed");
        }
        // This opens the arm when the "B" button is pressed.
        else if(op.gamepad2.b) {
            leftposition = 0;
            op.telemetry.addData("Servo Status", "Open");
        }

        // The speed values are calculated and stored in variables.
        armMotorPower = op.gamepad2.left_stick_y * armSpeedControl;

        // If time is up, then the motor powers will be 0.
        timeleft = 120 - op.getRuntime();
        if (timeleft <= 0) {
            armMotorPower = 0;
        }

        // The calculated power is then applied to the motors.
        armMotor.setPower(armMotorPower);
        clawLeft.setPosition(leftposition);
        rightposition = 1-leftposition;
        clawRight.setPosition(rightposition);
        leftposition = clawLeft.getPosition();
        rightposition = clawRight.getPosition();

        op.telemetry.addData("Status", "Time Left: " + timeleft);
        op.telemetry.addData("Motor", armMotorPower);
        op.telemetry.addData("Left Servo Position", leftposition);
        op.telemetry.addData("Right Servo Position", rightposition);

        // If time is up, then the motors will stop.
        if (timeleft <= 0) {
            armMotor.setPower(0);
        }
    }
}
