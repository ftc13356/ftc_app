package org.firstinspires.ftc.teamcode.RoverRuckus.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.teamcode.RoverRuckus.autonomous.autonomousFrame;

///////////////////////////////////////////////////////////////////////////////
// Purpose: Autonomous Program for Top Left
// Author: Ansh Gandhi, Jonathan Ma
///////////////////////////////////////////////////////////////////////////////

//Motor powers per encoderDrive();
//forward -+-+
//backward +-+-
//left ++-- (+)
//right--++ (-)

@Autonomous(name = "Autonomous Top Left")
public class autonomousTopLeft extends autonomousFrame {

    @Override
    public void runOpMode() {

        // GO TO
        // autonomousFrame.java TO
        // UPDATE VERSION NUMBER
        // BEFORE EVERY COMMIT

        versionPrint();

        initializeHardwareMap();
        setMotorDirection();
        vuforiaNavigation(0,0,0);

        waitForStart();

        encoderDrive(5, 0, 0, 0.5);
        encoderDrive(-4,0,0,0.5);

        telemetry.addData("Status", "Everything executed");
        telemetry.update();

        stop();

    }
}