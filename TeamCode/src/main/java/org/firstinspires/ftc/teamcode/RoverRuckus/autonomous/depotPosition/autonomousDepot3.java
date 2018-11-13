package org.firstinspires.ftc.teamcode.RoverRuckus.autonomous.depotPosition;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.RoverRuckus.autonomous.autonomousFrame;

///////////////////////////////////////////////////////////////////////////////
// Purpose: Autonomous Program for Depot Position Strategy 3
// Contributors: Ansh Gandhi, Jonathan Ma
///////////////////////////////////////////////////////////////////////////////

@Autonomous(name = "Autonomous Depot Strategy 3", group = "Depot")
public class autonomousDepot3 extends autonomousFrame {

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
        forward(-20,0.5);

        //sampling(); //change sampling to not go forward

        left(90, 0.25);
        forward(10, 0.5);
        left(45, 0.25);
        forward(30, 0.5);
        //drop team marker
        backward(95, 0.5);

        telemetry.addData("Status", "Everything executed");
        telemetry.update();

        stop();

    }
}