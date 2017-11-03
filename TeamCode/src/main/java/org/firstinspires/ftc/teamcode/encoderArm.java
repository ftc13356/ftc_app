/* Copyright (c) 2017 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import static android.os.SystemClock.sleep;

@TeleOp(name = "Arm with Encoder")
// @Disabled
public class encoderArm extends OpMode {

    // This is declaring the hardware.
    public ElapsedTime runtime = new ElapsedTime();
    public DcMotor armMotor;
    public Servo clawLeft;
    public Servo clawRight;

    static final double     COUNTS_PER_MOTOR_REV    = 1440 ;    // eg: TETRIX Motor Encoder

    //Need to change for arm gear tower:
    static final double     DRIVE_GEAR_REDUCTION    = 2.0 ;     // This is < 1.0 if geared UP
    //

    static final double     WHEEL_DIAMETER_INCHES   = 4.0 ;     // For figuring circumference
    static final double     COUNTS_PER_INCH         = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
                                                      (WHEEL_DIAMETER_INCHES * 3.1415);
    static final double     LIFT_SPEED             = 0.5;

    // This code will be runned when the INIT button is pressed.
    @Override
    public void init() {
        // Tell the driver that the initialization is starting.
        telemetry.addData("Status", "Initializing");

        // This is initializing the hardware variables.
        // The strings must be the same used when configuring the hardware using the FTC app.
        armMotor = hardwareMap.get(DcMotor.class, "armMotor");
        clawLeft = hardwareMap.servo.get("clawLeft");
        clawRight = hardwareMap.servo.get("clawRight");

        // This is just telling the direction of the motors.
        armMotor.setDirection(DcMotor.Direction.REVERSE);

        // Tell the driver that initialization is complete.
        telemetry.addData("Status", "Initialized");
    }

    // This code is just waiting for the PLAY button to be pressed.
    @Override
    public void init_loop() {
    }
    
    // This code will do something once when the PLAY button is pressed.
    @Override
    public void start() {
        runtime.reset();
    }

    // This code is just defining the variables needed for the loop.
    private double leftposition = 0;
    private double rightposition = 1;
    private double armSpeedControl = 0.5;

    // This code will run constantly after the previous part is runned.
    @Override
    public void loop() {
        // Some variables are being defined.
        double armMotorPower;

        if(gamepad1.left_bumper) {
            leftposition = 0.6;
            telemetry.addData("Servo Status", "Closed");
        }
        if(gamepad1.right_bumper) {
            leftposition = 0;
            telemetry.addData("Servo Status", "Open");
        }


        if (gamepad1.a) {
            encoderInit();
            encoderDrive(LIFT_SPEED, 0, 10.0);
            telemetry.addData("arm Position", "Home");
            armMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        }
        if (gamepad1.b) {
            encoderInit();
            encoderDrive(LIFT_SPEED, 5, 10.0);
            telemetry.addData("arm Position", "Height 1");
            armMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        }
        if (gamepad1.x) {
            encoderInit();
            encoderDrive(LIFT_SPEED, 10, 10.0);
            telemetry.addData("arm Position", "Height 2");
            armMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        }
        if (gamepad1.y) {
            encoderInit();
            encoderDrive(LIFT_SPEED, 10, 10.0);
            telemetry.addData("arm Position", "Height 3");
            armMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
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

        telemetry.addData("Motor", armMotorPower);
        telemetry.addData("Left Servo Position", leftposition);
        telemetry.addData("Right Servo Position", rightposition);
    }

    public void encoderInit(){
        telemetry.addData("Status", "Resetting Encoders");

        armMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        armMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void encoderDrive(double speed,
                             double verticalInches,
                             double timeoutSec) {
        int newTarget;

        // Determine new target position, and pass to motor controller
        newTarget = armMotor.getCurrentPosition() + (int)(verticalInches * COUNTS_PER_INCH);
        armMotor.setTargetPosition(newTarget);

        // Turn On RUN_TO_POSITION
        armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        // reset the timeout time and start motion.
        runtime.reset();
        armMotor.setPower(Math.abs(speed));

        // keep looping if there is time left, and both motors are running.
        // Note: We use isBusy() && isBusy()) in the loop test, which means that when EITHER motor hits
        // its target position, the motion will stop.  This is "safer" in the event that the robot will
        // always end the motion as soon as possible.
        // However, if you require that BOTH motors have finished their moves before the robot continues
        // onto the next step, use (isBusy() || isBusy()) in the loop test.
        while (runtime.seconds() < timeoutSec && armMotor.isBusy()) {

            // Display it for the driver.
            telemetry.addData("Path 1",  "Running to %7d", newTarget);
            telemetry.addData("Path 2",  "Running at %7d",
                    armMotor.getCurrentPosition());
        }

        // Stop all motion;
        armMotor.setPower(0);

        // Turn off RUN_TO_POSITION
        armMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        sleep(500);

    }

    // This code will run after the STOP button is pressed.
    @Override
    public void stop() {
    }
}