package org.firstinspires.ftc.teamcode.RoverRuckus.autonomous.oldAutonomous.dalyCity;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.teamcode.RoverRuckus.autonomous.autonomousFrame;

/**
 * Purpose: Autonomous Program for Depot Position (Strategy 2)
 * Goes to opposite alliance crater
 * What it does- descending from lander, sampling, delivers team marker, parks in crater
 */

@Autonomous(name = "Daly City Autonomous Depot 2", group = "Depot")
@Disabled
public class autonomousDepot2Daly extends autonomousFrame {

    @Override
    public void runOpMode() {

        // GO TO
        // autonomousFrame.java TO
        // UPDATE VERSION NUMBER
        // BEFORE EVERY COMMIT

        versionPrint();
        initializeRobot();
        initializeTensorFlow();

        telemetry.addData("Status", "Ready"); telemetry.update();
        waitForStart();

        // descend
        telemetry.addData("Status", "Descend"); telemetry.update();
        descend();

        // sampling
        telemetry.addData("Status", "Sampling"); telemetry.update();
        samplingDepot(false);

        // drop team marker
        telemetry.addData("Status", "Drop Team Marker"); telemetry.update();
        expelMarker();
        moveIntake(intakeUp);

        // go to crater
        telemetry.addData("Status", "Going to Crater"); telemetry.update();
        right(secondaryBase - turnCorrection,0.75);
        backward(85, 0.75);

        telemetry.addData("Status", "Everything executed"); telemetry.update();
        stop();

    }
}