package org.firstinspires.ftc.teamcode.teleop.outreach.F5_RobotGarden;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

///////////////////////////////////////////////////////////////////////////////
// Purpose: Class for controlling the drivetrain for the 4 omni-wheel drivetrain (Holonomic)
// Author: Jonathan Ma, Ansh Gandhi
///////////////////////////////////////////////////////////////////////////////

@TeleOp(name = "Chassis F5/Robot Garden Edition")
@Disabled
public class chassisF5RobotGarden {

    // Initialize the variables
    private DcMotor motorLeftfront;
    private DcMotor motorRightfront;
    private DcMotor motorLeftback;
    private DcMotor motorRightback;

    private double speedControl = 0.4;

    private ElapsedTime runtime = new ElapsedTime();

    // Creates OpMode
    private OpMode op;
    chassisF5RobotGarden(OpMode opmode) {
        op = opmode;
    }

    public void init() {

        op.telemetry.addData("Chassis", "Initializing");

        // Initializing the hardware variables
        motorLeftfront = op.hardwareMap.dcMotor.get("motorLeftfront");
        motorRightfront = op.hardwareMap.dcMotor.get("motorRightfront");
        motorLeftback = op.hardwareMap.dcMotor.get("motorLeftback");
        motorRightback = op.hardwareMap.dcMotor.get("motorRightback");

        // This tells the direction of the motor
        motorLeftfront.setDirection(DcMotor.Direction.FORWARD);
        motorRightfront.setDirection(DcMotor.Direction.FORWARD);
        motorLeftback.setDirection(DcMotor.Direction.FORWARD);
        motorRightback.setDirection(DcMotor.Direction.FORWARD);

        op.telemetry.addData("Chassis", "Initialized");
    }

    // This code is just waiting for the Play button to be pressed
    public void init_loop() {
    }

    // This code will do something once when the Play button is pressed
    public void start() {
        runtime.reset();
    }

    double timeLeft;
    double startTime = runtime.seconds();

    // This code will run constantly after the previous part is ran
    public void loop() {
        // Some variables are being defined
        double motorLeftfrontPower;
        double motorRightfrontPower;
        double motorLeftbackPower;
        double motorRightbackPower;

        // The left joystick is used to drive forward/backward and sideways while the right joystick is used to turn in place
        double driveFW = op.gamepad1.left_stick_y;
        double driveS = op.gamepad1.left_stick_x;
        double turn  = op.gamepad1.right_stick_x;

        motorLeftfrontPower = Range.clip((driveFW - driveS - turn) *speedControl, -1.0, 1.0) ;
        motorRightfrontPower = Range.clip((-driveFW - driveS - turn) *speedControl, -1.0, 1.0) ;
        motorLeftbackPower = Range.clip((driveFW + driveS - turn) *speedControl, -1.0, 1.0) ;
        motorRightbackPower = Range.clip((-driveFW + driveS - turn) *speedControl, -1.0, 1.0) ;

        // If time is up, then the motor powers will be 0.
        timeLeft = 60 + startTime - op.getRuntime();
        if (timeLeft <= 0) {
            motorLeftfrontPower = 0;
            motorRightfrontPower = 0;
            motorLeftbackPower = 0;
            motorRightbackPower = 0;
        }

        // The calculated power is then applied to the motors
        motorLeftfront.setPower(motorLeftfrontPower);
        motorRightfront.setPower(motorRightfrontPower);
        motorLeftback.setPower(motorLeftbackPower);
        motorRightback.setPower(motorRightbackPower);

        op.telemetry.addData("Driving Status", "Time Left: " + timeLeft);

        // If time is up, then the motors will stop.
        if (timeLeft <= 0) {
            motorLeftfront.setPower(0);
            motorRightfront.setPower(0);
            motorLeftback.setPower(0);
            motorRightback.setPower(0);
        }

    }
}
