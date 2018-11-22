package org.firstinspires.ftc.teamcode.RelicRecovery.teleop.outreach.JavaTheBot.studentCode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name="Jeff: Aiden, William")
@Disabled
public class JavaTheBotAidenWilliam extends OpMode {

    DcMotor leftMotor;
    DcMotor rightMotor;
    @Override
    public void init() {
        leftMotor = hardwareMap.dcMotor.get("left");
        rightMotor = hardwareMap.dcMotor.get("right");
    }

    @Override
    public void loop() {
         leftMotor.setPower(-gamepad1.left_stick_y - gamepad1.right_stick_x);
         rightMotor.setPower(+gamepad1.right_stick_y + gamepad1.left_stick_x);
    }
}