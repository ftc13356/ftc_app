package org.firstinspires.ftc.teamcode.RoverRuckus.teleop;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * <h2>Intake</h2>
 * Purpose:
 * <p>
 *
 * temp: This is the class which contains the code for the intake.
 */

@TeleOp(name = "Intake")
@Disabled
public class intakeShooter extends OpMode {

    // GO TO
    // teleopMain.java TO
    // UPDATE VERSION NUMBER
    // BEFORE EVERY COMMIT

    private DcMotor intakeAngleMotor;
<<<<<<< HEAD:TeamCode/src/main/java/org/firstinspires/ftc/teamcode/RoverRuckus/teleop/intake.java
    private DcMotor shooter;
=======
    private DcMotor shooterMotor;
>>>>>>> 6975743fd35767664f36561935b886873269da8d:TeamCode/src/main/java/org/firstinspires/ftc/teamcode/RoverRuckus/teleop/intakeShooter.java
    private CRServo leftIntake;
    private CRServo rightIntake;

    private double speedControl = 0.35;
    private double armMotorPower = 0;
    private double colorArmPower = 0;
    private int currentArmPosition = 0;

    private final int armDownLevel = -1500;
    private final int armUpLevel = 0;
    private final int encoderPositionError = 100;

    private OpMode op;
    intakeShooter(OpMode opMode) {
        op = opMode;
    }

    public void init() {

        intakeAngleMotor = op.hardwareMap.dcMotor.get("intakeAngle");
<<<<<<< HEAD:TeamCode/src/main/java/org/firstinspires/ftc/teamcode/RoverRuckus/teleop/intake.java
        shooter = op.hardwareMap.dcMotor.get("shooter");
=======
        shooterMotor = op.hardwareMap.dcMotor.get("shooter");
>>>>>>> 6975743fd35767664f36561935b886873269da8d:TeamCode/src/main/java/org/firstinspires/ftc/teamcode/RoverRuckus/teleop/intakeShooter.java
        leftIntake = op.hardwareMap.crservo.get("leftIntake");
        rightIntake = op.hardwareMap.crservo.get("rightIntake");

        intakeAngleMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public void loop() {

        double intakePower = 0;
        double shooterPower = 0;

        // Left stick y-axis controls intake's angle
        double intakeAngleMotorPower = -op.gamepad2.left_stick_y * speedControl;

<<<<<<< HEAD:TeamCode/src/main/java/org/firstinspires/ftc/teamcode/RoverRuckus/teleop/intake.java
        if (op.gamepad2.right_trigger!=0) {
            shooterPower = -0.5;
        }
=======
        double shooterMotorPower = -op.gamepad2.right_trigger;
>>>>>>> 6975743fd35767664f36561935b886873269da8d:TeamCode/src/main/java/org/firstinspires/ftc/teamcode/RoverRuckus/teleop/intakeShooter.java

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
<<<<<<< HEAD:TeamCode/src/main/java/org/firstinspires/ftc/teamcode/RoverRuckus/teleop/intake.java
        shooter.setPower(shooterPower);
=======
        shooterMotor.setPower(shooterMotorPower);
>>>>>>> 6975743fd35767664f36561935b886873269da8d:TeamCode/src/main/java/org/firstinspires/ftc/teamcode/RoverRuckus/teleop/intakeShooter.java
    }
}
