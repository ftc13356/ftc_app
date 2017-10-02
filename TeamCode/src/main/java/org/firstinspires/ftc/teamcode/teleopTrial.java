package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name = "Teleop Trial")
//@Disabled
public class teleopTrial extends OpMode
{
    // This is declaring the hardware.
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor motorLeftfront;
    private DcMotor motorRightfront;
    private DcMotor motorLeftback;
    private DcMotor motorRightback;

    // This code will be runned when the INIT button is pressed.
    @Override
    public void init() {
        // Tell the driver that the initialization is starting.
        telemetry.addData("Status", "Initialized");

        // This is initializing the hardware variables.
        // The strings must be the same used when configuring the hardware using the FTC app.
        motorLeftfront = hardwareMap.get(DcMotor.class, "motorLeftfront");
        motorRightfront = hardwareMap.get(DcMotor.class, "motorRightfront");
        motorLeftback = hardwareMap.get(DcMotor.class, "motorLeftback");
        motorRightback = hardwareMap.get(DcMotor.class, "motorRightback");

        // This is just telling that all of the motors are forward.
        motorLeftfront.setDirection(DcMotor.Direction.FORWARD);
        motorRightfront.setDirection(DcMotor.Direction.FORWARD);
        motorLeftback.setDirection(DcMotor.Direction.FORWARD);
        motorRightback.setDirection(DcMotor.Direction.FORWARD);

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
        runtime.reset();
    }

    // This code will run constantly after the previous part is runned.
    @Override
    public void loop() {

        // The variables are so that the motor speed can be displayed on the phone.
        double motorLeftfrontPower;
        double motorRightfrontPower;
        double motorLeftbackPower;
        double motorRightbackPower;

        // The left joystick is used to drive fw/s while the right joystick is used to turn in place.
        double driveFW = -gamepad1.left_stick_y;
        double driveS = gamepad1.left_stick_x;
        double turn  = gamepad1.right_stick_x;
        // The speed values are calculated and stored in variables.
        motorLeftfrontPower = Range.clip(-driveFW + driveS + turn, -1.0, 1.0) ;
        motorRightfrontPower = Range.clip(driveFW + driveS + turn, -1.0, 1.0) ;
        motorLeftbackPower = Range.clip(-driveFW - driveS + turn, -1.0, 1.0) ;
        motorRightbackPower = Range.clip(driveFW - driveS + turn, -1.0, 1.0) ;

        // The calculated power is then applied to the motors.
        motorLeftfront.setPower(motorLeftfrontPower);
        motorRightfront.setPower(motorRightfrontPower);
        motorLeftback.setPower(motorLeftbackPower);
        motorRightback.setPower(motorRightbackPower);

        // This shows that time left and the motor speeds
        telemetry.addData("Status", "Run Time: " + runtime.toString());
        telemetry.addData("Motors", "Leftfront (%.2f), Rightfront (%.2f), Leftback (%.2f), Rightback (%.2f)", motorLeftfrontPower, motorRightfrontPower, motorLeftbackPower, motorRightbackPower);

        //motorLeftfront = hardwareMap.dcMotor.get("motorLeftfront");
        //motorRightfront = hardwareMap.dcMotor.get("motorRightfront");
        //motorLeftback = hardwareMap.dcMotor.get("motorLeftback");
        //motorRightback = hardwareMap.dcMotor.get("motorRightback");

    }
    // This code will run after the STOP button is pressed.
    @Override
    public void stop() {
    }
}