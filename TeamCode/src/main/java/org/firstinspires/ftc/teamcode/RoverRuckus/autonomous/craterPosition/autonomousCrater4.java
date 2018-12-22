package org.firstinspires.ftc.teamcode.RoverRuckus.autonomous.craterPosition;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.RoverRuckus.autonomous.autonomousFrame;

/**
 * Purpose: Autonomous Program for Crater Position
 */

@Autonomous(name = "Autonomous Crater 2", group = "Crater")
public class autonomousCrater4 extends autonomousFrame {

    @Override
    public void runOpMode() {

        // GO TO
        // autonomousFrame.java TO
        // UPDATE VERSION NUMBER
        // BEFORE EVERY COMMIT

        versionPrint();
        initializeRobot();

        waitForStart();

        //Move intake down
        moveIntake(intakeDown);
        //Go through middle sampling
        telemetry.addData("Status", "Knocking middle mineral"); telemetry.update();
        forward(23, 0.5);
        //Move intake back up
        moveIntake(intakeUp);

        //Go backward to go underneath the lander
        backward(23, 0.5);
        //The rest of the code is to go to the depot and park in the crater
        left(135, 0.5);
        telemetry.addData("Status", "Going to Depot"); telemetry.update();
        forward(36, 0.5);
        right(9, 0.5);
        moveIntake(intakeDown);
        forward(24, 0.5);

        //Expel team marker into depot
        telemetry.addData("Status", "Dropping Team Marker"); telemetry.update();
        expelMarker();
        moveIntake(intakeUp);

        //move backwards to finish in the opposing alliance's crater
        telemetry.addData("Status", "Parking in Crater"); telemetry.update();
        backward(56, 0.5);

        stop();

    }
}