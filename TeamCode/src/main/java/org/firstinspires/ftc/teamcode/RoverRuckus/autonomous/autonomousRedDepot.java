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

@Autonomous(name = "Autonomous Red Depot")
public class autonomousRedDepot extends autonomousFrame {

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
        /*
        15 inches forward
        90 degrees right
        30 inches forward
        135 degrees left
        45 inches forward
        */

        telemetry.addData("Status", "Everything executed");
        telemetry.update();

        stop();

    }
}