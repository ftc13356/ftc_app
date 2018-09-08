package org.firstinspires.ftc.teamcode.RelicRecovery.teleop.outreach.att;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name = "Arm")
@Disabled
public class ArmATT {

    private Servo rightDrummer;
    private Servo leftDrummer;

    private double upPosition = 0;
    private double downPosition = 0.5;

    private double leftPosition = 0;
    private double rightPosition = 0.5;

    private ElapsedTime runtime = new ElapsedTime();

    private OpMode op;

    ArmATT(OpMode opmode){
        op = opmode;
    }

    public void init(){
        op.telemetry.addData("Arm", "Initializing");

        rightDrummer = op.hardwareMap.servo.get("rightDrummer");
        leftDrummer = op.hardwareMap.servo.get("leftDrummer");

        op.telemetry.addData("Arm", "Initialized");

    }

    public void init_loop(){}

    public void start(){
        runtime.reset();
    }

    double timeLeft;
    double startTime = runtime.seconds();

    public void loop(){
        if (op.gamepad1.a) {
           leftPosition = upPosition;
           op.telemetry.addData("Servo Status", "Left Up");
           op.telemetry.addData("Button Pressed", "A");
        }
        if (op.gamepad1.b) {
            leftPosition = downPosition;
            op.telemetry.addData("Servo Status", "Left Down");
            op.telemetry.addData("Button Pressed", "B");
        }
        if (op.gamepad1.x) {
            rightPosition = 1 - upPosition;
            op.telemetry.addData("Servo Status", "Right Up");
            op.telemetry.addData("Button Pressed", "X");
        }
        if (op.gamepad1.y) {
            rightPosition = 1- downPosition;
            op.telemetry.addData("Servo Status", "Right Down");
            op.telemetry.addData("Button Pressed", "Y");
        }

        timeLeft = 60 + startTime - op.getRuntime();
        if (timeLeft <= 0) {
            leftDrummer.setPosition(0);
            rightDrummer.setPosition(0.5);
        }

        leftDrummer.setPosition(leftPosition);
        rightDrummer.setPosition(rightPosition);
        leftPosition = leftDrummer.getPosition();
        rightPosition = rightDrummer.getPosition();

        op.telemetry.addData("Arm Status", "Time Left: " + timeLeft);

        op.telemetry.addData("Left Servo Position", leftPosition);
        op.telemetry.addData("Right Servo Position", rightPosition);

    }
}
