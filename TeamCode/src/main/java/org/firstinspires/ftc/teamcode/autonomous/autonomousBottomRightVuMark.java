package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;

///////////////////////////////////////////////////////////////////////////////
// Purpose: Autonomous Program for reading VuMark (Bottom Right)
// Author: Jonathan Ma, Ansh Gandhi
///////////////////////////////////////////////////////////////////////////////

//Motor powers per encoderDrive();
//forward -+-+
//backward +-+-
//left ++-- (+)
//right--++ (-)

@Autonomous(name = "Autonomous Bottom Right VuMark")
public class autonomousBottomRightVuMark extends autonomousFrame {

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

        // Changes distance depending on VuMark
        relicTrackables.activate();
        while (opModeIsActive() && detect == false && runtime.milliseconds() <= 5000) {
            RelicRecoveryVuMark vuMark = RelicRecoveryVuMark.from(relicTemplate);
            if (vuMark == RelicRecoveryVuMark.LEFT) {
                displayText = "Left";
                distance = 3.5;
                detect = true;
            }
            if (vuMark == RelicRecoveryVuMark.CENTER) {
                displayText = "Center";
                distance = 13;
                detect = true;
            }
            if (vuMark == RelicRecoveryVuMark.RIGHT) {
                displayText = "Right";
                distance = 19;
                detect = true;
            }
        }
        if (detect == false){
            displayText = "Unknown";
            distance = 12.5;
        }
        telemetry.addData("VuMark Identified:", displayText);
        telemetry.update();
        encoderDrive(26,0,0,0.4);
        encoderDrive(0,0,181,0.4);
        encoderDrive(0, distance,0,0.4);
        encoderDrive(-11.5,0,0,0.4);

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