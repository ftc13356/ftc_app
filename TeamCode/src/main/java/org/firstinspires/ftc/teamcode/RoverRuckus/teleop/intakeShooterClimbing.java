package org.firstinspires.ftc.teamcode.RoverRuckus.teleop;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * <h2>Intake</h2>
 * Purpose:
 * <p> Contains the functions and variables used for the intake, shooter,
 * and the winch and servo arm for climbing
 */

@TeleOp(name = "Intake")
@Disabled
public class intakeShooterClimbing extends OpMode {

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
    private DcMotor hookAngle;
    private Servo hookDeliver;
    private double hookDeliverPosition = 0.08;

    private OpMode op;
    intakeShooterClimbing(OpMode opMode) {
        op = opMode;
    }

    public void init() {
        intakeAngleMotor = op.hardwareMap.dcMotor.get("intakeAngle");
        shooter = op.hardwareMap.dcMotor.get("shooter");
        winchMotor = op.hardwareMap.dcMotor.get("winchMotor");
        hookAngle = op.hardwareMap.dcMotor.get("hookAngle");
        hookDeliver = op.hardwareMap.servo.get("hookDeliver");

        leftIntake = op.hardwareMap.crservo.get("leftIntake");
        rightIntake = op.hardwareMap.crservo.get("rightIntake");

        intakeAngleMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        hookAngle.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public void loop() {

        // Power variables
        double intakePower = 0;
        double shooterPower = 0;
        double winchPower = 0;
        double hookAnglePower = 0;

        // Left stick y-axis controls intake's angle
        double intakeAngleMotorPower = -op.gamepad2.left_stick_y * speedControl;

        // Right bumper fires shooter
        if (op.gamepad2.left_bumper) {
            shooterPower = -1;
        }

        // Left bumper loosens winch, right bumper tightens it
        if (op.gamepad1.left_bumper) {
            winchPower = 1;
        }
        if (op.gamepad1.right_bumper) {
            winchPower = -1;
        }

        // Left trigger raises climbing arm, right trigger lowers it
        if (op.gamepad1.left_trigger != 0) {
            hookAnglePower = op.gamepad1.left_trigger * 0.3;
        }
        if (op.gamepad1.right_trigger != 0) {
            hookAnglePower = -op.gamepad1.right_trigger * 0.3;
        }

        // (When pressed) Right trigger raises hook, (When released) returns to starting position
        if (op.gamepad2.right_trigger != 0) {
            hookDeliverPosition = 1;
        } else {
            hookDeliverPosition = 0;
        }

        // "A" button sucks in minerals
        if (op.gamepad2.a) {
            intakePower = -1;
        }

        // "Y" button ejects minerals
        if (op.gamepad2.y) {
            intakePower = 1;
        }

        op.telemetry.addData("Current Position", intakeAngleMotor.getCurrentPosition());
        op.telemetry.addData("Hook Deliver Position", hookDeliver.getPosition());

        leftIntake.setPower(intakePower);
        rightIntake.setPower(-intakePower);
        intakeAngleMotor.setPower(intakeAngleMotorPower);

        shooter.setPower(shooterPower);

        winchMotor.setPower(winchPower);
        hookAngle.setPower(hookAnglePower);
        hookDeliver.setPosition(hookDeliverPosition);
    }
}
