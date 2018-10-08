package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;


@TeleOp(name="Telephone")
public class TelephoneOperator extends OpMode {
    DcMotor Left;
    DcMotor Right;
    @Override
    public void init() {
        Left = hardwareMap.dcMotor.get("motorLeft");
        Right = hardwareMap.dcMotor.get("motorRight");
    }

    @Override
    public void loop() {
        Left.setPower((gamepad1.left_stick_y-gamepad1.right_stick_x)/5);
        Right.setPower((-gamepad1.left_stick_y-gamepad1.right_stick_x)/2);

        if(gamepad1.a) {
            telemetry.addData("BreakingNews", "Krishna IQ: 1/0");
        }
        else {
            telemetry.addData("BreakingNews", "TRUMP");
        }
    }


}
