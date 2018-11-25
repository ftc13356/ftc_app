package org.firstinspires.ftc.teamcode.RoverRuckus.autonomous.depotPosition;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.RoverRuckus.autonomous.autonomousFrame;

///////////////////////////////////////////////////////////////////////////////
// Purpose: Autonomous Program for Depot Position Strategy 1
// Contributors: Ansh Gandhi, Jonathan Ma
///////////////////////////////////////////////////////////////////////////////

@Autonomous(name = "Autonomous Depot Strategy 1", group = "Depot")
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

        forward(20,0.5);

        //intake down to push random mineral
        telemetry.addData("Status", "Knock Random Mineral"); telemetry.update();
        sleep(3000);

        forward(35,0.5);

        //drop team marker
        telemetry.addData("Status", "Drop Team Marker"); telemetry.update();
        sleep(3000);
        telemetry.addData("Status", "Going to Crater"); telemetry.update();

        left(75, 0.75);
        backward(90, 0.5);

        telemetry.addData("Status", "Everything executed"); telemetry.update();

        stop();

    }
}