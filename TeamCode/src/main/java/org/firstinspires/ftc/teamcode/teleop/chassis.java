package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

///////////////////////////////////////////////////////////////////////////////
// Purpose: Class for controlling the drivetrain for the 4 omni-wheel drivetrain (Holonomic)
// Author: Jonathan Ma, Ansh Gandhi
///////////////////////////////////////////////////////////////////////////////

@TeleOp(name = "Chassis")
@Disabled
public class chassis {

    // GO TO
    // teleop.JAVA TO
    // UPDATE VERSION NUMBER
    // BEFORE EVERY COMMIT

    // Initialize the variables
    private DcMotor motorLeftfront;
    private DcMotor motorRightfront;
    private DcMotor motorLeftback;
    private DcMotor motorRightback;

    private double speedControl = 0.5;

    private int display = 0;

    private int hold = 0;

    // Creates OpMode
    private OpMode op;
    chassis(OpMode opmode) {
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

    // Waiting for Play button to be pressed
    public void init_loop() {
    }

    // When Play button is pressed
    public void start() {
    }

    // Main Loop
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

        // This sets the speed mode to fast when the "A" button is pressed (Full Speed)
        if (op.gamepad1.a) {
            speedControl = 1;
            display = 0;
            hold = 0;
        }
        // This sets the speed mode to medium when the "X" button is pressed (Medium Speed)
        else if (op.gamepad1.x) {
            speedControl = 0.5;
            display = 1;
            hold = 1;
        }
        // This sets the speed mode to slow when the "B" button is pressed (Slow Speed)
        else if (op.gamepad1.b) {
            speedControl = 0.25;
            display = 2;
            hold = 2;
        }
        // This sets the speed mode to micro-adjustment speed when the left trigger is held down
        else if(op.gamepad1.left_trigger != 0) {
            speedControl = 0.15;
            display = 3;
        }

        // Allows micro-adjustment mode to work only when left trigger is held down
        else if(op.gamepad1.left_trigger == 0) {
            if (hold == 0) {
                speedControl = 1;
                display = 0;
            }
            if (hold == 1) {
                speedControl = 0.5;
                display = 1;
            }
            if (hold == 2) {
                speedControl = 0.25;
                display = 2;
            }
        }

        // Forward:  +LF -RF +LB -RB
        // Backward: -LF +RF -LB +RB
        // Left:     -LF -RF +LB +RB
        // Right:    +LF +RF -LB -RB
        // CW:       +LF +RF +LB +RB
        // CCW:      -LF -RF -LB -RB
        // All the values are added and multiplied by the speedControl, which can make the robot faster or slower
        // The calculated values are then clipped between -1.0 and 1.0
        // These values are then put into the motor speed variables
        motorLeftfrontPower = Range.clip((driveFW - driveS - turn) *speedControl, -1.0, 1.0) ;
        motorRightfrontPower = Range.clip((-driveFW - driveS - turn) *speedControl, -1.0, 1.0) ;
        motorLeftbackPower = Range.clip((driveFW + driveS - turn) *speedControl, -1.0, 1.0) ;
        motorRightbackPower = Range.clip((-driveFW + driveS - turn) *speedControl, -1.0, 1.0) ;

        // The calculated power is then applied to the motors
        motorLeftfront.setPower(motorLeftfrontPower);
        motorRightfront.setPower(motorRightfrontPower);
        motorLeftback.setPower(motorLeftbackPower);
        motorRightback.setPower(motorRightbackPower);

        // This prints speed mode on the screen
        if (display == 0) {
            op.telemetry.addData("SpeedMode", "Fast");
        }
        else if (display == 1) {
            op.telemetry.addData("SpeedMode", "Medium");
        }
        else if (display == 2) {
            op.telemetry.addData("SpeedMode", "Slow");
        }
        else if (display == 3) {
            op.telemetry.addData("SpeedMode", "Micro-Adjustment");
        }

    }
}
