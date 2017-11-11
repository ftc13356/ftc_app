package org.firstinspires.ftc.teamcode.teleop.teleop.att;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "Arm")
@Disabled
public class ArmATT {

    private Servo rightDrummer;
    private Servo leftDrummer;

    private double upPosition;
    private double downPosition;

    private OpMode op;

    ArmATT(OpMode opmode){
        op = opmode;
    }

    public void init(){

        rightDrummer = op.hardwareMap.servo.get("rightDrummer");
        leftDrummer = op.hardwareMap.servo.get("leftDrummer");
    }

    public void init_loop(){}

    public void start(){}

    public void loop(){

        if(op.gamepad2.left_bumper) {
            downPosition = upPosition;
            op.telemetry.addData("Servo Status", "Closed");
        }
        clawLeft.setPosition(leftPosition);
        rightPosition = 1 - leftPosition;
        clawRight.setPosition(rightPosition);
        leftPosition = clawLeft.getPosition();
        rightPosition = clawRight.getPosition();

    }
}
