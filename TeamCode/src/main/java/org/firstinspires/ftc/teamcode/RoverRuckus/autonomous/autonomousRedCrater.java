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

@Autonomous(name = "Autonomous Red Crater")
public class autonomousRedCrater extends autonomousFrame {

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
        /*
        15 inches forward
        90 degrees left
        40 inches forward
        45 degrees left
        55 inches forward
        */

        telemetry.addData("Status", "Everything executed");
        telemetry.update();

        stop();

    }
}