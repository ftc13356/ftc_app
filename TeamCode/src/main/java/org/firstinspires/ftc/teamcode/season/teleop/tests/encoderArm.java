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

package org.firstinspires.ftc.teamcode.season.teleop.tests;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name = "Arm with Encoder")
@Disabled
public class encoderArm extends OpMode {

    // This is declaring the hardware.
    public ElapsedTime runtime = new ElapsedTime();
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
        armMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    // This code is just defining the variables needed for the loop.
    private double leftposition = 0;
    private double rightposition = 1;
    private double armSpeed = 0.5;

    int targetValue = 0;

    // This code will run constantly after the previous part is runned.
    @Override
    public void loop() {
        if(gamepad1.left_bumper) {
            leftposition = 0.6;
            telemetry.addData("Servo Status", "Closed");
        }
        if(gamepad1.right_bumper) {
            leftposition = 0;
            telemetry.addData("Servo Status", "Open");
        }

        //4 counts per degree
        //all values should be negative
        if (gamepad1.dpad_down) {
            targetValue = -100;
            armMotor.setTargetPosition(targetValue);
            armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            telemetry.addData("Goal Position", "%7d",targetValue);
        }

        if (gamepad1.a) {
            targetValue = -1900;
            armMotor.setTargetPosition(targetValue);
            armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            telemetry.addData("Goal Position", "%7d",targetValue);
        }
        if (gamepad1.x) {
            targetValue = -3000;
            armMotor.setTargetPosition(targetValue);
            armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            telemetry.addData("Goal Position", "%7d",targetValue);
        }
        if (gamepad1.y) {
            targetValue = -4400;
            armMotor.setTargetPosition(targetValue);
            armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            telemetry.addData("Goal Position", "%7d",targetValue);
        }
        if (gamepad1.b) {
            targetValue = -5700;
            armMotor.setTargetPosition(targetValue);
            armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            telemetry.addData("Goal Position", "%7d",targetValue);
        }
        if (targetValue!= 0) {
            armMotor.setPower(armSpeed);
        }

        telemetry.addData("Current Position", "%7d", armMotor.getCurrentPosition());

        // The calculated power is then applied to the motors.
        clawLeft.setPosition(leftposition);
        rightposition = 1 - leftposition;
        clawRight.setPosition(rightposition);
        leftposition = clawLeft.getPosition();
        rightposition = clawRight.getPosition();

        telemetry.addData("Motor", armSpeed);
        telemetry.addData("Left Servo Position", leftposition);
        telemetry.addData("Right Servo Position", rightposition);
    }

    // This code will run after the STOP button is pressed.
    @Override
    public void stop() {
    }
}