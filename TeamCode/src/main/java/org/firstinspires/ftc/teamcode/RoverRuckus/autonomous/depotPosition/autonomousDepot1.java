package org.firstinspires.ftc.teamcode.RoverRuckus.autonomous.depotPosition;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.RoverRuckus.autonomous.autonomousFrame;

/**
 * Purpose: Autonomous Program for Depot Position
 */

@Autonomous(name = "Autonomous Depot", group = "Depot")
public class autonomousDepot1 extends autonomousFrame {

    @Override
    public void runOpMode() {

        // GO TO
        // autonomousFrame.java TO
        // UPDATE VERSION NUMBER
        // BEFORE EVERY COMMIT

        versionPrint();

        initializeHardwareMap();
        initializeMotors();

        waitForStart();

        //intake down to push random mineral
        lowerIntake();
        telemetry.addData("Status", "Knock Random Mineral"); telemetry.update();

        timedForward(50,0.5, 5000);

        //drop team marker
        telemetry.addData("Status", "Drop Team Marker"); telemetry.update();
        expelMarker();

        telemetry.addData("Status", "Going to Crater"); telemetry.update();

        left(35, 0.75);
        backward(80, 0.5);
        backward(5, 0.75);

        telemetry.addData("Status", "Everything executed"); telemetry.update();

        stop();

    }
}