package org.firstinspires.ftc.teamcode.autonomous.previousAutonomous.autonomousVuMark;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.teamcode.autonomous.autonomousFrame;

///////////////////////////////////////////////////////////////////////////////
// Purpose: Autonomous Program for reading VuMark (Top Right)
// Author: Jonathan Ma, Ansh Gandhi
///////////////////////////////////////////////////////////////////////////////

//Motor powers per encoderDrive();
//forward -+-+
//backward +-+-
//left ++-- (+)
//right--++ (-)

@Autonomous(name = "Autonomous Top Right VuMark (HouseT)")
@Disabled
public class autonomousTopRightVuMark extends autonomousFrame {

    @Override
    public void runOpMode() {

        // GO TO
        // autonomousFrame.JAVA TO
        // UPDATE VERSION NUMBER
        // BEFORE EVERY COMMIT

        telemetry.addData("Autonomous Program Version", autonomousVersionNumber);
        telemetry.update();

        // Initialization
        initializeHardwareMap();
        setMotorDirection();
        initializeVuforia();

        // Defining Variables
        boolean detect = false;
        double distance = 0;
        String displayText = "";
        ElapsedTime runtime = new ElapsedTime();

        // Set glyph claw to hold glyph
        glyphClawLeft.setPosition(0.3);
        glyphClawRight.setPosition(0.7);

        waitForStart();

        runtime.reset();

        // Pulling glyph, facing away from audience - align 1 inch back
        // Changes distance depending on VuMark
        relicTrackables.activate();
        while (opModeIsActive() && detect == false && runtime.milliseconds() <= 5000) {
            RelicRecoveryVuMark vuMark = RelicRecoveryVuMark.from(relicTemplate);
            if (vuMark == RelicRecoveryVuMark.LEFT) {
                displayText = "Left";
                distance = 27;
                detect = true;
            }
            if (vuMark == RelicRecoveryVuMark.CENTER) {
                displayText = "Center";
                distance = 33;
                detect = true;
            }
            if (vuMark == RelicRecoveryVuMark.RIGHT) {
                displayText = "Right";
                distance = 41;
                detect = true;
            }
        }
        if (detect == false){
            displayText = "Unknown";
            distance = 33;
        }
        telemetry.addData("VuMark Identified:", displayText);
        telemetry.update();
        encoderDrive(distance,0,0,0.4);

        sleep(500);
        encoderDrive(0,0,-91,0.3);
        encoderDrive(-10,0,0,0.3);

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
