package org.firstinspires.ftc.teamcode.RelicRecovery.teleop.outreach.JavaTheBot.studentCode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name = "Jerfur: Nathan, Andrew")
@Disabled
public class JavaTheBotNathanAndrew extends OpMode {

    DcMotor left;
    DcMotor right;

    @Override
    public void init() {
        left = hardwareMap.dcMotor.get("left");
        right = hardwareMap.dcMotor.get("right");
    }

    @Override
    public void loop() {
        double leftPower;
        double rightPower;
        leftPower=(2*gamepad1.left_trigger-1);
        rightPower=(2*gamepad1.right_trigger-1);
        if (gamepad1.left_stick_x != 0 || gamepad1.left_stick_y != 0) {
            leftPower=0;
            rightPower=0;
        }
        left.setPower(leftPower);
        right.setPower(rightPower);
    }
}