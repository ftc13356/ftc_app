package org.firstinspires.ftc.teamcode.RoverRuckus.teleop;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name = "Hex Chassis")
@Disabled
public class chassisHex extends OpMode {

    private DcMotor motorLeftFront;
    private DcMotor motorRightFront;
    private DcMotor motorLeftBack;
    private DcMotor motorRightBack;

    private double speedControl = 0.5;

    private OpMode op;
    chassisHex(OpMode opMode) {
        op = opMode;
    }

    public void init() {

        motorLeftFront = op.hardwareMap.dcMotor.get("motorLeftFront");
        motorRightFront = op.hardwareMap.dcMotor.get("motorRightFront");
        motorLeftBack = op.hardwareMap.dcMotor.get("motorLeftBack");
        motorRightBack = op.hardwareMap.dcMotor.get("motorRightBack");

        motorLeftFront.setDirection(DcMotor.Direction.REVERSE);
        motorRightBack.setDirection(DcMotor.Direction.REVERSE);
    }

    public void loop() {
        double motorLeftFrontPower;
        double motorRightFrontPower;
        double motorLeftBackPower;
        double motorRightBackPower;

        double driveFW = op.gamepad1.left_stick_y;
        double turn  = op.gamepad1.right_stick_x;

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
