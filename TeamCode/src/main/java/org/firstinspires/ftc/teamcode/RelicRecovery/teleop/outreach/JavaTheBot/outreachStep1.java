package org.firstinspires.ftc.teamcode.RelicRecovery.teleop.outreach.JavaTheBot;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name = "Step 1")
@Disabled
public class outreachStep1 extends OpMode {

    DcMotor left;
    @Override
    public void init() {
        left = hardwareMap.dcMotor.get("left");
    }

    @Override
    public void loop() {
        left.setPower(1);
    }
}