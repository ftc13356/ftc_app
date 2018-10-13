package org.firstinspires.ftc.teamcode.RoverRuckus.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

///////////////////////////////////////////////////////////////////////////////
// Purpose: Autonomous Program for Bottom Right
// Author: Ansh Gandhi, Jonathan Ma
///////////////////////////////////////////////////////////////////////////////

//Motor powers per encoderDrive();
//forward -+-+
//backward +-+-
//left ++-- (+)
//right--++ (-)

@Autonomous(name = " Autonomous Blue Depot")
public class autonomousBlueDepot extends autonomousFrame {

    @Override
    public void runOpMode() {

        // GO TO
        // autonomousFrame.java TO
        // UPDATE VERSION NUMBER
        // BEFORE EVERY COMMIT

        versionPrint();

        initializeHardwareMap();
        setMotorDirection();

        waitForStart();

        encoderDrive(15,  0, 0.5);
        encoderDrive(0, 90, 0.5);
        encoderDrive(50, 0, 0.5);
        encoderDrive(0, -135, 0.5);
        encoderDrive(65, 0, 0.5);
        //copy of red depot

        telemetry.addData("Status", "Everything executed");
        telemetry.update();

        stop();

    }
}