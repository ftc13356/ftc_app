package org.firstinspires.ftc.teamcode.RoverRuckus.autonomous.depotPosition;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.RoverRuckus.autonomous.autonomousFrame;

///////////////////////////////////////////////////////////////////////////////
// Purpose: Autonomous Program for Depot Position Strategy 2
// Contributors: Ansh Gandhi, Jonathan Ma
///////////////////////////////////////////////////////////////////////////////

@Autonomous(name = "Autonomous Depot Strategy 2", group = "Depot")
public class autonomousDepot2 extends autonomousFrame {

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
        left(90, 0.25);
        forward(20,0.5);

        //sampling();

        right(135, 0.25);
        forward(40,0.5);
        // drop team marker
        backward(50, 0.5);
        right(90, 0.25);
        forward(95, 0.5);
        left(90, 0.25);
        forward(10, 0.5);

        telemetry.addData("Status", "Everything executed");
        telemetry.update();

        stop();

    }
}