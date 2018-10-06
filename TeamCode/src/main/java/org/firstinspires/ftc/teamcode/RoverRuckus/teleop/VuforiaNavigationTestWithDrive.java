package org.firstinspires.ftc.teamcode.RoverRuckus.teleop;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

///////////////////////////////////////////////////////////////////////////////
// Purpose: Controls Drivetrain of Prototype Robot (All Wheel Drive)
// Author: Ansh Gandhi, Jonathan Ma
///////////////////////////////////////////////////////////////////////////////

@TeleOp(name = "All Wheel Drive Test")
public class VuforiaNavigationTestWithDrive extends OpMode {

    // Initialize the variables
    private DcMotor motorLeft;
    private DcMotor motorRight;

    public void init() {

        telemetry.addData("Robot", "Initializing");

        // Initializing the hardware variables
        motorLeft = hardwareMap.dcMotor.get("motorLeft");
        motorRight = hardwareMap.dcMotor.get("motorRight");

        motorLeft.setDirection(DcMotor.Direction.FORWARD);
        motorRight.setDirection(DcMotor.Direction.FORWARD);

        telemetry.addData("Robot", "Initialized");
    }

    public void init_loop() {
    }

    public void start() {
    }

    public void loop() {
        double motorLeftPower;
        double motorRightPower;

        double driveFW = gamepad1.left_stick_y;
        double turn  = gamepad1.right_stick_x;

        motorLeftPower = Range.clip((driveFW + turn) *0.35, -1.0, 1.0) ;
        motorRightPower = Range.clip((-driveFW + turn) *0.35, -1.0, 1.0) ;

        motorLeft.setPower(motorLeftPower);
        motorRight.setPower(motorRightPower);
        }
    }