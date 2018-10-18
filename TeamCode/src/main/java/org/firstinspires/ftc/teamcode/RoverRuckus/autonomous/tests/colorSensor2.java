package org.firstinspires.ftc.teamcode.RoverRuckus.autonomous.tests;

import android.graphics.Color;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.ColorSensor;

import org.firstinspires.ftc.teamcode.RoverRuckus.autonomous.autonomousFrame;

@Autonomous(name="Colors2!!!!!!!!!!!!!!!!")
public class colorSensor2 extends autonomousFrame {

    public ColorSensor colorSensor;

    @Override
    public void runOpMode() {
        colorSensor = hardwareMap.colorSensor.get("colorSensor");

        float hsvValues[] = {0F, 0F, 0F};

        waitForStart();

        while (opModeIsActive()) {
            //this line throws NullPointer error
            Color.RGBToHSV((int) (colorSensor.red()), (int) (colorSensor.green()), (int) (colorSensor.blue()), hsvValues);

            telemetry.addData("Hue", hsvValues[0]);
            telemetry.update();

            if (hsvValues[0] >= 15 && hsvValues[0] <= 29) {
                encoderDrive(5, 0, 1);
                telemetry.addData("Object Detected", "Gold");
                telemetry.update();
            }
            else if (hsvValues[0] >= 30 && hsvValues[0] <= 40) {
                encoderDrive(-5, 0, 1);
                telemetry.addData("Object Detected", "Silver");
                telemetry.update();
            }
            else {
                telemetry.addData("Object Detected", "Unknown");
                telemetry.update();
            }
        }
    }
}