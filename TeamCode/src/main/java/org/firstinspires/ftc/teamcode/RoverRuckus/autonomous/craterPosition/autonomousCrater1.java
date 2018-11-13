package org.firstinspires.ftc.teamcode.RoverRuckus.autonomous.craterPosition;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.RoverRuckus.autonomous.autonomousFrame;

///////////////////////////////////////////////////////////////////////////////
// Purpose: Autonomous Program for Crater Position Strategy 1
// Contributors: Ansh Gandhi, Jonathan Ma
///////////////////////////////////////////////////////////////////////////////

@Autonomous(name = "Autonomous Crater Strategy 1", group = "Crater")
public class autonomousCrater1 extends autonomousFrame {

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

        forward(53,0.5);
        right(45, 0.5);
        forward(14, 0.5);
        right(45, 0.5);
        forward(30, 0.5);
        //Drop Team Marker
        right(90, 0.5);
        forward(63, 0.5);
        right(45, 0.5);
        //Do Sampling
        forward(60, 0.5);//in place of sampling
        left(135, 0.5);
        forward(26, 0.5);//over the crater hump

        telemetry.addData("Status", "Everything executed");
        telemetry.update();

        stop();

    }
}