package org.firstinspires.ftc.teamcode.offSeason.autonomous.tests;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.offSeason.autonomous.autonomousFrame;

@Autonomous(name = "Multitasking test")
public class multitaskTest extends autonomousFrame {

    private ElapsedTime moveForwardTimer = new ElapsedTime();
    private ElapsedTime moveColorArmTimer = new ElapsedTime();

    private boolean doneMoveForward = false;
    private boolean doneMoveColorArm = false;

    public void runOpMode() {

        initializeHardwareMap();
        setMotorDirection();

        gripGlyphHolonomic();

        waitForStart();

        while (true) {

            telemetry.addData("Timer", "Move Forward: " + moveForwardTimer);
            telemetry.addData("Timer", "Move Color Arm: " + moveColorArmTimer);
            telemetry.update();

            if (moveColorArmTimer.milliseconds() > 4400) {
                colorArm.setPower(0);
                doneMoveColorArm = true;
                telemetry.addData("Done", "Moving Color Arm");
                telemetry.update();
            }else {
                colorArm.setPower(-1);
            }
            if (moveForwardTimer.seconds() > 10) {
                encoderDrive(0,0,0,0);
                doneMoveForward = true;
                telemetry.addData("Done", "Moving Forward");
                telemetry.update();
            }else {
                encoderDrive(5, 0,0, 0.3);
            }
            if (doneMoveForward && doneMoveColorArm) {
                telemetry.addData("Tasks", "Done");
                break;
            }
        }

        retractColorArm();
        stop();

    }
}
