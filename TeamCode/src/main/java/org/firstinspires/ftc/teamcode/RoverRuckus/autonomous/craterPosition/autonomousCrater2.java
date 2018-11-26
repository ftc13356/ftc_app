package org.firstinspires.ftc.teamcode.RoverRuckus.autonomous.craterPosition;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.teamcode.RoverRuckus.autonomous.autonomousFrame;

/**
 * Purpose: Autonomous Program for Crater Position Strategy 2
 */

@Autonomous(name = "Autonomous Crater Strategy 2", group = "Crater")
@Disabled
public class autonomousCrater2 extends autonomousFrame {

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

        telemetry.addData("Status", "Going to Depot"); telemetry.update();

        forward(73,0.5);
        right(140, 0.75);
        forward(45, 0.5);

        //drop team marker
        telemetry.addData("Status", "Drop Team Marker"); telemetry.update();
        sleep(1500);
        telemetry.addData("Status", "Going to Sampling Minerals"); telemetry.update();

        backward(42 , 0.5);
        right(140, 0.75);
        forward(77, 0.5);
        left(70, 0.75);
        forward(20, 0.5);

        //intake down to push random mineral
        telemetry.addData("Status", "Knock Random Mineral"); telemetry.update();
        sleep(1500);
        telemetry.addData("Status", "Going to Crater"); telemetry.update();

        forward(20, 0.5);

        telemetry.addData("Status", "Everything executed"); telemetry.update();

        stop();

    }
}