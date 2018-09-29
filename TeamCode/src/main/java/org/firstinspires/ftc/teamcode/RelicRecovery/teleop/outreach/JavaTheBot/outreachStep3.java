package org.firstinspires.ftc.teamcode.RelicRecovery.teleop.outreach.JavaTheBot;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name = "Step 3")
@Disabled
public class outreachStep3 extends OpMode {

    DcMotor left;
    DcMotor right;
    @Override
    public void init() {
        left = hardwareMap.dcMotor.get("left");
        right = hardwareMap.dcMotor.get("right");
    }

    @Override
    public void loop() {

        left.setPower(gamepad1.left_stick_y);
        right.setPower(-gamepad1.right_stick_y);
    }
}