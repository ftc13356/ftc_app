package org.firstinspires.ftc.teamcode.RoverRuckus.autonomous.craterPosition;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.RoverRuckus.autonomous.autonomousFrame;

///////////////////////////////////////////////////////////////////////////////
// Purpose: Autonomous Program for Crater Position Strategy 2
// Contributors: Ansh Gandhi, Jonathan Ma
///////////////////////////////////////////////////////////////////////////////

@Autonomous(name = "Autonomous Crater Strategy 2", group = "Crater")
public class autonomousCrater2 extends autonomousFrame {

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

        forward(67, 0.5);
        right(90, 0.5);
        forward(49, 0.5);
        //drop team marker
        right(180, 0.5);
        forward(49, 0.5);
        left(90, 0.5);
        forward(68, 0.5);
        left(90, 0.5);
        forward(24, 0.5);
        right(135, 0.5);
        //do sampling
        forward(42, 0.5); // in place of sampling
        left(135, 0.5);
        forward(26, 0.5);//over the crater hump

        telemetry.addData("Status", "Everything executed");
        telemetry.update();

        stop();

    }
}