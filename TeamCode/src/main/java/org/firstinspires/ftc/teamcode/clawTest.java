package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "Claw Test")
// @Disabled
public class clawTest extends LinearOpMode
    {
    private DcMotor armMotor;
    private Servo clawLeft;
    private Servo clawRight;

    private static final double ARM_RETRACTED = 0;
    private static final double ARM_EXTENDED = 1;

    @Override
    public void  runOpMode() throws InterruptedException
    {

        clawLeft = hardwareMap.servo.get("clawLeft");
        clawRight = hardwareMap.servo.get("clawRight");
        armMotor = hardwareMap.get(DcMotor.class, "armMotor");

        clawLeft.setPosition(ARM_RETRACTED);
        clawRight.setPosition(ARM_RETRACTED);
        armMotor.setDirection(DcMotor.Direction.FORWARD);

        waitForStart();

        while (opModeIsActive())
        {
            if(gamepad1.a)
            {
                clawLeft.setPosition(ARM_RETRACTED);
                clawRight.setPosition(ARM_EXTENDED);
                telemetry.addData("Servo Status", "Close");
                telemetry.update();
            }

            if(gamepad1.b)
            {
                clawLeft.setPosition(ARM_EXTENDED);
                clawRight.setPosition(ARM_RETRACTED);
                telemetry.addData("Servo Status", "Open");
                telemetry.update();
            }

            double armMotorPower = gamepad1.left_stick_y * 0.5;
            armMotor.setPower(armMotorPower);

            telemetry.addData("Motor", armMotorPower);
            telemetry.update();

            idle();
        }
    }
}
