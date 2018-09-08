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

package org.firstinspires.ftc.teamcode.RelicRecovery.autonomous.tests;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.RelicRecovery.autonomous.seasonAutonomousFrame;

@Autonomous(name="Ansh Autonomous")
@Disabled
public class autonomousAnsh extends seasonAutonomousFrame
{
    // Defining Autonomous OpMode Members
    private ElapsedTime runtime = new ElapsedTime();

    @Override
    public void runOpMode() throws InterruptedException
    {
        // Tells Driver Initialization is Starting
        telemetry.addData("Status", "Initializing");
        telemetry.update();

        // Sets Variable Names to Hardware Map Names
        motorLeftfront = hardwareMap.get(DcMotor.class, "motorLeftfront");
        motorRightfront = hardwareMap.get(DcMotor.class, "motorRightfront");
        motorLeftback = hardwareMap.get(DcMotor.class, "motorLeftback");
        motorRightback = hardwareMap.get(DcMotor.class, "motorRightback");

        // Sets Motor Directions to Forward
        motorLeftfront.setDirection(DcMotor.Direction.FORWARD);
        motorRightfront.setDirection(DcMotor.Direction.FORWARD);
        motorLeftback.setDirection(DcMotor.Direction.FORWARD);
        motorRightback.setDirection(DcMotor.Direction.FORWARD);

        // Tells Driver Initialization is Conplete
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        // Wait For the Driver To Press the Start Button
        waitForStart();
        runtime.reset();

        // ALL CODE HERE!!!
        drive(1,0,0,500,0.25);
        drive(0,1,0,500,0.25);
        drive(-1,0,0,500,0.25);
        drive(0,-1,0,500,0.25);

        // Tells Driver Time Left
        telemetry.addData("Status", "Time Left: " + (30 - runtime.seconds()));
        telemetry.update();
    }

    // Combined Drive Functions (Drivetrain)
    public void drive(double driveFB, double driveS, double turn, long time, double speedControl) {
        // Define Speed Variables
        double motorLeftfrontPower;
        double motorRightfrontPower;
        double motorLeftbackPower;
        double motorRightbackPower;

        // Calculating Power
        motorLeftfrontPower = Range.clip((-driveFB + driveS + turn) * speedControl, -1.0, 1.0);
        motorRightfrontPower = Range.clip((driveFB + driveS + turn) * speedControl, -1.0, 1.0);
        motorLeftbackPower = Range.clip((-driveFB - driveS + turn) * speedControl, -1.0, 1.0);
        motorRightbackPower = Range.clip((driveFB - driveS + turn) * speedControl, -1.0, 1.0);

        // Set Motor Power to Calculated Power
        motorLeftfront.setPower(motorLeftfrontPower);
        motorRightfront.setPower(motorRightfrontPower);
        motorLeftback.setPower(motorLeftbackPower);
        motorRightback.setPower(motorRightbackPower);

        sleep(time);

        telemetry.addData("Motors", "Leftfront (%.2f), Rightfront (%.2f), Leftback (%.2f), Rightback (%.2f)", motorLeftfrontPower, motorRightfrontPower, motorLeftbackPower, motorRightbackPower);
        telemetry.update();
    }
}
