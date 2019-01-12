package org.firstinspires.ftc.teamcode.RoverRuckus.autonomous.one_eleven.craterPosition;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.teamcode.RoverRuckus.autonomous.autonomousFrame;

/**
 * Purpose: Autonomous Program for Crater Position (Strategy 1)
 * What it does- sampling, delivers team marker, parks in crater
 */

@Autonomous(name = "Autonomous Crater Double Sampling", group = "Crater")
@Disabled
public class autonomousCraterDoubleSampling extends autonomousFrame {

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

        // go into depot starting position in order to do the depot sampling
        telemetry.addData("Status", "Going to Depot"); telemetry.update();
        left(90, 0.75);
        forward(45, 0.5);
        left(90,0.75);
        forward(33,0.5);
        left(180,0.75);

        // sampling
        telemetry.addData("Status", "Sampling"); telemetry.update();
        samplingDepot();

        // drop team marker
        telemetry.addData("Status", "Drop Team Marker"); telemetry.update();
        expelMarker();
        moveIntake(intakeUp);

        // go to crater
        telemetry.addData("Status", "Going to Crater"); telemetry.update();
        backward(70, 0.75);
        backward(5, 0.75);

        telemetry.addData("Status", "Everything executed"); telemetry.update();
        stop();

    }
}