package org.firstinspires.ftc.teamcode.RoverRuckus.autonomous.depotPosition;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.RoverRuckus.autonomous.autonomousFrame;

/**
 * Purpose: Autonomous Program for Depot Position
 */

@Autonomous(name = "Autonomous Depot 2", group = "Depot")
public class autonomousDepot3 extends autonomousFrame {

    @Override
    public void runOpMode() {

        // GO TO
        // autonomousFrame.java TO
        // UPDATE VERSION NUMBER
        // BEFORE EVERY COMMIT

        versionPrint();
        initializeRobot();

        waitForStart();
        //Preparation to knock center mineral in sampling and place marker in Team Depot
        telemetry.addData("Status", "Moving down Intake"); telemetry.update();
        moveIntake(intakeDown);

        //Go to hit center sampling and going to depot
        telemetry.addData("Status", "Knocking center mineral, going to Depot"); telemetry.update();
        forward(55,0.5);
        //Expel marker into depot
        telemetry.addData("Status", "Expelling team marker"); telemetry.update();
        expelMarker();

        telemetry.addData("Status", "Moving up Intake"); telemetry.update();
        moveIntake(intakeUp);
        //Position to go into crater
        telemetry.addData("Status", "Positioning for Parking in crater"); telemetry.update();
        backward(55, 0.5);
        right(135,0.5);
        forward(36,0.5);
        left(90,0.5);

        //Going into crater
        telemetry.addData("Status", "Parking"); telemetry.update();
        forward(28,0.5);

        stop();

    }
}