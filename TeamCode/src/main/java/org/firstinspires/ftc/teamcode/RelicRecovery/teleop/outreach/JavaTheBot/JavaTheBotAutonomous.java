package org.firstinspires.ftc.teamcode.RelicRecovery.teleop.outreach.JavaTheBot;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

@Autonomous(name="JavaTheBotAutonomous")
public class JavaTheBotAutonomous extends LinearOpMode {

    DcMotor left;
    DcMotor right;

    @Override
    public void runOpMode() {

        left = hardwareMap.dcMotor.get("left");
        right = hardwareMap.dcMotor.get("right");

        waitForStart();

        left.setPower(1);
        right.setPower(-1);
        sleep(2000);

        left.setPower(-1);
        right.setPower(1);
        sleep(2000);

        left.setPower(1);
        right.setPower(1);
        sleep(2000);

        stop();

    }
}