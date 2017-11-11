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

    private double leftPosition = 0;
    private double rightPosition = 1;

    private double armSpeedControl = 0.5;
    private double armSpeed = 0.5;

    private int targetValue = 0;

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
        armMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }


    // This code will run constantly after the previous part is runned.
    public void loop() {
        // Some variables are being defined.
        double armMotorPower;
        double timeLeft;

        double startTime = runtime.seconds();

        // This closes the arm when the left bumper is pressed.
        if(op.gamepad2.left_bumper) {
            leftPosition = 0.6;
            op.telemetry.addData("Servo Status", "Closed");
        }
        // This opens the arm when the right bumper is pressed.
        else if(op.gamepad2.right_bumper) {
            leftPosition = 0;
            op.telemetry.addData("Servo Status", "Open");
        }

        // The speed values are calculated and stored in variables.
        armMotorPower = op.gamepad2.left_stick_y * armSpeedControl;

        //4 counts per degree
        //All values should be negative
        if (op.gamepad1.dpad_down) {
            targetValue = -100;
            armMotor.setTargetPosition(targetValue);
            armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            op.telemetry.addData("Goal Position", "%7d",targetValue);
        }

        if (op.gamepad1.a) {
            targetValue = -1900;
            armMotor.setTargetPosition(targetValue);
            armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            op.telemetry.addData("Goal Position", "%7d",targetValue);
        }
        if (op.gamepad1.x) {
            targetValue = -3000;
            armMotor.setTargetPosition(targetValue);
            armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            op.telemetry.addData("Goal Position", "%7d",targetValue);
        }
        if (op.gamepad1.y) {
            targetValue = -4400;
            armMotor.setTargetPosition(targetValue);
            armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            op.telemetry.addData("Goal Position", "%7d",targetValue);
        }
        if (op.gamepad1.b) {
            targetValue = -5700;
            armMotor.setTargetPosition(targetValue);
            armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            op.telemetry.addData("Goal Position", "%7d",targetValue);
        }
        if (targetValue!= 0) {
            armMotor.setPower(armSpeed);
        }

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
        op.telemetry.addData("Current Position", "%7d", armMotor.getCurrentPosition());
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
