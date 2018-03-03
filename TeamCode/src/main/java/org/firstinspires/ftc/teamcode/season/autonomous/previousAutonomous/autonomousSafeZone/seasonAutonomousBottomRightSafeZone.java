package org.firstinspires.ftc.teamcode.season.autonomous.previousAutonomous.autonomousSafeZone;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.teamcode.season.autonomous.seasonAutonomousFrame;

///////////////////////////////////////////////////////////////////////////////
// Purpose: Autonomous Program to drive to safe zone (Bottom Right)
// Author: Jonathan Ma, Ansh Gandhi
///////////////////////////////////////////////////////////////////////////////

//Motor powers per encoderDrive();
//forward -+-+
//backward +-+-
//left ++-- (+)
//right--++ (-)

@Autonomous(name = "Autonomous Bottom Right Safe Zone")
@Disabled
public class seasonAutonomousBottomRightSafeZone extends seasonAutonomousFrame {

    @Override
    public void runOpMode() {

        // Initialization
        initializeHardwareMap();
        setMotorDirection();

        waitForStart();

        //To safe zone - facing balancing stone
        encoderDrive(-34, 0, 0, 0.5);
        encoderDrive(0, 11, 0, 0.5);
        telemetry.addData("Task", "At safe zone");
        telemetry.update();

        stop();

    }
}
