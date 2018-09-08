package org.firstinspires.ftc.teamcode.RelicRecovery.teleop.tests;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;

@TeleOp(name = "Continuous Servo Test")
@Disabled
public class continuousServoProgram extends OpMode{

    private CRServo glyphClawLeft;
    private CRServo glyphClawRight;
    private double glyphLeftPosition = 0;
    private double glyphRightPosition = 1;

    @Override
    public void init() {
        glyphClawLeft = hardwareMap.crservo.get("glyphClawLeft");
        glyphClawRight = hardwareMap.crservo.get("glyphClawRight");
    }

    @Override
    public void loop() {
        if (gamepad1.dpad_up) {
            glyphLeftPosition = 0;
            glyphRightPosition = 1;
        }

        if (gamepad1.dpad_down) {
            glyphLeftPosition = 1;
            glyphRightPosition = 0;
        }

        if (gamepad1.dpad_left) {
            glyphLeftPosition = -1;
            glyphRightPosition = 1;
        }

        if (gamepad1.dpad_right) {
            glyphLeftPosition = 1;
            glyphRightPosition = -1;
        }
        if (gamepad1.a) {
            glyphLeftPosition = 0.5;
            glyphRightPosition = 0.5;
        }

        if (gamepad1.b) {
            glyphLeftPosition = 0;
            glyphRightPosition = 0;
        }

        glyphClawLeft.setPower(glyphLeftPosition);
        glyphClawRight.setPower(glyphRightPosition);

        telemetry.addData("Left Arm Servo Position", glyphClawLeft.getPower());
        telemetry.addData("Right Arm Servo Position", glyphClawRight.getPower());
    }
}
