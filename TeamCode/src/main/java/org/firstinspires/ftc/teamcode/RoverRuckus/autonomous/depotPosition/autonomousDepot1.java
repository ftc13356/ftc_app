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
        setMotorDirection();

        waitForStart();

        forward(12,0.5);
        left(100, 0.5);
        forward(35,0.5); // 20 for sampling

        //sampling();

        right(135, 0.5);
        forward(40,0.5);
        // drop team marker
        right(100, 0.5);
        forward(95, 0.5);

        telemetry.addData("Status", "Everything executed");
        telemetry.update();

        stop();

    }
}