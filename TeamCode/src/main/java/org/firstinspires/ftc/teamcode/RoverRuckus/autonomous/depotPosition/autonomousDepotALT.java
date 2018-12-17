package org.firstinspires.ftc.teamcode.RoverRuckus.autonomous.depotPosition;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.RoverRuckus.autonomous.autonomousFrame;

/**
 * Purpose: Alternate Autonomous Program for Depot Position that
 * only knocks the middle mineral, drops team marker, and moves 40 inches left of the depot
 * so we don't crash/interfere with alliance partner
 */

@Autonomous(name = "ALT Autonomous Depot", group = "Depot")
public class autonomousDepotALT extends autonomousFrame {

    @Override
    public void runOpMode() {

        // GO TO
        // autonomousFrame.java TO
        // UPDATE VERSION NUMBER
        // BEFORE EVERY COMMIT

        versionPrint();
        initializeRobot();

        waitForStart();

        //intake down to push random mineral
        moveIntake(intakeDown);
        telemetry.addData("Status", "Knock Random Mineral"); telemetry.update();

        timedForward(50,0.5, 5000);

        //drop team marker
        telemetry.addData("Status", "Drop Team Marker"); telemetry.update();
        expelMarker();
        moveIntake(intakeUp);

        telemetry.addData("Status", "Moving out of the way"); telemetry.update();
        right(35, 0.75);
        backward(40, 0.5);

        stop();

    }
}