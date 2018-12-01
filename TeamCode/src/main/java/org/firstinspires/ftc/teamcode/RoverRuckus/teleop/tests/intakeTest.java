package org.firstinspires.ftc.teamcode.RoverRuckus.teleop.tests;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;

@TeleOp(name = "Intake Mechanism Test")
@Disabled
public class intakeTest extends OpMode {

    private CRServo left;
    private CRServo right;

    public void init() {

        left = hardwareMap.crservo.get("leftRoller");
        right = hardwareMap.crservo.get("rightRoller");
    }

    public void loop() {
        double power = gamepad1.left_stick_y;

        left.setPower(power);
        right.setPower(-power);
    }
}
