package org.firstinspires.ftc.teamcode.telopOrganizationConcept;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name = "chassis")
@Disabled
public class chassis {

    private DcMotor motorLeftfront;
    private DcMotor motorRightfront;

    final private double speedcontrol = 0.25;

    private ElapsedTime runtime = new ElapsedTime();

    private OpMode op;

    chassis(OpMode opmode) {
        op = opmode;
    }

    public void init() {
        // Tell the driver that the initialization is starting.
        op.telemetry.addData("chassis", "Initializing");

        // This is initializing the hardware variables.
        // The strings must be the same used when configuring the hardware using the FTC app.
        motorLeftfront = op.hardwareMap.get(DcMotor.class, "motorLeftfront");
        motorRightfront = op.hardwareMap.get(DcMotor.class, "motorRightfront");

        // This is just telling that all of the motors are forward.
        motorLeftfront.setDirection(DcMotor.Direction.FORWARD);
        motorRightfront.setDirection(DcMotor.Direction.FORWARD);

        // Tell the driver that initialization is complete.
        op.telemetry.addData("chassis", "Initialized");
    }

    public void start () {
        runtime.reset();
    }

    public void loop() {
        // Some variables are being defined.
        double motorLeftfrontPower;
        double motorRightfrontPower;

        // The left joystick is used to drive fw/s while the right joystick is used to turn in place.
        double driveFW = op.gamepad1.left_stick_y;
        double driveS = op.gamepad1.left_stick_x;
        double turn  = op.gamepad1.right_stick_x;

        // The speed values are calculated and stored in variables.
        motorLeftfrontPower = Range.clip((driveFW - driveS - turn)*speedcontrol, -1.0, 1.0) ;
        motorRightfrontPower = Range.clip((-driveFW - driveS - turn)*speedcontrol, -1.0, 1.0) ;

        // The calculated power is then applied to the motors.
        motorLeftfront.setPower(motorLeftfrontPower);
        motorRightfront.setPower(motorRightfrontPower);
        op.telemetry.addData("Status","Runtime:" + runtime.toString());
    }

}