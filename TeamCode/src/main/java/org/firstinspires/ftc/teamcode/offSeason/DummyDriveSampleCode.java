package org.firstinspires.ftc.teamcode.offSeason;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

/**
 * Created by ansh_ on 03/09/18.
 */

@TeleOp(name = "Code for Fun Outreach Event")
// @Disabled
public class DummyDriveSampleCode extends OpMode {

    // This is declaring the hardware.
    public DcMotor motorLeftback;
    public DcMotor motorRightback;

    // This code will be runned when the INIT button is pressed.
    @Override
    public void init() {
        // Tell the driver that the initialization is starting.
        telemetry.addData("Status", "Initializing");

        // This is initializing the hardware variables.
        // The strings must be the same used when configuring the hardware using the FTC app.
        motorLeftback = hardwareMap.get(DcMotor.class, "motorLeftback");
        motorRightback = hardwareMap.get(DcMotor.class, "motorRightback");

        // Tell the driver that initialization is complete.
        telemetry.addData("Status", "Initialized");
    }

    // This code is just waiting for the PLAY button to be pressed.
    @Override
    public void init_loop() {
    }

    // This code will do something once when the PLAY button is pressed.
    @Override
    public void start() {
    }

    // This code will run constantly after the previous part is runned.
    @Override
    public void loop() {
        // Some variables are being defined.
        double motorLeftbackPower;
        double motorRightbackPower;

        // The left joystick is used to drive fw/s while the right joystick is used to turn in place.
        double driveFW = gamepad1.left_stick_y;
        double turn  = gamepad1.right_stick_x;

        // The speed values are calculated and stored in variables.

        motorLeftbackPower = Range.clip((-driveFW - turn), -1.0, 1.0) ;
        motorRightbackPower = Range.clip((driveFW - turn), -1.0, 1.0) ;

        // The calculated power is then applied to the motors.
        motorLeftback.setPower(motorLeftbackPower);
        motorRightback.setPower(motorRightbackPower);

        telemetry.addData("Motors", "Leftback (%.2f), Rightback (%.2f)", motorLeftbackPower, motorRightbackPower);

    }

    // This code will run after the STOP button is pressed.
    @Override
    public void stop() {
    }
}