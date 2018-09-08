package org.firstinspires.ftc.teamcode.RelicRecovery.autonomous.previousAutonomous.autonomousSafeZone;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.teamcode.RelicRecovery.autonomous.seasonAutonomousFrame;

///////////////////////////////////////////////////////////////////////////////
// Purpose: Autonomous Program to drive to safe zone (Top Right)
// Author: Jonathan Ma, Ansh Gandhi
///////////////////////////////////////////////////////////////////////////////

//Motor powers per encoderDrive();
//forward -+-+
//backward +-+-
//left ++-- (+)
//right--++ (-)

@Autonomous(name = "Autonomous Top Right Safe Zone (HouseT)")
@Disabled
public class seasonAutonomousTopRightSafeZone extends seasonAutonomousFrame {

    @Override
    public void runOpMode() {

        // Initialization
        initializeHardwareMap();
        setMotorDirection();

        waitForStart();

        //To safe zone - facing balancing stone
        encoderDrive(33, 0, 0, 0.5);
        encoderDrive(0, 12, 0, 0.5);
        telemetry.addData("Task", "At safe zone");
        telemetry.update();

        stop();

    }
}
