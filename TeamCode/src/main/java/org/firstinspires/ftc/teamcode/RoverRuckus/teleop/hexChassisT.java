package org.firstinspires.ftc.teamcode.RoverRuckus.teleop;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.MotorControlAlgorithm;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;
import com.qualcomm.robotcore.util.Range;

/**
 * <h2>Hex Motor Chassis</h2>
 * Purpose:
 * <p> Contains the variables and functions specific to an all hex motor chassis
 * <p> so that we can switch from an all andymark motor chassis without changing any code!
 */

@TeleOp(name = "Hex Chassis")
@Disabled
public class hexChassisT extends OpMode {

    private DcMotorEx motorLeftFront;
    private DcMotorEx motorRightFront;
    private DcMotorEx motorLeftBack;
    private DcMotorEx motorRightBack;

    static final double LF_P = 5;
    static final double LF_I = 1;
    static final double LF_D = 5;
    static final double LF_F = 0;

    static final double LB_P = 5;
    static final double LB_I = 1;
    static final double LB_D = 5;
    static final double LB_F = 0;

    static final double RF_P = 1;
    static final double RF_I = 0.01;
    static final double RF_D = 0.05;
    static final double RF_F = 0;

    static final double RB_P = 1;
    static final double RB_I = 0.01;
    static final double RB_D = 0.05;
    static final double RB_F = 0;

    private double speedControl = 0.5;

    private int display = 0;

    private int hold = 0;

    private OpMode op;
    public hexChassisT(OpMode opMode) {
        op = opMode;
    }

    public void init() {
        motorLeftFront = (DcMotorEx) op.hardwareMap.dcMotor.get("motorLeftFront");
        motorRightFront = (DcMotorEx) op.hardwareMap.dcMotor.get("motorRightFront");
        motorLeftBack = (DcMotorEx) op.hardwareMap.dcMotor.get("motorLeftBack");
        motorRightBack = (DcMotorEx) op.hardwareMap.dcMotor.get("motorRightBack");

        motorLeftFront.setDirection(DcMotor.Direction.REVERSE);
        motorRightBack.setDirection(DcMotor.Direction.REVERSE);

        PIDFCoefficients pidNewLF = new PIDFCoefficients(LF_P, LF_I, LF_D, LF_F, MotorControlAlgorithm.LegacyPID);
        motorLeftFront.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, pidNewLF);

        PIDFCoefficients pidNewLB = new PIDFCoefficients(LB_P, LB_I, LB_D, LB_F, MotorControlAlgorithm.LegacyPID);
        motorLeftBack.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, pidNewLB);

        PIDFCoefficients pidNewRF = new PIDFCoefficients(RF_P, RF_I, RF_D, RF_F, MotorControlAlgorithm.LegacyPID);
        motorRightFront.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, pidNewRF);

        PIDFCoefficients pidNewRB = new PIDFCoefficients(RB_P, RB_I, RB_D, RB_F, MotorControlAlgorithm.LegacyPID);
        motorRightBack.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, pidNewRB);
    }

    public void loop() {
        double motorLeftFrontPower;
        double motorRightFrontPower;
        double motorLeftBackPower;
        double motorRightBackPower;

        double driveFW = op.gamepad1.left_stick_y;
        double turn  = op.gamepad1.right_stick_x;

        // This sets the speed mode to fast when the "A" button is pressed (Full Speed)
        if (op.gamepad1.a) {
            speedControl = 1;
            display = 0;
        }
        // This sets the speed mode to medium when the "X" button is pressed (Medium Speed)
        else if (op.gamepad1.x) {
            speedControl = 0.5;
            display = 1;
        }
        // This sets the speed mode to slow when the "B" button is pressed (Slow Speed)
        else if (op.gamepad1.b) {
            speedControl = 0.25;
            display = 2;
        }

        motorLeftFrontPower = Range.clip((driveFW - turn) * speedControl, -1.0, 1.0);
        motorRightFrontPower = Range.clip((-driveFW - turn) * speedControl, -1.0, 1.0);
        motorLeftBackPower = Range.clip((driveFW - turn) * speedControl, -1.0, 1.0);
        motorRightBackPower = Range.clip((-driveFW - turn) * speedControl, -1.0, 1.0);

        motorLeftFront.setPower(motorLeftFrontPower);
        motorRightFront.setPower(motorRightFrontPower);
        motorLeftBack.setPower(motorLeftBackPower);
        motorRightBack.setPower(motorRightBackPower);

        if (display == 0) {
            op.telemetry.addData("SpeedMode", "Fast");
        }
        else if (display == 1) {
            op.telemetry.addData("SpeedMode", "Medium");
        }
        else if (display == 2) {
            op.telemetry.addData("SpeedMode", "Slow");
        }
    }
}
