package org.firstinspires.ftc.teamcode.RoverRuckus.autonomous.one_eleven.craterPosition;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.teamcode.RoverRuckus.autonomous.autonomousFrame;

/**
 * Purpose: Autonomous Program for Crater Position (Strategy 1)
 * What it does- sampling, delivers team marker, parks in crater
 */

@Autonomous(name = "Autonomous Crater 1", group = "Crater")
@Disabled
public class autonomousCrater1 extends autonomousFrame {

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
        right(180,0.5);
        samplingCrater();

        telemetry.addData("Status", "Going to Depot"); telemetry.update();
        left(90,0.75);
        forward(65, 0.5);
        right(135, 0.75);
        forward(50,0.5);

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