package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

import static org.firstinspires.ftc.teamcode.key.key;

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

        // Initialization
        initializeHardwareMap();
        setMotorDirection();
        ElapsedTime runtime = new ElapsedTime();

        // Vuforia Initialization
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);
        parameters.vuforiaLicenseKey = key;
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.FRONT;
        this.vuforia = ClassFactory.createVuforiaLocalizer(parameters);
        VuforiaTrackables relicTrackables = this.vuforia.loadTrackablesFromAsset("RelicVuMark");
        VuforiaTrackable relicTemplate = relicTrackables.get(0);
        relicTemplate.setName("relicVuMarkTemplate");
        telemetry.addData("Vuforia Status", "Initialized");
        telemetry.update();

        // Defining Variables
        boolean detect = false;

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
        encoderDrive(-26,0,0,0.5);

        // Changes distance depending on VuMark
        relicTrackables.activate();
        while (opModeIsActive() && detect == false && getRuntime()<=5) {
            RelicRecoveryVuMark vuMark = RelicRecoveryVuMark.from(relicTemplate);
            if (vuMark == RelicRecoveryVuMark.LEFT) {
                telemetry.addData("VuMark Identified:", "Left");
                encoderDrive(0,18.75,0,0.4);
                detect = true;
            }
            if (vuMark == RelicRecoveryVuMark.CENTER) {
                telemetry.addData("VuMark Identified:", "Center");
                encoderDrive(0,12.75,0,0.4);
                detect = true;
            }
            if (vuMark == RelicRecoveryVuMark.RIGHT) {
                telemetry.addData("VuMark Identified:", "Right");
                encoderDrive(0,6.75,0,0.4);
                detect = true;
            }

            telemetry.update();
        }

        if (detect == false) {
            telemetry.addData("VuMark Identified:", "Unknown");
            encoderDrive(35,0,0,0.4);
        }

        encoderDrive(-8,0,0,0.5);

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