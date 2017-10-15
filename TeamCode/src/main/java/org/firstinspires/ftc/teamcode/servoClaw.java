package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "Claw Arm Test")
@Disabled
public class servoClaw extends LinearOpMode
    {

    private Servo clawLeft;
    private Servo clawRight;

    private static final double ARM_RETRACTED = 0;
    private static final double ARM_EXTENDED = 1;

    @Override
    public void  runOpMode() throws InterruptedException
    {

        clawLeft = hardwareMap.servo.get("clawLeft");
        clawRight = hardwareMap.servo.get("clawRight");

        clawLeft.setPosition(ARM_RETRACTED);
        clawRight.setPosition(ARM_RETRACTED);

        waitForStart();

        while (opModeIsActive())
        {
            if(gamepad1.a)
            {
                clawLeft.setPosition(ARM_RETRACTED);
                clawRight.setPosition(ARM_RETRACTED);
                telemetry.addData("Status", "Retracted");
                telemetry.update();
            }

            if(gamepad1.b)
            {
                clawLeft.setPosition(ARM_EXTENDED);
                clawRight.setPosition(ARM_EXTENDED);
                telemetry.addData("Status", "Extended");
                telemetry.update();
            }

            telemetry.addData("Servo", "%2f");
            telemetry.update();

            idle();
        }
    }
}
