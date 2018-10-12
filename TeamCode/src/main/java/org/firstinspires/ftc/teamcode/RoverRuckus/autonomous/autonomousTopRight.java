package org.firstinspires.ftc.teamcode.RoverRuckus.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

///////////////////////////////////////////////////////////////////////////////
// Purpose: Autonomous Program for Top Right
// Author: Ansh Gandhi, Jonathan Ma
///////////////////////////////////////////////////////////////////////////////

//Motor powers per encoderDrive();
//forward -+-+
//backward +-+-
//left ++-- (+)
//right--++ (-)

@Autonomous(name = "Autonomous Top Right")
public class autonomousTopRight extends autonomousFrame {

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