package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name = "Chassis")
@Disabled
public class chassis {

    private DcMotor motorLeftfront;
    private DcMotor motorRightfront;
    private DcMotor motorLeftback;
    private DcMotor motorRightback;

    private double speedControl = 0.5;

    private int display = 0;

    private OpMode op;

    chassis(OpMode opmode){
        op = opmode;
    }

    public void init() {
        // Tell the driver that the initialization is starting.
        op.telemetry.addData("Chassis", "Initializing");

        // This is initializing the hardware variables.
        // The strings must be the same used when configuring the hardware using the FTC app.
        motorLeftfront = op.hardwareMap.get(DcMotor.class, "motorLeftfront");
        motorRightfront = op.hardwareMap.get(DcMotor.class, "motorRightfront");
        motorLeftback = op.hardwareMap.get(DcMotor.class, "motorLeftback");
        motorRightback = op.hardwareMap.get(DcMotor.class, "motorRightback");

        // This is just telling the direction of the motor.
        motorLeftfront.setDirection(DcMotor.Direction.FORWARD);
        motorRightfront.setDirection(DcMotor.Direction.FORWARD);
        motorLeftback.setDirection(DcMotor.Direction.FORWARD);
        motorRightback.setDirection(DcMotor.Direction.FORWARD);

        // Tell the driver that initialization is complete.
        op.telemetry.addData("Chassis", "Initialized");
    }

    // This code is just waiting for the PLAY button to be pressed.

    public void init_loop() {
    }

    // This code will do something once when the PLAY button is pressed.
    public void start() {
    }

    // This code will run constantly after the previous part is runned.
    public void loop() {
        // Some variables are being defined.
        double motorLeftfrontPower;
        double motorRightfrontPower;
        double motorLeftbackPower;
        double motorRightbackPower;

        // The left joystick is used to drive fw/s while the right joystick is used to turn in place.
        double driveFW = op.gamepad1.left_stick_y;
        double driveS = op.gamepad1.left_stick_x;
        double turn  = op.gamepad1.right_stick_x;

        // This sets the speed mode to fast when the "A" button is pressed. (Full Speed)
        if (op.gamepad1.a) {
            speedControl = 1;
            display = 0;
        }
        // This sets the speed mode to slow (default) when the "B" button is pressed. (Slow Speed)
        else if (op.gamepad1.b) {
            speedControl = 0.25;
            display = 1;
        }
        // This sets the speed mode to "Tetrix" when the "X" button is pressed. (Tetrix speed represents 0.25 speed of the Tetrix motors.)
        else if (op.gamepad1.x) {
            speedControl = 0.5;
            display = 2;
        }

        // The speed values are calculated and stored in variables.
        motorLeftfrontPower = Range.clip((driveFW - driveS - turn)*speedControl, -1.0, 1.0) ;
        motorRightfrontPower = Range.clip((-driveFW - driveS - turn)*speedControl, -1.0, 1.0) ;
        motorLeftbackPower = Range.clip((driveFW + driveS - turn)*speedControl, -1.0, 1.0) ;
        motorRightbackPower = Range.clip((-driveFW + driveS - turn)*speedControl, -1.0, 1.0) ;

        // The calculated power is then applied to the motors.
        motorLeftfront.setPower(motorLeftfrontPower);
        motorRightfront.setPower(motorRightfrontPower);
        motorLeftback.setPower(motorLeftbackPower);
        motorRightback.setPower(motorRightbackPower);

        // This prints information on the screen.
        op.telemetry.addData("Mode", "Teleop");
        if (display == 0) {
            op.telemetry.addData("SpeedMode", "Fast");
        }
        else if (display == 1) {
            op.telemetry.addData("SpeedMode", "Slow");
        }
        else if (display == 2) {
            op.telemetry.addData("SpeedMode", "Tetrix");
        }

        op.telemetry.addData("Motors", "Leftfront (%.2f), Rightfront (%.2f), Leftback (%.2f), Rightback (%.2f)", motorLeftfrontPower, motorRightfrontPower, motorLeftbackPower, motorRightbackPower);

    }
}
