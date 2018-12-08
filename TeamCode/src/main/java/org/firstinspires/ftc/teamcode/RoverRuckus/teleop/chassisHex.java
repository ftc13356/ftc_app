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

    private int display = 0;

    private int hold = 0;

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

        // This sets the speed mode to fast when the "A" button is pressed (Full Speed)
        if (op.gamepad1.a) {
            speedControl = 1;
            display = 0;
            hold = 0;
        }
        // This sets the speed mode to medium when the "X" button is pressed (Medium Speed)
        else if (op.gamepad1.x) {
            speedControl = 0.5;
            display = 1;
            hold = 1;
        }
        // This sets the speed mode to slow when the "B" button is pressed (Slow Speed)
        else if (op.gamepad1.b) {
            speedControl = 0.25;
            display = 2;
            hold = 2;
        }
        // This sets the speed mode to micro-adjustment speed when the left trigger is held down
        else if(op.gamepad1.left_trigger != 0) {
            speedControl = 0.15;
            display = 3;
        }

        // Allows micro-adjustment mode to work only when left trigger is held down
        else if(op.gamepad1.left_trigger == 0) {
            if (hold == 0) {
                speedControl = 1;
                display = 0;
            }
            if (hold == 1) {
                speedControl = 0.5;
                display = 1;
            }
            if (hold == 2) {
                speedControl = 0.25;
                display = 2;
            }
        }

        motorLeftFrontPower = Range.clip((driveFW - turn) * speedControl, -1.0, 1.0);
        motorRightFrontPower = Range.clip((-driveFW - turn) * speedControl, -1.0, 1.0);
        motorLeftBackPower = Range.clip((driveFW - turn) * speedControl, -1.0, 1.0);
        motorRightBackPower = Range.clip((-driveFW - turn) * speedControl, -1.0, 1.0);

        motorLeftFront.setPower(motorLeftFrontPower);
        motorRightFront.setPower(motorRightFrontPower);
        motorLeftBack.setPower(motorLeftBackPower);
        motorRightBack.setPower(motorRightBackPower);

        if (display == 0) {
            op.telemetry.addData("SpeedMode", "Fast");
        }
        else if (display == 1) {
            op.telemetry.addData("SpeedMode", "Medium");
        }
        else if (display == 2) {
            op.telemetry.addData("SpeedMode", "Slow");
        }
        else if (display == 3) {
            op.telemetry.addData("SpeedMode", "Micro-Adjustment");
        }
    }
}
