package org.firstinspires.ftc.teamcode.RoverRuckus.autonomous.craterPosition;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.RoverRuckus.autonomous.autonomousFrame;

/**
 * Purpose: Autonomous Program for Crater Position
 */

@Autonomous(name = "Autonomous Crater", group = "Crater")
public class autonomousCrater1 extends autonomousFrame {

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
        sleep(1500);

        forward(5, 0.5);
        backward(7, 0.5);

        telemetry.addData("Status", "Going to Depot"); telemetry.update();

        left(130, 0.75);
        forward(38, 0.5);
        left(80, 0.75);
        forward(58, 0.5);

        //drop team marker
        telemetry.addData("Status", "Drop Team Marker"); telemetry.update();
        sleep(1500);
        telemetry.addData("Status", "Going to Crater"); telemetry.update();

        backward(100, 0.5);

        telemetry.addData("Status", "Everything executed"); telemetry.update();

        stop();

    }
}