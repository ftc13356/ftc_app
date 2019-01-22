package org.firstinspires.ftc.teamcode.RoverRuckus.autonomous.depotPosition;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.RoverRuckus.autonomous.autonomousFrame;

/**
 * Purpose: Autonomous Program for Depot Position (Strategy 1)
 * Goes to our alliance's crater
 * What it does- descending from lander, sampling, delivers team marker, parks in crater
 */

@Autonomous(name = "Autonomous Depot 1", group = "Depot")
public class autonomousDepot1 extends autonomousFrame {

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

        /*// descend
        telemetry.addData("Status", "Descend"); telemetry.update();
        descend();*/

        // push central mineral
        telemetry.addData("Status", "Sampling"); telemetry.update();
        samplingDepot(true);

        // drop team marker
        telemetry.addData("Status", "Drop Team Marker"); telemetry.update();
        expelMarker();
        moveIntake(intakeUp);

        // go to crater
        telemetry.addData("Status", "Going to Crater"); telemetry.update();
        telemetry.addData("correction", turnCorrection); telemetry.update();
        left(primaryBase - turnCorrection,0.75);
        backward(85,0.75);

        telemetry.addData("Status", "Everything executed"); telemetry.update();
        stop();

    }
}