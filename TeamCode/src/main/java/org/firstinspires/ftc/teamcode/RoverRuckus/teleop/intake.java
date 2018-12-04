package org.firstinspires.ftc.teamcode.RoverRuckus.teleop;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name = "Intake")
@Disabled
public class intake extends OpMode {

    private DcMotor intakeAngleMotor;
    private CRServo leftIntake;
    private CRServo rightIntake;

    private double speedControl = 0.25;

    private OpMode op;
    intake(OpMode opMode) {
        op = opMode;
    }

    public void init() {

        intakeAngleMotor = op.hardwareMap.dcMotor.get("intakeAngle");
        leftIntake = op.hardwareMap.crservo.get("leftIntake");
        rightIntake = op.hardwareMap.crservo.get("rightIntake");

    }

    public void loop() {

        double intakePower = op.gamepad2.left_stick_y;
        double intakeAngleMotorPower = -op.gamepad2.right_stick_y * speedControl;

        op.telemetry.addData("Current Position", intakeAngleMotor.getCurrentPosition());

        leftIntake.setPower(intakePower);
        rightIntake.setPower(-intakePower);

        intakeAngleMotor.setPower(intakeAngleMotorPower);
    }
}
