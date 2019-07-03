package org.firstinspires.ftc.teamcode.RoverRuckus.teleop.outreach.ATT;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

/**
 * Contains chassis functions for AT&T Discovery Day Outreach program
 */

@TeleOp(name = "Chassis")
@Disabled
public class ChassisATT extends OpMode{

    private DcMotor motorLeftfront;
    private DcMotor motorRightfront;
    private DcMotor motorLeftback;
    private DcMotor motorRightback;

    private double speedControl = 0.4;

    private ElapsedTime runtime = new ElapsedTime();

    private OpMode op;
    ChassisATT(OpMode opmode){
        op = opmode;
    }

    public void init() {

        op.telemetry.addData("Chassis", "Initializing");

        motorLeftfront = op.hardwareMap.get(DcMotor.class, "motorLeftFront");
        motorRightfront = op.hardwareMap.get(DcMotor.class, "motorRightFront");
        motorLeftback = op.hardwareMap.get(DcMotor.class, "motorLeftBack");
        motorRightback = op.hardwareMap.get(DcMotor.class, "motorRightBack");

        // This is just telling the direction of the motors.
        motorLeftfront.setDirection(DcMotor.Direction.FORWARD);
        motorRightfront.setDirection(DcMotor.Direction.FORWARD);
        motorLeftback.setDirection(DcMotor.Direction.FORWARD);
        motorRightback.setDirection(DcMotor.Direction.FORWARD);

        op.telemetry.addData("Chassis", "Initialized");
    }

    public void start() {
        runtime.reset();
    }

    double timeLeft;
    double startTime = runtime.seconds();

    public void loop() {

        double motorLeftfrontPower;
        double motorRightfrontPower;
        double motorLeftbackPower;
        double motorRightbackPower;

        double driveFW = op.gamepad1.left_stick_y;
        double turn = op.gamepad1.right_stick_x;

        motorLeftfrontPower = Range.clip((driveFW - turn) * speedControl, -1.0, 1.0) ;
        motorRightfrontPower = Range.clip((-driveFW - turn) * speedControl, -1.0, 1.0) ;
        motorLeftbackPower = Range.clip((driveFW - turn) * speedControl, -1.0, 1.0) ;
        motorRightbackPower = Range.clip((-driveFW - turn) * speedControl, -1.0, 1.0) ;

        timeLeft = 60 + startTime - op.getRuntime();
        if (timeLeft <= 0) {
            motorLeftfrontPower = 0;
            motorRightfrontPower = 0;
            motorLeftbackPower = 0;
            motorRightbackPower = 0;
        }

        if (op.gamepad2.dpad_up) {
            timeLeft = timeLeft + 5;
        }
        if (op.gamepad2.dpad_down) {
            timeLeft = timeLeft - 5;
        }

        motorLeftfront.setPower(motorLeftfrontPower);
        motorRightfront.setPower(motorRightfrontPower);
        motorLeftback.setPower(motorLeftbackPower);
        motorRightback.setPower(motorRightbackPower);

        if (timeLeft >= 0) {
            op.telemetry.addData("Driving Status", "Time Left- %.1f", timeLeft);
        }

        if (timeLeft <= 0) {
            motorLeftfront.setPower(0);
            motorRightfront.setPower(0);
            motorLeftback.setPower(0);
            motorRightback.setPower(0);
            op.telemetry.addData("Driving Status", "Disabled");
        }
    }
}
