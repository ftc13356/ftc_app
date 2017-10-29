package org.firstinspires.ftc.teamcode.Teleop2;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name = "Chasis")
@Disabled
public class Chassis extends OpMode implements iChassis {

    private DcMotor motorLeftfront;
    private DcMotor motorRightfront;

    final private double speedcontrol = 0.25;

    private ElapsedTime runtime = new ElapsedTime();

    @Override
    public void init() {
        // Tell the driver that the initialization is starting.
        telemetry.addData("Chassis", "Initializing");

        // This is initializing the hardware variables.
        // The strings must be the same used when configuring the hardware using the FTC app.
        motorLeftfront = hardwareMap.get(DcMotor.class, "motorLeftfront");
        motorRightfront = hardwareMap.get(DcMotor.class, "motorRightfront");

        // This is just telling that all of the motors are forward.
        motorLeftfront.setDirection(DcMotor.Direction.FORWARD);
        motorRightfront.setDirection(DcMotor.Direction.FORWARD);

        // Tell the driver that initialization is complete.
        telemetry.addData("Chassis", "Initialized");
    }
    public void start () {
        runtime.reset();
    }
    @Override
    public void loop() {
        // Some variables are being defined.
        double motorLeftfrontPower;
        double motorRightfrontPower;

        // The left joystick is used to drive fw/s while the right joystick is used to turn in place.
        double driveFW = gamepad1.left_stick_y;
        double driveS = gamepad1.left_stick_x;
        double turn  = gamepad1.right_stick_x;

        // The speed values are calculated and stored in variables.
        motorLeftfrontPower = Range.clip((driveFW - driveS - turn)*speedcontrol, -1.0, 1.0) ;
        motorRightfrontPower = Range.clip((-driveFW - driveS - turn)*speedcontrol, -1.0, 1.0) ;

        // The calculated power is then applied to the motors.
        motorLeftfront.setPower(motorLeftfrontPower);
        motorRightfront.setPower(motorRightfrontPower);
        telemetry.addData("Status","Runtime:" + runtime.toString());
    }

}
