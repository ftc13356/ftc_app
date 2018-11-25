package org.firstinspires.ftc.teamcode.RoverRuckus.autonomous.craterPosition;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.RoverRuckus.autonomous.autonomousFrame;

///////////////////////////////////////////////////////////////////////////////
// Purpose: Autonomous Program for Crater Position Strategy 3
// Contributors: Ansh Gandhi, Jonathan Ma
///////////////////////////////////////////////////////////////////////////////

@Autonomous(name = "Autonomous Crater Strategy 3", group = "Crater")
public class autonomousCrater3 extends autonomousFrame {

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

        forward(20, 0.5);

        //intake down to push random mineral
        telemetry.addData("Status", "Knock Random Mineral"); telemetry.update();
        sleep(3000);

        forward(5, 0.5);
        backward(5, 0.5);

        telemetry.addData("Status", "Going to Depot"); telemetry.update();

        left(130, 0.75);
        forward(38, 0.5);
        left(80, 0.75);
        forward(58, 0.5);

        //drop team marker
        telemetry.addData("Status", "Drop Team Marker"); telemetry.update();
        sleep(3000);
        telemetry.addData("Status", "Going to Crater"); telemetry.update();

        backward(90, 0.5);

        telemetry.addData("Status", "Everything executed"); telemetry.update();

        stop();

    }
}