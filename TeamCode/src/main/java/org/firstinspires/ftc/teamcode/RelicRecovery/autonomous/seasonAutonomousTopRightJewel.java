package org.firstinspires.ftc.teamcode.RelicRecovery.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

///////////////////////////////////////////////////////////////////////////////
// Purpose: Autonomous Program for knocking jewel and reading VuMark (Top Right)
// Author: Jonathan Ma, Ansh Gandhi
///////////////////////////////////////////////////////////////////////////////

//Motor powers per encoderDrive();
//forward -+-+
//backward +-+-
//left ++-- (+)
//right--++ (-)

@Autonomous(name = "17-18 Season Autonomous Top Right Jewel (HouseT)")
@Disabled
public class seasonAutonomousTopRightJewel extends seasonAutonomousFrame {

    @Override
    public void runOpMode() {

        // GO TO
        // seasonAutonomousFrame.JAVA TO
        // UPDATE VERSION NUMBER
        // BEFORE EVERY COMMIT

        versionPrint();

        // Initialization
        initializeHardwareMap();
        setMotorDirection();
        initializeVuforia();

        // Defining Variables
        allianceColor = 2; // blue

        // Set glyph claw to hold glyph
        gripGlyphHolonomic();

        waitForStart();

        // Pushing glyph, facing away from audience
        // Move arm up
        moveArm(-0.2,1000);

        // Detect VuMark
        detectVuMark(27, 33, 42);

        // Extend color arm
        extendColorArm();

        // Detect and knock jewel
        reactToJewelDetect(17.5);
        encoderDrive(0,0, distanceJewel,0.5);

        // Retract color arm
        retractColorArm();

        // Drive to appropriate cryptobox column
        encoderDrive(distanceVuMark,0,0,0.5);
        encoderDrive(0,0,-91,0.5);
        encoderDrive(-8,0,0,0.5);

        // Release glyph
        releaseGlyphSwerve();
        telemetry.addData("Task", "Glyph In");
        telemetry.update();

        // Back up from, shove in, back up from glyph
        encoderDrive(5, 0, 0, 0.5);
        encoderDrive(-5,0,0,0.5);
        encoderDrive(4,0,0,0.5);
        telemetry.addData("Task", "At safe zone");
        telemetry.update();

        // Move arm back down
        armMotor.setPower(0.2);
        while (touchSensor.getState()) {
            telemetry.addData("Touch Sensor", touchSensor.getState());
            telemetry.update();
        }
        armMotor.setPower(0);

        stop();

    }
}