package org.firstinspires.ftc.teamcode.RoverRuckus.autonomous.craterPosition;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.RoverRuckus.autonomous.autonomousFrame;

/**
 * Purpose: Autonomous Program for Crater Position (Strategy 2)
 * What it does- sampling, delivers team marker, parks in crater
 */

@Autonomous(name = "Autonomous Crater 2", group = "Depot")
public class autonomousCrater2 extends autonomousFrame {

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

        // sampling
        telemetry.addData("Status", "Sampling"); telemetry.update();
        samplingCrater();

        left(90,0.75);
        forward(70, 0.5);
        right(135, 0.75);
        forward(40,0.5);
        backward(80,0.5);
        telemetry.addData("Status", "Going to Crater"); telemetry.update();
        telemetry.addData("Status", "Everything executed"); telemetry.update();
        stop();

    }
}