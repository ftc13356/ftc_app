package org.firstinspires.ftc.teamcode.RelicRecovery.autonomous.previousAutonomous.autonomousVuMark;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.teamcode.RelicRecovery.autonomous.seasonAutonomousFrame;

///////////////////////////////////////////////////////////////////////////////
// Purpose: Autonomous Program for reading VuMark (Bottom Left)
// Author: Jonathan Ma, Ansh Gandhi
///////////////////////////////////////////////////////////////////////////////

//Motor powers per encoderDrive();
//forward -+-+
//backward +-+-
//left ++-- (+)
//right--++ (-)

@Autonomous(name = "Autonomous Bottom Left VuMark (HouseB)")
@Disabled
public class seasonAutonomousBottomLeftVuMark extends seasonAutonomousFrame {

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
        boolean detect = false;
        double distance = 0;
        String displayText = "";
        ElapsedTime runtime = new ElapsedTime();

        // Set glyph claw to hold glyph
        glyphClawSwerveLeft.setPosition(0.3);
        glyphClawSwerveRight.setPosition(0.7);

        waitForStart();

        runtime.reset();

        // Pushing glyph, facing away from audience
        // Move arm up
        armMotor.setPower(-0.25);
        sleep(1500);
        armMotor.setPower(0);

        // Changes distance depending on VuMark
        relicTrackables.activate();
        while (opModeIsActive() && detect == false && runtime.milliseconds() <= 5000) {
            RelicRecoveryVuMark vuMark = RelicRecoveryVuMark.from(relicTemplate);
            if (vuMark == RelicRecoveryVuMark.LEFT) {
                displayText = "Left";
                distance = -19.75;
                detect = true;
            }
            if (vuMark == RelicRecoveryVuMark.CENTER) {
                displayText = "Left";
                distance = -12.5;
                detect = true;
            }
            if (vuMark == RelicRecoveryVuMark.RIGHT) {
                displayText = "Left";
                distance = -4.75;
                detect = true;
            }
        }
        if (detect == false){
            displayText = "Unknown";
            distance = -11.5;
        }
        telemetry.addData("VuMark Identified:", displayText);
        telemetry.update();
        encoderDrive(-28,0,0,0.4);
        encoderDrive(0,distance,0,0.3);
        encoderDrive(-5.25,0,0,0.4);

        // Release glyph
        glyphClawSwerveLeft.setPosition(1);
        glyphClawSwerveRight.setPosition(0);
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