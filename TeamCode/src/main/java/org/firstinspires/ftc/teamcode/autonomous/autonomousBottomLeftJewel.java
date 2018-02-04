package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

///////////////////////////////////////////////////////////////////////////////
// Purpose: Autonomous Program for knocking jewel and reading VuMark (Bottom Left)
// Author: Jonathan Ma, Ansh Gandhi
///////////////////////////////////////////////////////////////////////////////

//Motor powers per encoderDrive();
//forward -+-+
//backward +-+-
//left ++-- (+)
//right--++ (-)

@Autonomous(name = "Autonomous Bottom Left Jewel (HouseB)")
public class autonomousBottomLeftJewel extends autonomousFrame {

    @Override
    public void runOpMode() {

        // GO TO
        // autonomousFrame.JAVA TO
        // UPDATE VERSION NUMBER
        // BEFORE EVERY COMMIT

        versionPrint();

        // Initialization
        initializeHardwareMap();
        setMotorDirection();
        initializeVuforia();

        // Defining Variables
        allianceColor = 1; // red

        // Set glyph claw to hold glyph
        glyphClawLeft.setPosition(0.4);
        glyphClawRight.setPosition(0.6);

        waitForStart();

        // Pushing glyph, facing away from audience
        // Move arm up
        armMotor.setPower(-0.25);
        sleep(1500);
        armMotor.setPower(0);

        // Detect VuMark
        detectVuMark(-18.75, -11.5, -3.75);

        // Extend color arm
        colorArm.setPower(-1);
        sleep(8000);
        colorArm.setPower(0);

        // Detect Jewel
        reactToJewelDetect(3);

        // Retract color arm
        colorArm.setPower(1);
        sleep(7000);
        colorArm.setPower(0);

        // Drive to appropriate cryptobox column
        encoderDrive(-28 - distanceJewel,0,0,0.3);
        encoderDrive(0, distanceVuMark,0,0.3);
        encoderDrive(-8.25,0,0,0.3);

        // Release glyph
        glyphClawLeft.setPosition(1);
        glyphClawRight.setPosition(0);
        telemetry.addData("Task", "Glyph In");
        telemetry.update();

        // Back up from glyph
        encoderDrive(3, 0, 0, 0.3);
        telemetry.addData("Task", "At safe zone");
        telemetry.update();

        // Move arm back down
        armMotor.setPower(0.15);
        while (touchSensor.getState()) {
            telemetry.addData("Touch Sensor", touchSensor.getState());
            telemetry.update();
        }
        armMotor.setPower(0);

        stop();

    }
}