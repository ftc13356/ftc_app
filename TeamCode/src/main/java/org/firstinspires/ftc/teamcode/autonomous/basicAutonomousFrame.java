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

package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;


@Autonomous(name="Basic Autonomous Frame")
@Disabled
public abstract class basicAutonomousFrame extends LinearOpMode {

    // Defining Motors (Drivetrain)
    public DcMotor motorLeftfront;
    public DcMotor motorRightfront;
    public DcMotor motorLeftback;
    public DcMotor motorRightback;

    // Defining Motor (Arm)
    public DcMotor armMotor;

    // Defining Servos (Arm)
    public Servo clawLeft;
    public Servo clawRight;

    // Defining Variables (Claw)
    double leftPosition = 0;
    double rightPosition = 1;

    // Defining Variables (Drivetrain, Encoder)
    int targetValue = 0;

    // Arm Position Function (Arm, Encoder)

    public void armPosition(int position) {
        if (position == 0){
            targetValue = -100;
        }
        else if (position == 1){
            targetValue = -1900;
        }
        else if (position == 2){
            targetValue = -3000;
        }
        else if (position == 3){
            targetValue = -4400;
        }
        else if (position == 4){
            targetValue = -5700;
        }
        else {
            targetValue = position;
        }
        armMotor.setTargetPosition(targetValue);
        armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        telemetry.addData("Goal Position", "%7d",targetValue);
    }


    // Claw Functions (Arm)
    public void closeClaw() {
        leftPosition = 0.6;
        telemetry.addData("Servo Status", "Closed");
        clawLeft.setPosition(leftPosition);
        rightPosition = 1 - leftPosition;
        clawRight.setPosition(rightPosition);
    }

    public void partialClaw() {
        leftPosition = 0.48;
        telemetry.addData("Servo Status", "Partially Open");
        clawLeft.setPosition(leftPosition);
        rightPosition = 1 - leftPosition;
        clawRight.setPosition(rightPosition);
    }

    public void openClaw() {
        leftPosition = 0;
        telemetry.addData("Servo Status", "Open");
        clawLeft.setPosition(leftPosition);
        rightPosition = 1 - leftPosition;
        clawRight.setPosition(rightPosition);
    }



    // Basic Drive Functions (Drivetrain)
    public void moveForward(double power, long time) {
        motorLeftfront.setPower(-power);
        motorRightfront.setPower(power);
        motorLeftback.setPower(-power);
        motorRightback.setPower(power);
        sleep(time);
    }

    public void moveLeft (double power, long time) {
        motorLeftfront.setPower(power);
        motorRightfront.setPower(power);
        motorLeftback.setPower(-power);
        motorRightback.setPower(-power);
        sleep(time);
    }

    public void  moveRight (double power, long time) {
        moveLeft(-power, time);
    }

    public void  moveBackward (double power, long time) {
        moveForward(-power, time);
    }

    public void Stop () {
        stop();
    }


    // Combined Drive Functions (Drivetrain)
    public void drive(double driveFB, double driveS, double turn, long time, double speedfactor) {
        // Define Speed Variables
        double motorLeftfrontPower;
        double motorRightfrontPower;
        double motorLeftbackPower;
        double motorRightbackPower;

        // Calculating Power
        motorLeftfrontPower = Range.clip((-driveFB + driveS + turn)*speedfactor, -1.0, 1.0);
        motorRightfrontPower = Range.clip((driveFB + driveS + turn)*speedfactor, -1.0, 1.0);
        motorLeftbackPower = Range.clip((-driveFB - driveS + turn)*speedfactor, -1.0, 1.0);
        motorRightbackPower = Range.clip((driveFB - driveS + turn)*speedfactor, -1.0, 1.0);

        // Set Motor Power to Calculated Power
        motorLeftfront.setPower(motorLeftfrontPower);
        motorRightfront.setPower(motorRightfrontPower);
        motorLeftback.setPower(motorLeftbackPower);
        motorRightback.setPower(motorRightbackPower);
        
        sleep(time);

        telemetry.addData("Motors", "Leftfront (%.2f), Rightfront (%.2f), Leftback (%.2f), Rightback (%.2f)", motorLeftfrontPower, motorRightfrontPower, motorLeftbackPower, motorRightbackPower);
        telemetry.update();
    }

    public void driveStop(long time) {
        stop();
        sleep(time);
        telemetry.addData("Motors", "Leftfront:0, Rightfront:0, Leftback:0, Rightback:0");
        telemetry.update();
    }


    // Combined Drive Function (Drivetrain, Encoder)
    public void driveEncoder(double driveFB, double driveS, double turn, double inchFactor, double encoderFactor, double speed) {
        // Defines Variables
        int newLeftfrontTarget;
        int newRightfrontTarget;
        int newLeftbackTarget;
        int newRightbackTarget;

        // Calculates Target Position
        double motorLeftfrontEncoder = (-driveFB + driveS + turn)*encoderFactor;
        double motorRightfrontEncoder = (driveFB + driveS + turn)*encoderFactor;
        double motorLeftbackEncoder = (-driveFB - driveS + turn)*encoderFactor;
        double motorRightbackEncoder = (driveFB - driveS + turn)*encoderFactor;

        // Ensures OpMode is Active
        if (opModeIsActive()) {

            // Calculates Target Position Based on Current Position
            newLeftfrontTarget = motorLeftfront.getCurrentPosition() + (int) (motorLeftfrontEncoder * inchFactor);
            newRightfrontTarget = motorRightfront.getCurrentPosition() + (int) (motorRightfrontEncoder * inchFactor);
            newLeftbackTarget = motorLeftback.getCurrentPosition() + (int) (motorLeftbackEncoder * inchFactor);
            newRightbackTarget = motorRightback.getCurrentPosition() + (int) (motorRightbackEncoder * inchFactor);

            // Sets Target Position for Motors
            motorLeftfront.setTargetPosition(newLeftfrontTarget);
            motorRightfront.setTargetPosition(newRightfrontTarget);
            motorLeftback.setTargetPosition(newLeftbackTarget);
            motorRightback.setTargetPosition(newRightbackTarget);

            // Changes Motor Mode
            motorLeftfront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            motorRightfront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            motorLeftback.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            motorRightback.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            // Sets Power to Motors
            motorLeftfront.setPower(Math.abs(speed));
            motorRightfront.setPower(Math.abs(speed));
            motorLeftback.setPower(Math.abs(speed));
            motorRightback.setPower(Math.abs(speed));

            // Displays Target and Current Position When Active OpMode and Active Motor(s)
            while (opModeIsActive() && (motorLeftfront.isBusy() || motorRightfront.isBusy() || motorLeftback.isBusy() || motorRightback.isBusy())) {

                // Displays Target and Current Positions
                telemetry.addData("Target Value", "Running to %7d :%7d :%7d :%7d", newLeftfrontTarget, newRightfrontTarget, newLeftbackTarget, newRightbackTarget);
                telemetry.addData("Current Value", "Running at %7d :%7d: %7d :%7d",
                        motorLeftfront.getCurrentPosition(),
                        motorRightfront.getCurrentPosition(),
                        motorLeftback.getCurrentPosition(),
                        motorRightback.getCurrentPosition());
                telemetry.update();
            }

            // Stops Motors
            motorLeftfront.setPower(0);
            motorRightfront.setPower(0);
            motorLeftback.setPower(0);
            motorRightback.setPower(0);

            // Changes Motor Mode
            motorLeftfront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            motorRightfront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            motorLeftback.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            motorRightback.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
    }
}
