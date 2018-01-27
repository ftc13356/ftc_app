package org.firstinspires.ftc.teamcode.teleop.debug;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "Arm Move for Debug")
public class armMoveDebug extends OpMode{

    // VERSION NUMBER(MAJOR.MINOR) - DATE
    // DO BEFORE EVERY COMMIT!
    private final String armDebugVersionNumber = "4.0 - 1/26/18 ";

    // Initialize the variables
    private DcMotor armMotor;
    private Servo armClawLeft;
    private Servo armClawRight;
    private DigitalChannel touchSensor;
    private CRServo colorArm;

    private final double armSpeedControl = 0.5;

    private double armMotorPower = 0;
    private double colorArmPower = 0;

    private int currentArmPosition = 0;

    private double clawLeftPosition = 0.35;
    private double clawRightPosition = 0.65;

    @Override
    public void init() {

        telemetry.addData("Teleop Program Version", armDebugVersionNumber);
        telemetry.addData("Arm", "Initializing");

        // Initialize hardware variables
        armMotor = hardwareMap.dcMotor.get("armMotor");
        armClawLeft = hardwareMap.servo.get("clawLeft");
        armClawRight = hardwareMap.servo.get("clawRight");
        touchSensor = hardwareMap.digitalChannel.get("touchSensor");
        colorArm = hardwareMap.crservo.get("colorArm");
        touchSensor.setMode(DigitalChannel.Mode.INPUT);

        armMotor.setDirection(DcMotor.Direction.REVERSE);
        armMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        telemetry.addData("Arm", "Initialized");
    }

    @Override
    public void loop() {

        // Right bumper opens claw, Left bumper closes claw, B opens claw slightly
        if (gamepad1.left_bumper) {
            clawLeftPosition = 0.7;
            clawRightPosition = 0.3;
            telemetry.addData("Arm Servo Status", "Closed");
        }

        if (gamepad1.right_bumper) {
            clawLeftPosition = 0.35;
            clawRightPosition = 0.65;
            telemetry.addData("Arm Servo Status", "Open Completely");
        }

        if (gamepad1.a) {
            clawLeftPosition = 0.5;
            clawRightPosition = 0.5;
            telemetry.addData("Arm Servo Status", "Open Slightly");
        }

        // Reset encoder
        if (gamepad1.b) {
            armMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            armMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
        // Left stick is used to raise/lower arm
        armMotorPower = gamepad1.left_stick_y * armSpeedControl;
        colorArmPower = gamepad1.right_stick_x;

        colorArm.setPower(colorArmPower);

        currentArmPosition = armMotor.getCurrentPosition();

        // Stop arm's motion if it hits touch sensor and moving downward
        if (!touchSensor.getState()) {
            telemetry.addData("Touch Sensor", "Is Pressed");

            if (armMotorPower > 0) {
                armMotorPower = 0;
                telemetry.addData("Arm", "Is Stopped");
            }
        }

        // Stop arm's motion if it goes above upper limit and moving upward
        if (currentArmPosition < -5900 && armMotorPower < 0) {
            armMotorPower = 0;
            telemetry.addData("Arm Status", "Upper Limit Reached");
        }

        // Set arm power, servo position
        armMotor.setPower(armMotorPower);

        armClawLeft.setPosition(clawLeftPosition);
        armClawRight.setPosition(clawRightPosition);

        telemetry.addData("Left Arm Servo Position", armClawLeft.getPosition());
        telemetry.addData("Right Arm Servo Position", armClawRight.getPosition());
        telemetry.addData("Arm Position", armMotor.getCurrentPosition());

    }
}
