package org.firstinspires.ftc.teamcode.season.autonomous.tests;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;

@Autonomous(name = "Glyph Servo Test")
@Disabled
public class glyphServoTest extends LinearOpMode {

    private Servo glyphClawRight;
    private Servo glyphClawLeft;

    private double leftPosition = 0.3;
    private double rightPosition = 0.7;

    @Override
    public void  runOpMode() throws InterruptedException
    {

        glyphClawLeft = hardwareMap.servo.get("glyphClawLeft");
        glyphClawRight = hardwareMap.servo.get("glyphClawRight");

        glyphClawLeft.setPosition(1);
        glyphClawRight.setPosition(0);

        waitForStart();

        glyphClawLeft.setPosition(leftPosition);
        glyphClawRight.setPosition(rightPosition);
        glyphClawLeft.getPosition();
        glyphClawRight.getPosition();

        telemetry.addData("Claw Left", glyphClawLeft.getPosition());
        telemetry.addData("Claw Right", glyphClawRight.getPosition());
        telemetry.update();


    }
}
