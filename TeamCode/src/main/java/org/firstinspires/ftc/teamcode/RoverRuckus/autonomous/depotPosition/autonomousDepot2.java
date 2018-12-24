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

        // drop team marker
        telemetry.addData("Status", "Drop Team Marker"); telemetry.update();
        //expelMarker();
        moveIntake(intakeUp);
        // right(35, 0.75)??

        // go to crater
        telemetry.addData("Status", "Going to Crater"); telemetry.update();
        backward(70, 0.5);
        backward(5, 0.75);

        telemetry.addData("Status", "Everything executed"); telemetry.update();
        stop();

    }
}