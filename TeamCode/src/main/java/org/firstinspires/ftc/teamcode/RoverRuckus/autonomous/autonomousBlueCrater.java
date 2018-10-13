package org.firstinspires.ftc.teamcode.RoverRuckus.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

///////////////////////////////////////////////////////////////////////////////
// Purpose: Autonomous Program for Bottom Left
// Author: Ansh Gandhi, Jonathan Ma
///////////////////////////////////////////////////////////////////////////////

//Motor powers per encoderDrive();
//forward -+-+
//backward +-+-
//left ++-- (+)
//right--++ (-)

@Autonomous(name = "Autonomous Blue Crater")
public class autonomousBlueCrater extends autonomousFrame {

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
        encoderDrive(0, -90, 0.5);
        encoderDrive(40, 0, 0.5);
        encoderDrive(0, -45, 0.5);
        encoderDrive(55, 0, 0.5);
        //copy of red crater

        telemetry.addData("Status", "Everything executed");
        telemetry.update();

        stop();

    }
}