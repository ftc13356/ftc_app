package org.firstinspires.ftc.teamcode.teleop.debug;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "Arm Move for Debug")
public class armMoveDebug extends OpMode{

    // VERSION NUMBER(MAJOR.MINOR) - DATE
    // DO BEFORE EVERY COMMIT!
    private final String armDebugVersionNumber = "3.3 - 1/19/18 ";

    // Initialize the variables
    private DcMotor armMotor;
    private Servo armClawLeft;
    private Servo armClawRight;
    private DigitalChannel touchSensor;

    private final double armSpeedControl = 0.5;

    private double armMotorPower = 0;

    private double armLeftPosition = 0;
    private double armRightPosition = 1;

    @Override
    public void init() {

        telemetry.addData("Teleop Program Version", armDebugVersionNumber);
        telemetry.addData("Arm", "Initializing");

        // Initialize hardware variables
        armMotor = hardwareMap.dcMotor.get("armMotor");
        armClawLeft = hardwareMap.servo.get("clawLeft");
        armClawRight = hardwareMap.servo.get("clawRight");
        touchSensor = hardwareMap.digitalChannel.get("touchSensor");
        touchSensor.setMode(DigitalChannel.Mode.INPUT);

        armMotor.setDirection(DcMotor.Direction.REVERSE);
        armMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        telemetry.addData("Arm", "Initialized");
    }

    @Override
    public void loop() {

        // Right bumper opens claw, Left bumper closes claw
        if (gamepad1.right_bumper) {
            armLeftPosition = 0;
            armRightPosition = 1;
            telemetry.addData("Arm Servo Status", "Open Completely");
        }

        if (gamepad1.left_bumper) {
            armLeftPosition = 0.7;
            armRightPosition = 0.3;
            telemetry.addData("Arm Servo Status", "Closed");
        }

        // Left stick is used to raise/lower arm
        armMotorPower = gamepad1.left_stick_y * armSpeedControl;

        // If arm hits sensor and power is negative, it stops, if positive, it continues
        if (!touchSensor.getState()) {
            telemetry.addData("Touch Sensor", "Is Pressed");

            if (armMotorPower > 0) {
                armMotorPower = 0;
                telemetry.addData("Arm", "Is Stopped");
            }
        }

        // Set arm power, servo position
        armMotor.setPower(armMotorPower);

        armClawLeft.setPosition(armLeftPosition);
        armClawRight.setPosition(armRightPosition);

        telemetry.addData("Left Arm Servo Position", armClawLeft.getPosition());
        telemetry.addData("Right Arm Servo Position", armClawRight.getPosition());
        telemetry.addData("Arm Position", armMotor.getCurrentPosition());

    }
}
