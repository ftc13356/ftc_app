package org.firstinspires.ftc.teamcode.RoverRuckus.autonomous.craterPosition;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.RoverRuckus.autonomous.autonomousFrame;

/**
 * Purpose: Autonomous Program for Crater Position (Strategy 1)
 * Goes to our alliance's crater
 * What it does- descending from lander, knocks central mineral in sampling field,
 *               delivers team marker, parks in crater
 */

@Autonomous(name = "Autonomous Crater 1", group = "Crater")
public class autonomousCrater1 extends autonomousFrame {

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
        timedForward(21, 0.75, 2000);
        timedBackward(11, 0.75, 2000);

        // go to depot
        telemetry.addData("Status", "Going to Depot"); telemetry.update();
        left(70, 0.75);
        timedForward(41, 0.75, 3000);
        left(48, 0.75);
        timedForward(40, 0.75, 3000);

        // drop team marker
        telemetry.addData("Status", "Drop Team Marker"); telemetry.update();
        expelMarker();
        moveIntake(intakeUp);

        // go to crater
        telemetry.addData("Status", "Going to Crater"); telemetry.update();
        backward(75, 0.75);
        backward(5, 0.75);

        telemetry.addData("Status", "Everything executed"); telemetry.update();
        stop();

    }
}