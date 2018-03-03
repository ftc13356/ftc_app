package org.firstinspires.ftc.teamcode.season.teleop.tests;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

@TeleOp(name = "Servo Test")
@Disabled
public class servoTest extends LinearOpMode
    {

    private Servo test;

    private static final double ARM_RETRACTED = 0;
    private static final double ARM_EXTENDED = 1;

    @Override
    public void  runOpMode() throws InterruptedException
    {

        test = hardwareMap.servo.get("test");

        test.setPosition(ARM_RETRACTED);

        waitForStart();

        while (opModeIsActive())
        {

            if(gamepad1.a)
            {

                test.setPosition(ARM_RETRACTED);
                telemetry.addData("Status", "Retracted");
                telemetry.update();

            }
            if(gamepad1.b)
            {

                test.setPosition(ARM_EXTENDED);
                telemetry.addData("Status", "Extended");
                telemetry.update();

            }

            telemetry.addData("Servo", "%2f");
            telemetry.update();

            idle();

        }
    }
}
