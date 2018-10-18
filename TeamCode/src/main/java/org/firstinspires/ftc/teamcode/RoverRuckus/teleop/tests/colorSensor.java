package org.firstinspires.ftc.teamcode.RoverRuckus.teleop.tests;

import android.graphics.Color;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;

@TeleOp(name = "Colors!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!")
public class colorSensor extends OpMode{

    private ColorSensor colorSensor;

    private float hsvValues[] = {0F, 0F, 0F};

    @Override
    public void init() {
        colorSensor = hardwareMap.colorSensor.get("colorSensor");
    }

    @Override
    public void loop() {
        Color.RGBToHSV((colorSensor.red()), (colorSensor.green()), (colorSensor.blue()), hsvValues);
        telemetry.addData("Hue", hsvValues[0]);
        if (hsvValues[0] >= 15 && hsvValues[0] <= 29) {
            telemetry.addData("Object Detected", "Gold");
        }
        if (hsvValues[0] >= 30 && hsvValues[0] <= 40) {
            telemetry.addData("Object Detected", "Silver");
        }
        else {
            telemetry.addData("Object Detected", "Unknown");
        }
    }
}
