package org.firstinspires.ftc.teamcode.RoverRuckus.teleop;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

///////////////////////////////////////////////////////////////////////////////
// Purpose: Controls Drivetrain of Prototype Robot (All Wheel Drive)
// Author: Ansh Gandhi, Jonathan Ma
///////////////////////////////////////////////////////////////////////////////

@TeleOp(name = "All Wheel Drive Test")
public class AWDTest extends OpMode {

    // Initialize the variables
    private DcMotor LeftFront;
    private DcMotor RightFront;
    private DcMotor LeftBack;
    private DcMotor RightBack;

    private double speedControl = 0.5;

    private int display = 0;

    private int hold = 0;

    public void init() {

        telemetry.addData("Robot", "Initializing");

        // Initializing the hardware variables
        LeftFront = hardwareMap.dcMotor.get("LeftFront");
        RightFront = hardwareMap.dcMotor.get("RightFront");
        LeftBack = hardwareMap.dcMotor.get("LeftBack");
        RightBack = hardwareMap.dcMotor.get("RightBack");

        LeftFront.setDirection(DcMotor.Direction.FORWARD);
        RightFront.setDirection(DcMotor.Direction.FORWARD);
        LeftBack.setDirection(DcMotor.Direction.FORWARD);
        RightBack.setDirection(DcMotor.Direction.FORWARD);

        telemetry.addData("Robot", "Initialized");
    }

    public void init_loop() {
    }

    public void start() {
    }

    public void loop() {
        double LeftFrontPower;
        double RightFrontPower;
        double LeftBackPower;
        double RightBackPower;

        double driveFW = gamepad1.left_stick_y;
        double turn  = gamepad1.right_stick_x;

        if (gamepad1.a) {
            speedControl = 1;
            display = 0;
            hold = 0;
        }
        else if (gamepad1.x) {
            speedControl = 0.5;
            display = 1;
            hold = 1;
        }
        else if (gamepad1.b) {
            speedControl = 0.25;
            display = 2;
            hold = 2;
        }
        else if(gamepad1.left_trigger != 0) {
            speedControl = 0.15;
            display = 3;
        }

        else if(gamepad1.left_trigger == 0) {
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

        LeftFrontPower = Range.clip((driveFW - turn) *speedControl, -1.0, 1.0) ;
        RightFrontPower = Range.clip((-driveFW - turn) *speedControl, -1.0, 1.0) ;
        LeftBackPower = Range.clip((driveFW - turn) *speedControl, -1.0, 1.0) ;
        RightBackPower = Range.clip((-driveFW - turn) *speedControl, -1.0, 1.0) ;

        LeftFront.setPower(LeftFrontPower);
        RightFront.setPower(RightFrontPower);
        LeftBack.setPower(LeftBackPower);
        RightBack.setPower(RightBackPower);

        if (display == 0) {
            telemetry.addData("SpeedMode", "Fast");
        }
        else if (display == 1) {
            telemetry.addData("SpeedMode", "Medium");
        }
        else if (display == 2) {
            telemetry.addData("SpeedMode", "Slow");
        }
        else if (display == 3) {
            telemetry.addData("SpeedMode", "Micro-Adjustment");
        }
    }
}
