package org.firstinspires.ftc.teamcode.RelicRecovery.teleop.outreach.JavaTheBot;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name = "Step 2")
public class outreachStep2 extends OpMode {

    DcMotor left;
    DcMotor right;
    @Override
    public void init() {
        left = hardwareMap.dcMotor.get("left");
        right = hardwareMap.dcMotor.get("right");
    }

    @Override
    public void loop() {
        left.setPower(1);
        right.setPower(1);
    }
}