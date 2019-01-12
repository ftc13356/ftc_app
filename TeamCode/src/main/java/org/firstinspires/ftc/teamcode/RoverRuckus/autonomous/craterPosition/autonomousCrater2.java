package org.firstinspires.ftc.teamcode.RoverRuckus.autonomous.craterPosition;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.teamcode.RoverRuckus.autonomous.autonomousFrame;

/**
 * Purpose: Autonomous Program for Crater Position (Strategy 2)
 * What it does- sampling, delivers team marker, parks in crater
 */

@Autonomous(name = "Autonomous Crater 2", group = "Crater")
@Disabled
public class autonomousCrater2 extends autonomousFrame {

    // THIS IS THE SAME AS CRATER 1!

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
        left(36, 0.75);
        timedForward(40, 0.5, 6000);

        // drop team marker
        telemetry.addData("Status", "Drop Team Marker"); telemetry.update();
        expelMarker();
        moveIntake(intakeUp);

        // go to crater
        telemetry.addData("Status", "Going to Crater"); telemetry.update();
        backward(95,0.5);

        telemetry.addData("Status", "Everything executed"); telemetry.update();
        stop();

    }
}