package org.firstinspires.ftc.teamcode.RoverRuckus.autonomous.oldAutonomous.dalyCity;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.teamcode.RoverRuckus.autonomous.autonomousFrame;

/**
 * Purpose: Autonomous Program for Crater Position (Strategy 1)
 * Goes to our alliance's crater
 * What it does- descending from lander, sampling, parks in crater
 */

@Autonomous(name = "Daly City Autonomous Crater 1", group = "Crater")
@Disabled
public class autonomousCrater1Daly extends autonomousFrame {

    @Override
    public void runOpMode() {

        // GO TO
        // autonomousFrame.java TO
        // UPDATE VERSION NUMBER
        // BEFORE EVERY COMMIT

        versionPrint();
        initializeRobot();
        initializeTensorFlow();

        waitForStart();

        // descend
        telemetry.addData("Status", "Descend"); telemetry.update();
        descend();

        // sampling
        telemetry.addData("Status", "Sampling"); telemetry.update();
        samplingCrater(true);

        // go to crater
        telemetry.addData("Status", "Going to Crater"); telemetry.update();
        forward(7, 0.75);

        telemetry.addData("Status", "Everything executed"); telemetry.update();
        stop();

    }
}