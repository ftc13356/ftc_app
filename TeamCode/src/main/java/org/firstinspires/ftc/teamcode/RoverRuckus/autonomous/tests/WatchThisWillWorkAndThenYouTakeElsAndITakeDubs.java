package org.firstinspires.ftc.teamcode.RoverRuckus.autonomous.tests;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.RoverRuckus.autonomous.autonomousFrame;

import java.util.List;

/**
 * Purpose: Vuforia Navigation Test In Autonomous
 */

@Autonomous(name = "Autonomous Crater 1", group = "Crater")
public class WatchThisWillWorkAndThenYouTakeElsAndITakeDubs extends autonomousFrame {

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

        List locationData = MoveToLocation(200,100,0);

        // descend
        telemetry.addData("Status", "Descend"); telemetry.update();
        moveWinch(1, 5000);

        // push central mineral
        telemetry.addData("Status", "Knock Random Mineral"); telemetry.update();
        moveIntake(intakeDown);
        timedForward(23, 0.5, 5000);
        backward(11, 0.5);

        // go to depot
        telemetry.addData("Status", "Going to Depot"); telemetry.update();
        left(70, 0.75);
        timedForward(41, 0.5, 5000);
        left(40, 0.75);
        timedForward(40, 0.5, 6000);

        // drop team marker
        telemetry.addData("Status", "Drop Team Marker"); telemetry.update();
        expelMarker();
        moveIntake(intakeUp);

        // go to crater
        telemetry.addData("Status", "Going to Crater"); telemetry.update();
        backward(75, 0.5);
        backward(5, 0.75);

        telemetry.addData("Status", "Everything executed"); telemetry.update();
        stop();

    }
}