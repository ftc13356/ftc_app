package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name = "Arm Move")
public class armMove extends OpMode{

    private DcMotor armMotor;
    private double armSpeedControl = 0.5;

    @Override
    public void init(){
        armMotor = hardwareMap.get(DcMotor.class, "armMotor");
        armMotor.setDirection(DcMotor.Direction.REVERSE);
    }

    @Override
    public void loop() {
        double armMotorPower = 0;
        armMotorPower = gamepad1.left_stick_y * armSpeedControl;
        armMotor.setPower(armMotorPower);
    }
}
