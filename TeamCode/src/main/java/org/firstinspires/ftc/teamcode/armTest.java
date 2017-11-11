package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name = "test")
public class armTest extends OpMode{

    private DcMotor armMotor;
    //private double armSpeedControl = 0.5;
    //private double armSpeed = 0.5;

    //private int targetValue = 0;

    // ElapsedTime runtime = new ElapsedTime();

    @Override
    public void init() {
        // Tell the driver that the initialization is starting.
        telemetry.addData("Arm", "Initializing");

        // This is initializing the hardware variables.
        // The strings must be the same used when configuring the hardware using the FTC app.
        armMotor = hardwareMap.get(DcMotor.class, "armMotor");

        // This is just telling the direction of the motors.
        armMotor.setDirection(DcMotor.Direction.REVERSE);

        // Tell the driver that initialization is complete.
        telemetry.addData("Arm", "Initialized");
    }

    // This code is just waiting for the PLAY button to be pressed.
    @Override
    public void init_loop() {
    }

    // This code will do something once when the PLAY button is pressed.
    @Override
    public void start() {
        //runtime.reset();
        //armMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }


    // This code will run constantly after the previous part is runned.
    @Override
    public void loop() {
        // Some variables are being defined.
        double armMotorPower;

        // The speed values are calculated and stored in variables.
        armMotorPower =  gamepad1.left_stick_y; //* armSpeedControl;

        //4 counts per degree
        //All values should be negative
        /*if ( gamepad1.dpad_down) {
            targetValue = -100;
            armMotor.setTargetPosition(targetValue);
            armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
             telemetry.addData("Goal Position", "%7d",targetValue);
        }

        if ( gamepad1.a) {
            targetValue = -1900;
            armMotor.setTargetPosition(targetValue);
            armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
             telemetry.addData("Goal Position", "%7d",targetValue);
        }
        if ( gamepad1.x) {
            targetValue = -3000;
            armMotor.setTargetPosition(targetValue);
            armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
             telemetry.addData("Goal Position", "%7d",targetValue);
        }
        if ( gamepad1.y) {
            targetValue = -4400;
            armMotor.setTargetPosition(targetValue);
            armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
             telemetry.addData("Goal Position", "%7d",targetValue);
        }
        if ( gamepad1.b) {
            targetValue = -5700;
            armMotor.setTargetPosition(targetValue);
            armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
             telemetry.addData("Goal Position", "%7d",targetValue);
        }
        if (targetValue!= 0) {
            armMotor.setPower(armSpeed);
        }*/


        // The calculated power is then applied to the motors.
        armMotor.setPower(armMotorPower);

         telemetry.addData("Motor", armMotorPower);
         //telemetry.addData("Current Position", "%7d", armMotor.getCurrentPosition());

    }
}
