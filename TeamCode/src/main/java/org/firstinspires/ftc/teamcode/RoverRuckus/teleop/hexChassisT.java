package org.firstinspires.ftc.teamcode.RoverRuckus.teleop;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
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

    private DcMotor motorLeftFront;
    private DcMotor motorRightFront;
    private DcMotor motorLeftBack;
    private DcMotor motorRightBack;

    private double speedControl = 0.5;

    private int display = 0;

    private OpMode op;
    /**
     * Constructs a hex chassis object
     * @param opMode the main program usually "this"
     */
    public hexChassisT(OpMode opMode) {
        op = opMode;
    }

    /**
     * Map chassis motors to their names in the robot config file and sets their direction
     */
    public void init() {
        motorLeftFront = op.hardwareMap.dcMotor.get("motorLeftFront");
        motorRightFront = op.hardwareMap.dcMotor.get("motorRightFront");
        motorLeftBack = op.hardwareMap.dcMotor.get("motorLeftBack");
        motorRightBack = op.hardwareMap.dcMotor.get("motorRightBack");

        motorLeftFront.setDirection(DcMotor.Direction.REVERSE);
        motorRightBack.setDirection(DcMotor.Direction.REVERSE);
    }

    /**
     * Allows the robot to be driven based on controller input
     * <p> 3 speed modes- slow, medium, and fast
     */
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
