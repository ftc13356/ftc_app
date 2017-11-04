package org.firstinspires.ftc.teamcode.teleop;

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

    double leftPosition = 0;
    double rightPosition = 1;

    double armSpeedControl = 0.5;

    private ElapsedTime runtime = new ElapsedTime();

    private OpMode op;

    Arm(OpMode opmode){
        op = opmode;
    }

    public void init() {
        // Tell the driver that the initialization is starting.
        op.telemetry.addData("Arm", "Initializing");

        // This is initializing the hardware variables.
        // The strings must be the same used when configuring the hardware using the FTC app.
        armMotor = op.hardwareMap.get(DcMotor.class, "armMotor");
        clawLeft = op.hardwareMap.servo.get("clawLeft");
        clawRight = op.hardwareMap.servo.get("clawRight");

        // This is just telling the direction of the motors.
        armMotor.setDirection(DcMotor.Direction.REVERSE);

        // Tell the driver that initialization is complete.
        op.telemetry.addData("Arm", "Initialized");
    }

    // This code is just waiting for the PLAY button to be pressed.
    public void init_loop() {
    }

    // This code will do something once when the PLAY button is pressed.
    public void start() {
        runtime.reset();
    }


    // This code will run constantly after the previous part is runned.
    public void loop() {
        // Some variables are being defined.
        double armMotorPower;
        double timeLeft;

        double startTime = runtime.seconds();

        // This closes the arm when the "A" button is pressed.
        if(op.gamepad2.a) {
            leftPosition = 0.6;
            op.telemetry.addData("Servo Status", "Closed");
        }
        // This opens the arm when the "B" button is pressed.
        else if(op.gamepad2.b) {
            leftPosition = 0;
            op.telemetry.addData("Servo Status", "Open");
        }

        // The speed values are calculated and stored in variables.
        armMotorPower = op.gamepad2.left_stick_y * armSpeedControl;

        // If time is up, then the motor powers will be 0.
        timeLeft = 120 + startTime - op.getRuntime();
        if (timeLeft <= 0) {
            armMotorPower = 0;
            clawLeft.setPosition(0);
            clawRight.setPosition(1);
        }

        // The calculated power is then applied to the motors.
        armMotor.setPower(armMotorPower);
        clawLeft.setPosition(leftPosition);
        rightPosition = 1 - leftPosition;
        clawRight.setPosition(rightPosition);
        leftPosition = clawLeft.getPosition();
        rightPosition = clawRight.getPosition();

        op.telemetry.addData("Status", "Time Left: " + timeLeft);
        op.telemetry.addData("Motor", armMotorPower);
        op.telemetry.addData("Left Servo Position", leftPosition);
        op.telemetry.addData("Right Servo Position", rightPosition);

        // If time is up, then the motors will stop.
        if (timeLeft <= 0) {
            armMotor.setPower(0);
            clawLeft.setPosition(0);
            clawRight.setPosition(1);
        }
    }
}
