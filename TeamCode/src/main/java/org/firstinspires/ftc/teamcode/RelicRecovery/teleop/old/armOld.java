package org.firstinspires.ftc.teamcode.RelicRecovery.teleop.old;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name = "ArmOld")
@Disabled
public class armOld extends OpMode{

    private DcMotor armMotor;
    private Servo clawLeft;
    private Servo clawRight;

    private double leftPosition = 0;
    private double rightPosition = 1;

    static private double armSpeedControl = 0.5;
    static private double triggerSpeedControl = 0.25;
    static private double armSpeed = 0.5;

    private int targetValue = 0;

    private ElapsedTime runtime = new ElapsedTime();
    private double startTimeArm = runtime.seconds();

    private OpMode op;

    armOld(OpMode opmode){
        op = opmode;
    }

    public void init() {
        // Tell the driver that the initialization is starting.
        op.telemetry.addData("Arm", "Initializing");

        // This is initializing the hardware variables.
        // The strings must be the same used when configuring the hardware using the FTC app.
        armMotor = op.hardwareMap.get(DcMotor.class, "armMotor");
        clawLeft = op.hardwareMap.servo.get("clawLeft");
        clawRight = op.hardwareMap.servo.get("clawRight");

        // This is just telling the direction of the motors.
        armMotor.setDirection(DcMotor.Direction.REVERSE);

        // Tell the driver that initialization is complete.
        op.telemetry.addData("Arm", "Initialized");
    }

    // This code is just waiting for the PLAY button to be pressed.
    public void init_loop() {
    }

    // This code will do something once when the PLAY button is pressed.
    public void start() {
        runtime.reset();
        armMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }


    // This code will run constantly after the previous part is runned.
    public void loop() {
        // Some variables are being defined.
        double armMotorPower = 0;
        double timeLeftArm;

        boolean armIsStill = true;
        boolean print = true;

        // This closes the armOld when the left bumper is pressed.
        if (op.gamepad2.left_bumper) {
            leftPosition = 0.6;
            op.telemetry.addData("Servo Status", "Closed");
        }
        // This opens the armOld when the right bumper is pressed.
        if (op.gamepad2.right_bumper) {
            leftPosition = 0;
            op.telemetry.addData("Servo Status", "Open Completely");
        }

        if (op.gamepad2.dpad_up && leftPosition == 0.6) {
            leftPosition = 0.48;
            op.telemetry.addData("Servo Status", "Open Slightly");
        }

        //4 counts per degree
        //All values should be negative
        if (op.gamepad2.dpad_down) {
            targetValue = -100;
            armIsStill = false;
            armMotor.setTargetPosition(targetValue);
            armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            op.telemetry.addData("Goal Position", "%7d", targetValue);
        }
        else if (op.gamepad2.a) {
            targetValue = -1900;
            armIsStill = false;
            armMotor.setTargetPosition(targetValue);
            armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            op.telemetry.addData("Goal Position", "%7d", targetValue);
        }
        else if (op.gamepad2.x) {
            targetValue = -3000;
            armIsStill = false;
            armMotor.setTargetPosition(targetValue);
            armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            op.telemetry.addData("Goal Position", "%7d", targetValue);
        }
        else if (op.gamepad2.y) {
            targetValue = -4400;
            armIsStill = false;
            armMotor.setTargetPosition(targetValue);
            armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            op.telemetry.addData("Goal Position", "%7d", targetValue);
        }
        else if (op.gamepad2.b) {
            targetValue = -5700;
            armIsStill = false;
            armMotor.setTargetPosition(targetValue);
            armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            op.telemetry.addData("Goal Position", "%7d", targetValue);
        }

        if (armMotor.isBusy() == false) {
            armMotorPower = op.gamepad2.left_trigger * triggerSpeedControl;
            if (armMotorPower == 0){
                armMotorPower = -op.gamepad2.right_trigger * triggerSpeedControl;
                if (armMotorPower == 0){
                    armMotorPower = op.gamepad2.left_stick_y * armSpeedControl;
                }
            }
        }

        // If time is up, then the motor powers will be 0.
        timeLeftArm = 120 + startTimeArm - op.getRuntime();
        if (timeLeftArm <= 0) {
            armMotorPower = 0;
            clawLeft.setPosition(0);
            clawRight.setPosition(1);
        }

        // The calculated power is then applied to the motors.
        if (armMotorPower == 0) {
            armMotor.setPower(armSpeed);
            print = true;
        }
        else {
            armMotor.setPower(armMotorPower);
            print = false;
        }
        clawLeft.setPosition(leftPosition);
        rightPosition = 1 - leftPosition;
        clawRight.setPosition(rightPosition);
        leftPosition = clawLeft.getPosition();
        rightPosition = clawRight.getPosition();

        op.telemetry.addData("Status", "Time Left: " + timeLeftArm);
        if (print) {
            op.telemetry.addData("Motor Speed", armSpeed);
        }
        else {
            op.telemetry.addData("Motor Speed", armMotorPower);
        }
        op.telemetry.addData("Current Position", "%7d", armMotor.getCurrentPosition());
        op.telemetry.addData("Left Servo Position", leftPosition);
        op.telemetry.addData("Right Servo Position", rightPosition);

        // If time is up, then the motors will stop.
        if (timeLeftArm <= 0) {
            armMotor.setPower(0);
            clawLeft.setPosition(0);
            clawRight.setPosition(1);
        }
    }
}
