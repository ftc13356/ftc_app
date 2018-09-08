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

package org.firstinspires.ftc.teamcode.RelicRecovery.teleop.old;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name = "Teleop Drivetrain and Arm Original")
@Disabled
public class teleopDrivetrainArmOriginal extends OpMode {

    // This is declaring the hardware.
    public ElapsedTime runtime = new ElapsedTime();
    public DcMotor motorLeftfront;
    public DcMotor motorRightfront;
    public DcMotor motorLeftback;
    public DcMotor motorRightback;
    public DcMotor armMotor;
    public Servo clawLeft;
    public Servo clawRight;

    // This code will be runned when the INIT button is pressed.
    @Override
    public void init() {
        // Tell the driver that the initialization is starting.
        telemetry.addData("Status", "Initializing");

        // This is initializing the hardware variables.
        // The strings must be the same used when configuring the hardware using the FTC app.
        motorLeftfront = hardwareMap.get(DcMotor.class, "motorLeftfront");
        motorRightfront = hardwareMap.get(DcMotor.class, "motorRightfront");
        motorLeftback = hardwareMap.get(DcMotor.class, "motorLeftback");
        motorRightback = hardwareMap.get(DcMotor.class, "motorRightback");
        armMotor = hardwareMap.get(DcMotor.class, "armMotor");
        clawLeft = hardwareMap.servo.get("clawLeft");
        clawRight = hardwareMap.servo.get("clawRight");

        // This is just telling the direction of the motors.
        motorLeftfront.setDirection(DcMotor.Direction.FORWARD);
        motorRightfront.setDirection(DcMotor.Direction.FORWARD);
        motorLeftback.setDirection(DcMotor.Direction.FORWARD);
        motorRightback.setDirection(DcMotor.Direction.FORWARD);
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
    public void start()
    {
        runtime.reset();
    }

    // This code is just defining the variables needed for the loop.
    double speedcontrol = 0.25;
    boolean display = false;
    double leftposition = 0;
    double rightposition = 1;
    double armSpeedControl = 0.5;
    double startTime = runtime.seconds();

    // This code will run constantly after the previous part is runned.
    @Override
    public void loop() {
        // Some variables are being defined.
        double motorLeftfrontPower;
        double motorRightfrontPower;
        double motorLeftbackPower;
        double motorRightbackPower;
        double armMotorPower;
        double timeleft;

        // The left joystick is used to drive fw/s while the right joystick is used to turn in place.
        double driveFW = gamepad1.left_stick_y;
        double driveS = gamepad1.left_stick_x;
        double turn  = gamepad1.right_stick_x;

        // This sets the speed mode to fast when the "A" button is pressed.
        if (gamepad1.a)
        {
            speedcontrol = 1;
            display = true;
        }
        // This sets the speed mode to slow (default) when the "B" button is pressed.
        else if (gamepad1.b)
        {
            speedcontrol = 0.25;
            display = false;
        }
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

        motorLeftfrontPower = Range.clip((driveFW - driveS - turn)*speedcontrol, -1.0, 1.0) ;
        motorRightfrontPower = Range.clip((-driveFW - driveS - turn)*speedcontrol, -1.0, 1.0) ;
        motorLeftbackPower = Range.clip((driveFW + driveS - turn)*speedcontrol, -1.0, 1.0) ;
        motorRightbackPower = Range.clip((-driveFW + driveS - turn)*speedcontrol, -1.0, 1.0) ;
        armMotorPower = gamepad2.left_stick_y * armSpeedControl;

        // If time is up, then the motor powers will be 0.
        timeleft = 120 + startTime - getRuntime();
        if (timeleft <= 0)
        {
            motorLeftfrontPower = 0;
            motorRightfrontPower = 0;
            motorLeftbackPower = 0;
            motorRightbackPower = 0;
            armMotorPower = 0;
        }

        // The calculated power is then applied to the motors.
        motorLeftfront.setPower(motorLeftfrontPower);
        motorRightfront.setPower(motorRightfrontPower);
        motorLeftback.setPower(motorLeftbackPower);
        motorRightback.setPower(motorRightbackPower);
        armMotor.setPower(armMotorPower);
        clawLeft.setPosition(leftposition);
        rightposition = 1-leftposition;
        clawRight.setPosition(rightposition);
        leftposition = clawLeft.getPosition();
        rightposition = clawRight.getPosition();

        // This prints information on the screen.
        telemetry.addData("Mode", "Teleop");
        if (display)
        {
            telemetry.addData("SpeedMode", "Fast");
        }
        else
        {
            telemetry.addData("SpeedMode", "Slow");
        }
        //timeleft = 120 - getRuntime();
        telemetry.addData("Status", "Time Left: " + timeleft);
        telemetry.addData("Motors", "Leftfront (%.2f), Rightfront (%.2f), Leftback (%.2f), Rightback (%.2f)", motorLeftfrontPower, motorRightfrontPower, motorLeftbackPower, motorRightbackPower);
        telemetry.addData("Motor", armMotorPower);
        telemetry.addData("Left Servo Position", leftposition);
        telemetry.addData("Right Servo Position", rightposition);

        // If time is up, then the motors will stop.
        if (timeleft <= 0)
        {
            motorLeftfront.setPower(0);
            motorRightfront.setPower(0);
            motorLeftback.setPower(0);
            motorRightback.setPower(0);
            armMotor.setPower(0);
        }
    }

    // This code will run after the STOP button is pressed.
    @Override
    public void stop() {
    }
}