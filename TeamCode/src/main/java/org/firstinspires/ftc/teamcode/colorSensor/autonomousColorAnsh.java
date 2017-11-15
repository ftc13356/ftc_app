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

package org.firstinspires.ftc.teamcode.colorSensor;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.autonomous.basicAutonomousFrame;

@Autonomous(name="Ansh Autonomous Color")
@Disabled
public class autonomousColorAnsh extends basicAutonomousFrame
{
    // Defining Autonomous OpMode Members
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor motorLeftfront;
    private DcMotor motorRightfront;
    private DcMotor motorLeftback;
    private DcMotor motorRightback;
    ColorSensor sensorColor;

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
        sensorColor = hardwareMap.get(ColorSensor.class, "color");

        // Sets Motor Directions to Forward
        motorLeftfront.setDirection(DcMotor.Direction.FORWARD);
        motorRightfront.setDirection(DcMotor.Direction.FORWARD);
        motorLeftback.setDirection(DcMotor.Direction.FORWARD);
        motorRightback.setDirection(DcMotor.Direction.FORWARD);

        // Tells Driver Initialization is Complete
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        // Wait For the Driver To Press the Start Button
        waitForStart();
        runtime.reset();

        // ALL CODE HERE!!!
        float huevalue = CheckColor();
        drive(0,1,0,500,0.25);
        if (runtime.milliseconds() >= 500 || huevalue >= 340 && huevalue <= 360 || huevalue >= 0 && huevalue <= 20 || huevalue >= 210 && huevalue <= 275)
        {
            stop();
        }
        // Put Glyph into Cryptobox
        // drive(1,0,0,1500,0.25);
        // drive(0,0,1,1000,0.25);
        // drive(1,0,0,750,0.25);
        // drive(-1,0,0,1000,0.25);
        // drive(0,0,1,2000,0.25);
        // drive(1,0,0,1500,0.25);

        // Tells Driver Time Left
        telemetry.addData("Status", "Time Left: " + (30 - runtime.seconds()));
        telemetry.update();
    }
    // Defining CheckColor, It prints if the color checked is red or blue.
    public float CheckColor() {
        float mastervalue=0;
        while (opModeIsActive()) {
            float hsvValues[] = {0F, 0F, 0F};
            final float values[] = hsvValues;
            int relativeLayoutId = hardwareMap.appContext.getResources().getIdentifier("RelativeLayout", "id", hardwareMap.appContext.getPackageName());
            final View relativeLayout = ((Activity) hardwareMap.appContext).findViewById(relativeLayoutId);
            telemetry.addData("Hue", hsvValues[0]);
            if (hsvValues[0] >= 340 && hsvValues[0] <= 360 || hsvValues[0] >= 0 && hsvValues[0] <= 20) {
                telemetry.addData("Color", "Red");
                break;
            }
            else if (hsvValues[0] >= 210 && hsvValues[0] <= 275) {
                telemetry.addData("Color", "Blue");
                break;
            }
            else {telemetry.addData("Color", "None");}
            telemetry.update();
            Color.RGBToHSV((int) (sensorColor.red()), (int) (sensorColor.green()), (int) (sensorColor.blue()), hsvValues);
            relativeLayout.post(new Runnable() {public void run() {relativeLayout.setBackgroundColor(Color.HSVToColor(0xff, values));}});
            relativeLayout.post(new Runnable() {public void run() {relativeLayout.setBackgroundColor(Color.WHITE);}});
            mastervalue = hsvValues[0];
        }
        return mastervalue;
    }
}