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

import android.app.Activity;
import android.graphics.Color;
import android.view.View;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;

/*
 * This is an example LinearOpMode that shows how to use
 * the REV Robotics Color-Distance Sensor.
 *
 * Note that the REV Robotics Color-Distance incorporates two sensors into one device.
 * It has a light/distance (range) sensor.  It also has an RGB color sensor.
 * The light/distance sensor saturates at around 2" (5cm).  This means that targets that are 2"
 * or closer will display the same value for distance/light detected.
 * In this example, we  also use the distance sensor to display the distance
 * to the target object.  Note that the distance sensor saturates at around 2" (5 cm).
 *
 * It assumes the sensor is configured with the name "sensor_color_distance".
 */

@Autonomous(name = "colorSensorTest")
// @Disabled
public class colorSensorTest extends LinearOpMode {

    ColorSensor sensorColor;

    @Override
    public void runOpMode() {
        sensorColor = hardwareMap.get(ColorSensor.class, "color");
        float hsvValues[] = {0F, 0F, 0F};
        final float values[] = hsvValues;
        int relativeLayoutId = hardwareMap.appContext.getResources().getIdentifier("RelativeLayout", "id", hardwareMap.appContext.getPackageName());
        final View relativeLayout = ((Activity) hardwareMap.appContext).findViewById(relativeLayoutId);
        waitForStart();

        while (opModeIsActive()) {
            Color.RGBToHSV((int) (sensorColor.red()), (int) (sensorColor.green()), (int) (sensorColor.blue()), hsvValues);
            telemetry.addData("Alpha", sensorColor.alpha());
            telemetry.addData("Red  ", sensorColor.red());
            telemetry.addData("Green", sensorColor.green());
            telemetry.addData("Blue ", sensorColor.blue());
            telemetry.addData("Hue", hsvValues[0]);
            if (hsvValues[0]>=340 && hsvValues[0]<=360 || hsvValues[0]>=0 && hsvValues[0]<=20) {telemetry.addData("Color", "Red");}
            else if (hsvValues[0]>=210 && hsvValues[0]<=275) {telemetry.addData("Color", "Blue");}
            else {telemetry.addData("Color", "None");}
            telemetry.update();
            relativeLayout.post(new Runnable() {public void run() {relativeLayout.setBackgroundColor(Color.HSVToColor(0xff, values));}});
        }
        relativeLayout.post(new Runnable() {public void run() {relativeLayout.setBackgroundColor(Color.WHITE);}});
    }
}
