package org.firstinspires.ftc.teamcode.autonomous.tests;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

@Autonomous(name = "Glyph Servo Test")
// @Disabled
public class glyphServoTest extends LinearOpMode {

    private Servo glyphClawRight;
    private Servo glyphClawLeft;

    private static final double ARM_RETRACTED = 0;
    private static final double ARM_EXTENDED = 1;

    private double glyphLeftPosition;
    private double glyphRightPosition;

    @Override
    public void  runOpMode() throws InterruptedException
    {

        glyphClawLeft = hardwareMap.servo.get("glyphClawLeft");
        glyphClawRight = hardwareMap.servo.get("glyphClawRight");

        glyphClawLeft.setPosition(ARM_RETRACTED);
        glyphClawRight.setPosition(ARM_EXTENDED);

        waitForStart();

        glyphLeftPosition = 0.75;

        glyphClawLeft.setPosition(glyphLeftPosition);
        glyphRightPosition = 1 - glyphLeftPosition;
        glyphClawRight.setPosition(glyphRightPosition);

        telemetry.addData("Servo", "%2f");
        telemetry.update();
    }
}
