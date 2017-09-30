package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name = "Teleop Trial")
public class teleopTrial extends LinearOpMode
{
    private DcMotor motorLeftfront;
    private DcMotor motorRightfront;
    private DcMotor motorLeftback;
    private DcMotor motorRightback;

    @Override
    public void runOpMode() throws InterruptedException
    {

        motorLeftfront = hardwareMap.dcMotor.get("motorLeftfront");
        motorRightfront = hardwareMap.dcMotor.get("motorRightfront");
        motorLeftback = hardwareMap.dcMotor.get("motorLeftback");
        motorRightback = hardwareMap.dcMotor.get("motorRightback");

        /**public void DriveFW(double power, long time)
        {
            motorLeftfront.setPower(-power);
            motorRightfront.setPower(power);
            motorLeftback.setPower(-power);
            motorRightback.setPower(power);
            Thread.sleep(time);
        }

        public void DriveLR(double power, long time)
        {
            motorLeftfront.setPower(-power);
            motorRightfront.setPower(-power);
            motorLeftback.setPower(power);
            motorRightback.setPower(power);
            Thread.sleep(time);
        }

        public void Turn(double power, long time)
        {
            motorLeftfront.setPower(power);
            motorRightfront.setPower(power);
            motorLeftback.setPower(power);
            motorRightback.setPower(power);
            Thread.sleep(time);
        }

        public void StopMotors()
        {
            motorLeftfront.setPower(0);
            motorRightfront.setPower(0);
            motorLeftback.setPower(0);
            motorRightback.setPower(0);
        }**/

        waitForStart();
        while (teleopTrial())
        {

            DriveFW(1, 5000);
            Turn(1, 5000);
            DriveLR(1,5000);
            StopMotors();
            //motorLeftfront.setPower(-gamepad1.left_stick_y);
            //motorRightfront.setPower(-gamepad1.right_stick_y);
            //motorLeftback.setPower(-gamepad1.left_stick_y);
            //motorRightback.setPower(-gamepad1.right_stick_y);

            idle();
        }
    }
}
