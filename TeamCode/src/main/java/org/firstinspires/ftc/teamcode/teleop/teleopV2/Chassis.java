package org.firstinspires.ftc.teamcode.teleop.teleopV2;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name = "Chassis")
@Disabled
public class Chassis {

    private DcMotor motorLeftfront;
    private DcMotor motorRightfront;
    private DcMotor motorLeftback;
    private DcMotor motorRightback;

    private ElapsedTime runtime = new ElapsedTime();

    private OpMode op;

    Chassis(OpMode opmode){
        op = opmode;
    }

    public void init() {
        // Tell the driver that the initialization is starting.
        op.telemetry.addData("Status", "Initializing");

        // This is initializing the hardware variables.
        // The strings must be the same used when configuring the hardware using the FTC app.
        motorLeftfront = op.hardwareMap.get(DcMotor.class, "motorLeftfront");
        motorRightfront = op.hardwareMap.get(DcMotor.class, "motorRightfront");
        motorLeftback = op.hardwareMap.get(DcMotor.class, "motorLeftback");
        motorRightback = op.hardwareMap.get(DcMotor.class, "motorRightback");

        // This is just telling the direction of the motors.
        motorLeftfront.setDirection(DcMotor.Direction.FORWARD);
        motorRightfront.setDirection(DcMotor.Direction.FORWARD);
        motorLeftback.setDirection(DcMotor.Direction.FORWARD);
        motorRightback.setDirection(DcMotor.Direction.FORWARD);

        // Tell the driver that initialization is complete.
        op.telemetry.addData("Status", "Initialized");
    }

    // This code is just waiting for the PLAY button to be pressed.

    public void init_loop() {
    }

    // This code will do something once when the PLAY button is pressed.
    public void start()
    {
        runtime.reset();
    }

    // This code is just defining the variables needed for the loop.
    double speedcontrol = 0.25;
    boolean display = false;

    // This code will run constantly after the previous part is runned.
    public void loop() {
        // Some variables are being defined.
        double motorLeftfrontPower;
        double motorRightfrontPower;
        double motorLeftbackPower;
        double motorRightbackPower;
        double timeleft;

        // The left joystick is used to drive fw/s while the right joystick is used to turn in place.
        double driveFW = op.gamepad1.left_stick_y;
        double driveS = op.gamepad1.left_stick_x;
        double turn  = op.gamepad1.right_stick_x;

        // This sets the speed mode to fast when the "A" button is pressed.
        if (op.gamepad1.a)
        {
            speedcontrol = 1;
            display = true;
        }
        // This sets the speed mode to slow (default) when the "B" button is pressed.
        else if (op.gamepad1.b)
        {
            speedcontrol = 0.25;
            display = false;
        }

        // The speed values are calculated and stored in variables.
        motorLeftfrontPower = Range.clip((driveFW - driveS - turn)*speedcontrol, -1.0, 1.0) ;
        motorRightfrontPower = Range.clip((-driveFW - driveS - turn)*speedcontrol, -1.0, 1.0) ;
        motorLeftbackPower = Range.clip((driveFW + driveS - turn)*speedcontrol, -1.0, 1.0) ;
        motorRightbackPower = Range.clip((-driveFW + driveS - turn)*speedcontrol, -1.0, 1.0) ;

        // If time is up, then the motor powers will be 0.
        timeleft = 120 - op.getRuntime();
        if (timeleft <= 0)
        {
            motorLeftfrontPower = 0;
            motorRightfrontPower = 0;
            motorLeftbackPower = 0;
            motorRightbackPower = 0;
        }

        // The calculated power is then applied to the motors.
        motorLeftfront.setPower(motorLeftfrontPower);
        motorRightfront.setPower(motorRightfrontPower);
        motorLeftback.setPower(motorLeftbackPower);
        motorRightback.setPower(motorRightbackPower);

        // This prints information on the screen.
        op.telemetry.addData("Mode", "Teleop");
        if (display) {
            op.telemetry.addData("SpeedMode", "Fast");
        }
        else {
            op.telemetry.addData("SpeedMode", "Slow");
        }

        op.telemetry.addData("Status", "Time Left: " + timeleft);
        op.telemetry.addData("Motors", "Leftfront (%.2f), Rightfront (%.2f), Leftback (%.2f), Rightback (%.2f)", motorLeftfrontPower, motorRightfrontPower, motorLeftbackPower, motorRightbackPower);

        // If time is up, then the motors will stop.
        if (timeleft <= 0)
        {
            motorLeftfront.setPower(0);
            motorRightfront.setPower(0);
            motorLeftback.setPower(0);
            motorRightback.setPower(0);
        }
    }
}
