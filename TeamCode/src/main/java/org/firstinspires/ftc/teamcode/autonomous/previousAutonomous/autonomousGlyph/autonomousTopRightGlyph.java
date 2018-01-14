package org.firstinspires.ftc.teamcode.autonomous.previousAutonomous.autonomousGlyph;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.teamcode.autonomous.autonomousFrame;

///////////////////////////////////////////////////////////////////////////////
// Purpose: Autonomous Program to score 1 glyph in cryptobox center column (Top Right)
// Author: Jonathan Ma, Ansh Gandhi
///////////////////////////////////////////////////////////////////////////////

//Motor powers per encoderDrive();
//forward -+-+
//backward +-+-
//left ++-- (+)
//right--++ (-)

@Autonomous(name = "Autonomous Top Right Glyph (HouseT)")
@Disabled
public class autonomousTopRightGlyph extends autonomousFrame {

    @Override
    public void runOpMode() {

        // Initialization
        initializeHardwareMap();
        setMotorDirection();

        // Set glyph claw to hold glyph
        glyphClawLeft.setPosition(0.3);
        glyphClawRight.setPosition(0.7);

        waitForStart();

        // To cryptobox - facing balancing stone
        // Move arm up
        armMotor.setPower(-0.25);
        sleep(1500);
        armMotor.setPower(0);

        // Drive to cryptobox
        encoderDrive(35, 0, 0, 0.5);
        encoderDrive(0, 0, -91, 0.4);
        encoderDrive(-9.75, 0, 0, 0.5);

        // Release glyph
        glyphClawLeft.setPosition(1);
        glyphClawRight.setPosition(0);
        telemetry.addData("Task", "Glyph In");
        telemetry.update();

        // Back up from glyph
        encoderDrive(3, 0, 0, 0.3);
        telemetry.addData("Task", "At safe zone");
        telemetry.update();

        stop();

    }
}