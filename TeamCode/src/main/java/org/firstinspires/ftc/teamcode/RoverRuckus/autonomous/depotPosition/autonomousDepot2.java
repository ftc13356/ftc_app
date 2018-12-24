package org.firstinspires.ftc.teamcode.RoverRuckus.autonomous.depotPosition;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.RoverRuckus.autonomous.autonomousFrame;

/**
 * Purpose: Autonomous Program for Depot Position (Strategy 2)
 * What it does- sampling, delivers team marker, parks in crater
 */

@Autonomous(name = "Autonomous Depot 2", group = "Depot")
public class autonomousDepot2 extends autonomousFrame {

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
        samplingDepot();
        left(45,0.75);
        backward(80,0.5);

        stop();

    }
}