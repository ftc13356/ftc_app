package org.firstinspires.ftc.teamcode.Teleop2;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "Arm")
@Disabled
public class Arm extends OpMode implements iArm {

    private DcMotor armMotor;
    private Servo clawLeft;
    private Servo clawRight;

    double leftposition = 0;
    double rightposition = 1;
    final private double armSpeedControl = 0.5;

    @Override
    public void init(){
        // Tell the driver that the initialization is starting.
        telemetry.addData("Arm", "Initializing");

        // This is initializing the hardware variables.
        // The strings must be the same used when configuring the hardware using the FTC app.
        armMotor = hardwareMap.get(DcMotor.class, "armMotor");
        clawLeft = hardwareMap.servo.get("clawLeft");
        clawRight = hardwareMap.servo.get("clawRight");

        // This is just telling the direction of the motors.
        armMotor.setDirection(DcMotor.Direction.REVERSE);

        // Tell the driver that initialization is complete.
        telemetry.addData("Arm", "Initialized");
    }

    @Override
    public  void loop(){
        // Some variables are being defined.
        double armMotorPower;

        // This closes the arm when the "A" button is pressed.
        if(gamepad2.a)
        {
            leftposition = 0.6;
            telemetry.addData("Servo Status", "Closed");
        }
        // This opens the arm when the "B" button is pressed.
        else if(gamepad2.b)
        {
            leftposition = 0;
            telemetry.addData("Servo Status", "Open");
        }

        // The speed values are calculated and stored in variables.
        armMotorPower = gamepad2.left_stick_y * armSpeedControl;

        // The calculated power is then applied to the motors.
        armMotor.setPower(armMotorPower);
        clawLeft.setPosition(leftposition);
        rightposition = 1-leftposition;
        clawRight.setPosition(rightposition);
        leftposition = clawLeft.getPosition();
        rightposition = clawRight.getPosition();

    }

}
