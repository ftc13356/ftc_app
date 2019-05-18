package org.firstinspires.ftc.teamcode.RoverRuckus.teleop.outreach.ATT;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name = "Arm")
@Disabled
public class ArmATT extends OpMode{

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

    public void init() {
        op.telemetry.addData("Arm", "Initializing");

        rightDrummer = op.hardwareMap.servo.get("armRight");
        leftDrummer = op.hardwareMap.servo.get("armLeft");

        rightDrummer.setPosition(downPosition);
        leftDrummer.setPosition(downPosition);

        op.telemetry.addData("Arm", "Initialized");
        //leftArm's down is rightArm's up
    }

    public void init_loop() {}

    public void start(){
        runtime.reset();
    }

    double timeLeft;
    double startTime = runtime.seconds();

    public void loop() {
        timeLeft = 60 + startTime - op.getRuntime();

        if (timeLeft >= 0) {
            if (op.gamepad2.a) {
                if (op.gamepad2.left_bumper) {
                    leftPosition = upPosition;
                    leftDrummer.setPosition(leftPosition);
                } else {
                    leftPosition = downPosition;
                    leftDrummer.setPosition(leftPosition);
                }

                if (op.gamepad2.right_bumper) {
                    rightPosition = 1 - upPosition;
                    rightDrummer.setPosition(rightPosition);
                } else {
                    rightPosition = 1 - downPosition;
                    rightDrummer.setPosition(rightPosition);
                }
            } else {
                if (op.gamepad1.left_bumper) {
                    leftPosition = upPosition;
                    leftDrummer.setPosition(leftPosition);
                } else {
                    leftPosition = downPosition;
                    leftDrummer.setPosition(leftPosition);
                }

                if (op.gamepad1.right_bumper) {
                    rightPosition = 1 - upPosition;
                    rightDrummer.setPosition(rightPosition);
                } else {
                    rightPosition = 1 - downPosition;
                    rightDrummer.setPosition(rightPosition);
                }
            }

            leftDrummer.setPosition(leftPosition);
            rightDrummer.setPosition(rightPosition);
            leftPosition = leftDrummer.getPosition();
            rightPosition = rightDrummer.getPosition();

            if (op.gamepad2.dpad_up) {
                timeLeft=timeLeft+5;
            }
            if (op.gamepad2.dpad_down) {
                timeLeft=timeLeft-5;
            }

            op.telemetry.addData("Arm Status", "Time Left- %.1f",  timeLeft);
        }

        if (timeLeft <= 0) {
            op.telemetry.addData("Arm Status", "Disabled");
        }
    }
}
