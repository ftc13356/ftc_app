package org.firstinspires.ftc.teamcode.RoverRuckus.teleop.tests;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

///////////////////////////////////////////////////////////////////////////////
// Purpose: Test for Encoder Drive
// Author: Ansh Gandhi, Jonathan Ma
///////////////////////////////////////////////////////////////////////////////

@TeleOp(name = "Encoder Test")
@Disabled
public class encoderTest extends OpMode {

    // Initialize the variables
    private DcMotor LeftFront;
    private DcMotor RightFront;
    private DcMotor LeftBack;
    private DcMotor RightBack;

    int target = 0;
    double speed = 0.5;

    public void init() {

        telemetry.addData("Robot", "Initializing");

        // Initializing the hardware variables
        LeftFront = hardwareMap.dcMotor.get("motorLeftFront");
        RightFront = hardwareMap.dcMotor.get("motorRightFront");
        LeftBack = hardwareMap.dcMotor.get("motorLeftBack");
        RightBack = hardwareMap.dcMotor.get("motorRightBack");

        LeftFront.setDirection(DcMotor.Direction.FORWARD);
        RightFront.setDirection(DcMotor.Direction.FORWARD);
        LeftBack.setDirection(DcMotor.Direction.FORWARD);
        RightBack.setDirection(DcMotor.Direction.FORWARD);

        telemetry.addData("Robot", "Initialized");
    }

    @Override
    public void start() {
        LeftFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        LeftBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        RightFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        RightBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }


    public void loop() {

        if (gamepad1.a){
            target = target + 1500;
        }

        LeftFront.setTargetPosition(target);
        RightFront.setTargetPosition(target);
        LeftBack.setTargetPosition(target);
        RightBack.setTargetPosition(target);


// Changes Motor Mode
        LeftFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        RightFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        LeftBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        RightBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        // Sets Power to speed
        LeftFront.setPower(Math.abs(speed));
        RightFront.setPower(Math.abs(speed));
        LeftBack.setPower(Math.abs(speed));
        RightBack.setPower(Math.abs(speed));

        LeftFront.setPower(0);
        RightFront.setPower(0);
        LeftBack.setPower(0);
        RightBack.setPower(0);

        // Changes Motor Mode
        LeftFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        RightFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        LeftBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        RightBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        telemetry.addData("Current Value", "Running at %7d :%7d: %7d :%7d", LeftFront.getCurrentPosition(), RightFront.getCurrentPosition(), LeftBack.getCurrentPosition(), RightBack.getCurrentPosition());
        telemetry.addData("Target", target);
    }
}
