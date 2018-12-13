package org.firstinspires.ftc.teamcode.RoverRuckus.autonomous.craterPosition;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.RoverRuckus.autonomous.autonomousFrame;

/**
 * Purpose: Alternate Autonomous Program for Crater Position that
 * only knocks the middle mineral and parks in crater
 * so we don't crash/interfere with alliance partner
 */

@Autonomous(name = "ALT Autonomous Crater", group = "Crater")
public class autonomousCraterALT extends autonomousFrame {

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

        //intake down to push random mineral
        moveIntake(intakeDown);
        telemetry.addData("Status", "Knock Random Mineral"); telemetry.update();

        timedForward(23, 0.5, 5000);
        moveIntake(intakeUp);

        telemetry.addData("Status", "Going to Crater"); telemetry.update();
        forward(6, 0.5);

        stop();

    }
}