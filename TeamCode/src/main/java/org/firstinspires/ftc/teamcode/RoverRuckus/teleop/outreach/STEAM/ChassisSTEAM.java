package org.firstinspires.ftc.teamcode.RoverRuckus.teleop.outreach.STEAM;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.MotorControlAlgorithm;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name = "Chassis")
@Disabled
public class ChassisSTEAM extends OpMode {

    private DcMotor motorLeftFront;
    private DcMotor motorRightFront;
    private DcMotor motorLeftBack;
    private DcMotor motorRightBack;

    private ElapsedTime runtime = new ElapsedTime();

    private OpMode op;

    public ChassisSTEAM(OpMode opMode) {
        op = opMode;
    }

    public void init() {
        motorLeftFront = (DcMotor) op.hardwareMap.dcMotor.get("motorLeftFront");
        motorRightFront = (DcMotor) op.hardwareMap.dcMotor.get("motorRightFront");
        motorLeftBack = (DcMotor) op.hardwareMap.dcMotor.get("motorLeftBack");
        motorRightBack = (DcMotor) op.hardwareMap.dcMotor.get("motorRightBack");

        motorLeftFront.setDirection(DcMotor.Direction.REVERSE);
        motorRightBack.setDirection(DcMotor.Direction.REVERSE);
    }

    public void start() {
        runtime.reset();
    }

    double timeLeft;
    double startTime = runtime.seconds();

    public void loop() {
        double motorLeftFrontPower;
        double motorRightFrontPower;
        double motorLeftBackPower;
        double motorRightBackPower;

        double driveFW = op.gamepad1.left_stick_y;
        double turn  = op.gamepad1.right_stick_x;

        motorLeftFrontPower = Range.clip(driveFW - turn, -1.0, 1.0);
        motorRightFrontPower = Range.clip(-driveFW - turn, -1.0, 1.0);
        motorLeftBackPower = Range.clip(driveFW - turn, -1.0, 1.0);
        motorRightBackPower = Range.clip(-driveFW - turn, -1.0, 1.0);

        timeLeft = 60 + startTime - op.getRuntime();
        if (timeLeft <= 0) {
            motorLeftFrontPower = 0;
            motorRightFrontPower = 0;
            motorLeftBackPower = 0;
            motorRightBackPower = 0;
        }

        motorLeftFront.setPower(motorLeftFrontPower);
        motorRightFront.setPower(motorRightFrontPower);
        motorLeftBack.setPower(motorLeftBackPower);
        motorRightBack.setPower(motorRightBackPower);

        op.telemetry.addData("Driving Status", "Time Left: " + timeLeft);
    }
}