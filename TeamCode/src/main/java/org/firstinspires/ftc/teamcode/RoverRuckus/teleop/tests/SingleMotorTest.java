package org.firstinspires.ftc.teamcode.RoverRuckus.teleop.tests;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name = "Intake Mechanism Test")
public class SingleMotorTest extends OpMode {

    private DcMotor LeftFront;

    public void init() {

        LeftFront = hardwareMap.dcMotor.get("LeftFront");
    }

    public void loop() {
        LeftFront.setPower(gamepad1.right_stick_y);
        telemetry.addData("Motor Speed", 0.2*gamepad1.right_stick_y);
    }
}
