package org.firstinspires.ftc.teamcode.RoverRuckus.teleop.tests;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name = "core hex test")
public class coreHexTest extends OpMode {

    private DcMotor motorLeftFront;
    private DcMotor motorRightFront;
    private DcMotor motorLeftBack;
    private DcMotor motorRightBack;

    private double speedControl = 0.6;

    public void init() {

        telemetry.addData("Robot", "Initializing");

        motorLeftFront = hardwareMap.dcMotor.get("motorLeftFront");
        motorRightFront = hardwareMap.dcMotor.get("motorRightFront");
        motorLeftBack = hardwareMap.dcMotor.get("motorLeftBack");
        motorRightBack = hardwareMap.dcMotor.get("motorRightBack");

        motorLeftFront.setDirection(DcMotor.Direction.REVERSE);
        motorRightBack.setDirection(DcMotor.Direction.REVERSE);

        telemetry.addData("Robot", "Initialized");
    }

    public void loop() {
        double motorLeftFrontPower;
        double motorRightFrontPower;
        double motorLeftBackPower;
        double motorRightBackPower;

        double driveFW = gamepad1.left_stick_y;
        double turn  = gamepad1.right_stick_x;

        motorLeftFrontPower = Range.clip((driveFW - turn) * speedControl, -1.0, 1.0);
        motorRightFrontPower = Range.clip((-driveFW - turn) * speedControl, -1.0, 1.0);
        motorLeftBackPower = Range.clip((driveFW - turn) * speedControl, -1.0, 1.0);
        motorRightBackPower = Range.clip((-driveFW - turn) * speedControl, -1.0, 1.0);

        motorLeftFront.setPower(motorLeftFrontPower);
        motorRightFront.setPower(motorRightFrontPower);
        motorLeftBack.setPower(motorLeftBackPower);
        motorRightBack.setPower(motorRightBackPower);
    }
}