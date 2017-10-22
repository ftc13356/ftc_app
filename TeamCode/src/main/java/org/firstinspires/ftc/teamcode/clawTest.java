package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "Claw Test")
// @Disabled
public class clawTest extends LinearOpMode
    {
    private DcMotor armMotor;
    private Servo clawLeft;
    private Servo clawRight;

    @Override
    public void  runOpMode() throws InterruptedException
    {
        clawLeft = hardwareMap.servo.get("clawLeft");
        clawRight = hardwareMap.servo.get("clawRight");
        armMotor = hardwareMap.get(DcMotor.class, "armMotor");

        double leftoriginal = clawLeft.getPosition();
        double rightoriginal = clawRight.getPosition();
        double leftposition = clawLeft.getPosition();
        double rightposition = clawRight.getPosition();
        double accuracy = 0.25;
        armMotor.setDirection(DcMotor.Direction.REVERSE);

        waitForStart();

        while (opModeIsActive())
        {
            if(gamepad1.a)
            {
                clawLeft.setPosition(leftoriginal+accuracy);
                clawRight.setPosition(rightoriginal-accuracy);
                leftposition = clawLeft.getPosition();
                rightposition = clawRight.getPosition();
                telemetry.addData("Left Servo Position", leftposition);
                telemetry.addData("Right Servo Position", rightposition);
                telemetry.update();
            }

            else if(gamepad1.b)
            {
                leftoriginal = clawLeft.getPosition();
                rightoriginal = clawRight.getPosition();
                clawLeft.setPosition(leftoriginal+accuracy);
                clawRight.setPosition(rightoriginal-accuracy);
                leftposition = clawLeft.getPosition();
                rightposition = clawRight.getPosition();
                telemetry.addData("Left Servo Position", leftposition);
                telemetry.addData("Right Servo Position", rightposition);
                telemetry.update();
            }

            double armMotorPower = gamepad1.left_stick_y * 0.5;
            armMotor.setPower(armMotorPower);

            telemetry.addData("Motor", armMotorPower);
            telemetry.addData("Left Servo Position", leftposition);
            telemetry.addData("Right Servo Position", rightposition);
            telemetry.addData("Left Servo Original Position", leftoriginal);
            telemetry.addData("Right Servo Original Position", rightoriginal);
            telemetry.update();

            idle();
        }
    }
}
