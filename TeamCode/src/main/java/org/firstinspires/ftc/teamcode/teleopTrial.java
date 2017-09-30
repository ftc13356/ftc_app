package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name = "Teleop Trial")
public class teleopTrial extends LinearOpMode
{
    private DcMotor motorLeftfront;
    //private DcMotor motorRightfront;
    //private DcMotor motorLeftback;
    //private DcMotor motorRightback;

    @Override
    public void runOpMode() throws InterruptedException
    {

        motorLeftfront = hardwareMap.dcMotor.get("motorLeftfront");
        //motorRightfront = hardwareMap.dcMotor.get("motorRightfront");
        //motorLeftback = hardwareMap.dcMotor.get("motorLeftback");
        //motorRightback = hardwareMap.dcMotor.get("motorRightback");

        //motorLeftfront.setDirection(DcMotor.Direction.REVERSE);
        //motorRightback.setDirection(DcMotor.Direction.REVERSE);

        waitForStart();
        while (opModeIsActive())
        {

            motorLeftfront.setPower(-gamepad1.left_stick_y);
            //motorRightfront.setPower(-gamepad1.right_stick_y);
            //motorLeftback.setPower(-gamepad1.left_stick_y);
            //motorRightback.setPower(-gamepad1.right_stick_y);

            idle();
        }
    }
}
