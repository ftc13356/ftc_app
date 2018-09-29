package org.firstinspires.ftc.teamcode.RelicRecovery.teleop.outreach.JavaTheBot;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name="JavaTheBot")
@Disabled
public class JavaTheBot extends OpMode {
    //Comment
    DcMotor left;
    DcMotor right;

    @Override
    public void init() {
        left = hardwareMap.dcMotor.get("left");
        right = hardwareMap.dcMotor.get("right");
    }

    @Override
    public void loop() {

        double driveFW = gamepad1.left_stick_y;
        double turn = gamepad1.right_stick_x;

        double leftPower = driveFW - turn;
        double rightPower = -driveFW - turn;

        left.setPower(leftPower);
        right.setPower(rightPower);
    }
}