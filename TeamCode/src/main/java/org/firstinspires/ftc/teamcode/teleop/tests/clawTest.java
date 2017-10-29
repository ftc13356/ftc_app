package org.firstinspires.ftc.teamcode.teleop.tests;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "Claw Test")
@Disabled
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

        armMotor.setDirection(DcMotor.Direction.REVERSE);
        double leftposition = 0;
        double rightposition = 1;

        waitForStart();

        while (opModeIsActive())
        {
            if(gamepad1.a)
            {
                leftposition = 0.6;
                telemetry.addData("Servo Status", "Closed");
            }

            else if(gamepad1.b)
            {
                leftposition = 0;
                telemetry.addData("Servo Status", "Open");
            }

            double armMotorPower = gamepad1.left_stick_y * 0.5;
            armMotor.setPower(armMotorPower);
            clawLeft.setPosition(leftposition);
            rightposition = 1-leftposition;
            clawRight.setPosition(rightposition);

            telemetry.addData("Motor", armMotorPower);
            telemetry.update();

            idle();
        }
    }
}
