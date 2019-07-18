package org.firstinspires.ftc.teamcode.RoverRuckus.teleop.outreach.STEAM;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * Contains intake and shooter functions for STEAM Night Outreach program
 */

@TeleOp(name = "Intake")
@Disabled
public class intakeShooterSTEAM extends OpMode {

    private CRServo leftIntake;
    private CRServo rightIntake;
    private DcMotor intakeAngleMotor;
    private double speedControl = 0.35;
    private DcMotor shooter;

    private ElapsedTime runtime = new ElapsedTime();

    private OpMode op;
    intakeShooterSTEAM(OpMode opMode) {
        op = opMode;
    }

    public void init() {
        intakeAngleMotor = op.hardwareMap.dcMotor.get("intakeAngle");
        shooter = op.hardwareMap.dcMotor.get("shooter");
        leftIntake = op.hardwareMap.crservo.get("leftIntake");
        rightIntake = op.hardwareMap.crservo.get("rightIntake");

        intakeAngleMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public void start() {
        runtime.reset();
    }

    private double timeLeft;
    private double startTime = runtime.seconds();
    public void loop() {

        double intakePower = 0;
        double shooterPower = 0;
        double intakeAngleMotorPower = -op.gamepad2.left_stick_y * speedControl;

        // Right bumper fires shooter
        if (op.gamepad1.left_bumper) {
            shooterPower = -1;
        }

        // "A" button sucks in minerals
        if (op.gamepad1.a) {
            intakePower = -1;
        }

        // "Y" button ejects minerals
        if (op.gamepad1.y) {
            intakePower = 1;
        }

        timeLeft = 60 + startTime - op.getRuntime();

        if (op.gamepad2.dpad_up) {
            timeLeft = timeLeft + 5;
        }
        if (op.gamepad2.dpad_down) {
            timeLeft = timeLeft - 5;
        }

        if (timeLeft <= 0) {
            intakePower = 0;
            intakeAngleMotorPower = 0;
            shooterPower = 0;
            op.telemetry.addData("Intake + Shooting Status", "Disabled");
        }

        leftIntake.setPower(intakePower);
        rightIntake.setPower(-intakePower);
        intakeAngleMotor.setPower(intakeAngleMotorPower);
        shooter.setPower(shooterPower);

        if (timeLeft >= 0) {
            op.telemetry.addData("Intake + Shooting Status", "Time Left- %.1f", timeLeft);
        }
    }
}
