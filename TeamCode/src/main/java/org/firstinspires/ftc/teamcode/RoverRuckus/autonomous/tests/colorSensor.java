package org.firstinspires.ftc.teamcode.RoverRuckus.autonomous.tests;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.teamcode.RoverRuckus.autonomous.autonomousFrame;

/**
 * Purpose: Test program using color checker
 */

@Autonomous(name="Autonomous Color Sensor Test")
@Disabled
public class colorSensor extends autonomousFrame {

    @Override
    public void runOpMode() {
        initializeHardwareMap();
        waitForStart();

        //colorChecker sampleCheck = new colorChecker(this);

        //sampleCheck.detectObject();
    }
}