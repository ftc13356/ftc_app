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

package org.firstinspires.ftc.teamcode.autonomous.colorSensor;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;


@Autonomous(name="Jonathan Autonomous Color")
@Disabled
public class autonomousColorJonathan extends LinearOpMode {

    public DcMotor motorLeftfront;
    public DcMotor motorRightfront;
    public DcMotor motorLeftback;
    public DcMotor motorRightback;

    ColorSensor sensorColor;

    boolean colorRed = false;
    boolean colorBlue = false;
    boolean colorGreen = false;

    @Override
    public void runOpMode() {

        motorLeftfront = hardwareMap.get(DcMotor.class, "motorLeftfront");
        motorRightfront = hardwareMap.get(DcMotor.class, "motorRightfront");
        motorLeftback = hardwareMap.get(DcMotor.class, "motorLeftback");
        motorRightback = hardwareMap.get(DcMotor.class, "motorRightback");

        sensorColor = hardwareMap.get(ColorSensor.class, "color");
        float hsvValues[] = {0F, 0F, 0F};

        motorLeftfront.setDirection(DcMotor.Direction.FORWARD);
        motorRightfront.setDirection(DcMotor.Direction.FORWARD);
        motorLeftback.setDirection(DcMotor.Direction.FORWARD);
        motorRightback.setDirection(DcMotor.Direction.FORWARD);

        waitForStart();

        while (opModeIsActive()) {

            moveForward(.1,5000);

            if (hsvValues[0] >= 340 && hsvValues[0] <= 20) {
                telemetry.addData("Color", "Red");
                telemetry.update();
                colorRed = true;
            }
            if (hsvValues[0] >= 100 && hsvValues[0] <= 140) {
                telemetry.addData("Color", "Blue");
                telemetry.update();
                colorBlue = true;
            }
            if (hsvValues[0] >= 210 && hsvValues[0] <= 275) {
                telemetry.addData("Color", "Green");
                telemetry.update();
                colorGreen = true;
            }
            if (colorRed) {
                Stop();
            }
        }


    }

    public void moveForward(double power, long time) {
        motorLeftfront.setPower(-power);
        motorRightfront.setPower(power);
        motorLeftback.setPower(-power);
        motorRightback.setPower(power);
        sleep(time);
    }

    public void moveLeft(double power, long time) {
        motorLeftfront.setPower(power);
        motorRightfront.setPower(power);
        motorLeftback.setPower(-power);
        motorRightback.setPower(-power);
        sleep(time);
    }

    public void moveRight(double power, long time) {
        moveLeft(-power, time);
    }

    public void moveBackward(double power, long time) {
        moveForward(-power, time);
    }

    public void Stop() {
        stop();
    }

    public void checkColor () {

    }


}