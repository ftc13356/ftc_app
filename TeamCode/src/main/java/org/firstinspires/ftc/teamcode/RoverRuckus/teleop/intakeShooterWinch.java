package org.firstinspires.ftc.teamcode.RoverRuckus.teleop;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * <h2>Intake</h2>
 * Purpose:
 * <p> Contains the functions and variables used for the intake, shooter, and the winch for climbing
 */

@TeleOp(name = "Intake")
@Disabled
public class intakeShooterWinch extends OpMode {

    // GO TO
    // teleopMain.java TO
    // UPDATE VERSION NUMBER
    // BEFORE EVERY COMMIT

    private CRServo leftIntake;
    private CRServo rightIntake;
    private DcMotor intakeAngleMotor;
    private double speedControl = 0.35;

    private DcMotor shooter;

    private DcMotor winchMotor;

    private OpMode op;
    intakeShooterWinch(OpMode opMode) {
        op = opMode;
    }

    public void init() {
        intakeAngleMotor = op.hardwareMap.dcMotor.get("intakeAngle");
        shooter = op.hardwareMap.dcMotor.get("shooter");
        winchMotor = op.hardwareMap.dcMotor.get("winchMotor");

        leftIntake = op.hardwareMap.crservo.get("leftIntake");
        rightIntake = op.hardwareMap.crservo.get("rightIntake");

        intakeAngleMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public void loop() {

        double intakePower = 0;
        double shooterPower = 0;
        double winchPower;

        // Left stick y-axis controls intake's angle
        double intakeAngleMotorPower = -op.gamepad2.left_stick_y * speedControl;

        if (op.gamepad2.right_trigger !=0 ) {
            shooterPower = -1;
        }

        winchPower = op.gamepad1.right_trigger - op.gamepad1.left_trigger;

        // If a pressed, minerals sucked in
        if (op.gamepad2.a) {
            intakePower = -1;
        }
        // If y pressed, minerals, ejected
        if (op.gamepad2.y) {
            intakePower = 1;
        }

        op.telemetry.addData("Current Position", intakeAngleMotor.getCurrentPosition());

        leftIntake.setPower(intakePower);
        rightIntake.setPower(-intakePower);
        intakeAngleMotor.setPower(intakeAngleMotorPower);

        shooter.setPower(shooterPower);

        winchMotor.setPower(winchPower);
    }
}
