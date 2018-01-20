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

package org.firstinspires.ftc.teamcode.autonomous.tests.colorSensor;

import android.graphics.Color;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.autonomous.autonomousFrame;

@Autonomous(name="Ansh Autonomous Color")
// @Disabled
public class autonomousColorAnsh extends autonomousFrame {

    ColorSensor sensorColor;

    @Override
    public void runOpMode() throws InterruptedException {

        // Initialization
        initializeHardwareMap();
        sensorColor = hardwareMap.get(ColorSensor.class, "color");
        setMotorDirection();

        // Defining Variables
        boolean detect = false;
        float allianceColor;
        String displayText = "";
        ElapsedTime runtime = new ElapsedTime();

        waitForStart();

        runtime.reset();

        allianceColor = 1;

        reactToDetect(5000, 6, allianceColor, detect, displayText, runtime);
    }

    // Defining checkColor, Prints color (red/blue) and returns value
    public float checkColor() {

        float masterValue = 0;
        float hsvValues[] = {0F, 0F, 0F};
        Color.RGBToHSV((int) (sensorColor.red()), (int) (sensorColor.green()), (int) (sensorColor.blue()), hsvValues);
        telemetry.addData("Hue", hsvValues[0]);
        if (hsvValues[0] >= 340 || hsvValues[0] <= 20) {
            masterValue = 1;
        }
        if (hsvValues[0] >= 210 && hsvValues[0] <= 275) {
            masterValue = 2;
        }

        telemetry.update();

        return masterValue;
    }

    // Defining reactToDetect, Moves depending on relationship between jewel and alliance color, detects color again if red/blue not detected
    public void reactToDetect(double checkTime, double distance, float allianceColor, boolean detect, String displayText, ElapsedTime runtime) {

        float colorValue = checkColor();
        while (opModeIsActive() && detect == false && runtime.milliseconds() <= checkTime) {
            colorValue = checkColor();
            if (colorValue == 1) {
                displayText = "Red";
                detect = true;

                if (colorValue == allianceColor) {
                    encoderDrive(-distance,0,0,0.3);
                }
                else {
                    encoderDrive(distance,0,0,0.3);
                }
            }
            if (colorValue == 2) {
                displayText = "Blue";
                detect = true;

                if (colorValue == allianceColor) {
                    encoderDrive(-distance,0,0,0.3);
                }
                else {
                    encoderDrive(distance,0,0,0.3);
                }
            }
        }
        if (colorValue == 0) {
            displayText = "Unknown";
        }
        telemetry.addData("Color Identified:", displayText);
        telemetry.update();
    }
}