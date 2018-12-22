package org.firstinspires.ftc.teamcode.RoverRuckus.autonomous.depotPosition;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.teamcode.RoverRuckus.autonomous.autonomousFrame;

/**
 * Purpose: Autonomous Program for Depot Position Strategy 2
 */

@Autonomous(name = "Autonomous Depot Strategy 2", group = "Depot")
@Disabled
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

        forward(20,0.5);

        //intake down to push center mineral in Sampling
        telemetry.addData("Status", "Knock center Mineral"); telemetry.update();
        sleep(1500);

        forward(40,0.5);

        //drop team marker
        telemetry.addData("Status", "Drop Team Marker"); telemetry.update();
        sleep(1500);
        telemetry.addData("Status", "Going to Crater"); telemetry.update();

        backward(60, 0.5);
        left(70, 0.75);
        backward(70, 0.5);
        right(130, 0.75);
        forward(20, 0.5);

        telemetry.addData("Status", "Everything executed"); telemetry.update();

        stop();

    }
}