package org.firstinspires.ftc.teamcode.RoverRuckus.autonomous.craterPosition;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.teamcode.RoverRuckus.autonomous.autonomousFrame;

/**
 * Purpose: Autonomous Program for Crater Position (Strategy 2)
 * Goes to our alliance's crater
 * What it does- descending from lander, sampling, delivers team marker (if center), parks in crater
 */

@Autonomous(name = "Autonomous Crater 2", group = "Crater")
@Disabled
public class autonomousCrater2 extends autonomousFrame {

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

        // descend
        telemetry.addData("Status", "Descend"); telemetry.update();
        descend();

        // sampling
        telemetry.addData("Status", "Sampling"); telemetry.update();
        samplingCrater(false);

        if (gotoDepot) {
            // go to depot
            telemetry.addData("Status", "Going to Depot"); telemetry.update();
            backward(11, 0.75);
            left(70, 0.75);
            timedForward(40, 0.75, 3000);
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
        } else {
            // go to crater
            telemetry.addData("Status", "Going to Crater"); telemetry.update();
            forward(7, 0.75);
        }

        telemetry.addData("Status", "Everything executed"); telemetry.update();
        stop();

    }
}