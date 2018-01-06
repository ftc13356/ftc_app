package org.firstinspires.ftc.teamcode.teleop.debug;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "Arm Move for Debug")
public class armMoveDebug extends OpMode{

    private DcMotor armMotor;
    private Servo armClawLeft;
    private Servo armClawRight;

    private final double armSpeedControl = 0.5;
    private double armMotorPower = 0;
    private double armLeftPosition = 0;
    private double armRightPosition = 1;

    @Override
    public void init() {
        armMotor = hardwareMap.get(DcMotor.class, "armMotor");
        armClawLeft = hardwareMap.servo.get("clawLeft");
        armClawRight = hardwareMap.servo.get("clawRight");
        armMotor.setDirection(DcMotor.Direction.REVERSE);
        armMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        armMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    @Override
    public void loop() {
        armMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        armMotorPower = gamepad1.left_stick_y * armSpeedControl;

        if (gamepad1.dpad_up) {
            armLeftPosition = 0;
            armRightPosition = 1;
        }

        if (gamepad1.dpad_down) {
            armLeftPosition = 1;
            armRightPosition = 0;
        }

        if (gamepad1.dpad_left) {
            armLeftPosition = -1;
            armRightPosition = 1;
        }

        if (gamepad1.dpad_right) {
            armLeftPosition = 1;
            armRightPosition = -1;
        }

        armMotor.setPower(armMotorPower);
        armClawLeft.setPosition(armLeftPosition);
        armClawRight.setPosition(armRightPosition);

        telemetry.addData("Left Arm Servo Position", armClawLeft.getPosition());
        telemetry.addData("Right Arm Servo Position", armClawRight.getPosition());
        telemetry.addData("Arm Position", armMotor.getCurrentPosition());
    }
}
