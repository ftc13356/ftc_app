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

        waitForStart();

        // descend
        telemetry.addData("Status", "Descend"); telemetry.update();
        moveWinch(1, 2500);
        moveWinch(-1, 1000);

        // push central mineral
        telemetry.addData("Status", "Knock Central Mineral"); telemetry.update();
        moveIntake(intakeDown);
        timedForward(50,0.75, 5000);

        // drop team marker
        telemetry.addData("Status", "Drop Team Marker"); telemetry.update();
        expelMarker();
        moveIntake(intakeUp);

        // go to crater
        telemetry.addData("Status", "Going to Crater"); telemetry.update();
        right(27,0.75);
        backward(85, 0.75);

        telemetry.addData("Status", "Everything executed"); telemetry.update();
        stop();

    }
}