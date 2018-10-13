package org.firstinspires.ftc.teamcode.RoverRuckus.autonomous.tests;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

///////////////////////////////////////////////////////////////////////////////
// Purpose: Autonomous Program Test
// Author: Ansh Gandhi, Jonathan Ma
///////////////////////////////////////////////////////////////////////////////

//Motor powers per encoderDrive();
//forward -+-+
//backward +-+-
//left ++-- (+)
//right--++ (-)

@Autonomous(name = "Test")
public class autonomousTest extends LinearOpMode {

    DcMotor motorLeft;
    DcMotor motorRight;

    @Override
    public void runOpMode() {

        motorLeft = hardwareMap.dcMotor.get("motorLeft");
        motorRight = hardwareMap.dcMotor.get("motorRight");

        waitForStart();

        //Next to depot
        forward(0.5,1000); //15 inches forward
        right(0.5,1000); // 90 degrees right
        forward(0.5,2000); //30 inches forward
        left(0.5,1500); // 135 degrees left
        forward(0.5,3000); //45 inches forward

        //Next to crater
        forward(0.5,1000); //15 inches forward
        left(0.5,1000); // 90 degrees left
        forward(0.5,2500); //40 inches forward
        left(0.5,500); // 45 degrees left
        forward(0.5,3500); //55 inches forward

        telemetry.addData("Status", "Everything executed");
        telemetry.update();

        stop();
    }

    public void forward(double power, long time) {
        motorLeft.setPower(power);
        motorRight.setPower(power);
        sleep(time);
        motorLeft.setPower(0);
        motorRight.setPower(0);
    }

    public void backward(double power, long time) {
        motorLeft.setPower(-power);
        motorRight.setPower(-power);
        sleep(time);
        motorLeft.setPower(0);
        motorRight.setPower(0);
    }

    public void right(double power, long time) {
        motorLeft.setPower(power);
        motorRight.setPower(-power);
        sleep(time);
        motorLeft.setPower(0);
        motorRight.setPower(0);
    }

    public void left(double power, long time) {
        motorLeft.setPower(-power);
        motorRight.setPower(power);
        sleep(time);
        motorLeft.setPower(0);
        motorRight.setPower(0);
    }
}