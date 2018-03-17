package org.firstinspires.ftc.teamcode.offSeason.teleop.tests;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name = "Intake Test")
public class intakeTest extends OpMode {

    private DcMotor leftIntake;
    private DcMotor rightIntake;

    @Override
    public void init() {
        leftIntake = hardwareMap.dcMotor.get("leftIntake");
        rightIntake = hardwareMap.dcMotor.get("rightIntake");
    }
    @Override
    public void loop() {
        leftIntake.setPower(gamepad1.left_stick_y);
        rightIntake.setPower(-gamepad1.left_stick_y);
    }
}
