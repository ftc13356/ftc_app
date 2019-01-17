package org.firstinspires.ftc.teamcode.RoverRuckus.autonomous.oldAutonomous.walnutCreek;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.teamcode.RoverRuckus.autonomous.autonomousFrame;

/**
 * Purpose: Autonomous Program for Depot Position (Strategy 1)
 * Goes to our alliance's crater
 * What it does- descending from lander, knocks central mineral in sampling field,
 *               delivers team marker, parks in crater
 */

@Autonomous(name = "Walnut Creek Autonomous Depot 1", group = "Depot")
@Disabled
public class autonomousDepot1Walnut extends autonomousFrame {

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
        moveWinch(1, 2000);
        timedForward(15, 0.2, 1500);
        moveWinch(1, 2000);
        moveWinch(-1, 1500);

        // push central mineral
        telemetry.addData("Status", "Knock Central Mineral"); telemetry.update();
        moveIntake(intakeDown);
        timedForward(47,0.75, 5000);

        // drop team marker
        telemetry.addData("Status", "Drop Team Marker"); telemetry.update();
        expelMarker();
        moveIntake(intakeUp);

        // go to crater
        telemetry.addData("Status", "Going to Crater"); telemetry.update();
        left(45,0.75);
        backward(85,0.75);

        telemetry.addData("Status", "Everything executed"); telemetry.update();
        stop();

    }
}