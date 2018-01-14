package org.firstinspires.ftc.teamcode.autonomous.previousAutonomous.november28VuMarkOLD;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;
import org.firstinspires.ftc.teamcode.autonomous.autonomousFrame;

import static org.firstinspires.ftc.teamcode.key.key;

@Autonomous(name = "Autonomous Top Right OLD (HouseT)")
@Disabled
public class autonomousTopRightOLD extends autonomousFrame {

    @Override
    public void runOpMode() {

        initializeHardwareMap();
        setMotorDirection();
        initializeVuforia();

        boolean detect = false;

        waitForStart();

        armMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        //Motor powers per encoderDrive();
        //forward -+-+
        //backward +-+-
        //left ++-- (+)
        //right--++ (-)

        //To safe zone - facing balancing stone
        encoderDrive(-12, 0, 0, 0.5);

        relicTrackables.activate();
        while (opModeIsActive() && detect == false) {
            RelicRecoveryVuMark vuMark = RelicRecoveryVuMark.from(relicTemplate);
            if (vuMark == RelicRecoveryVuMark.LEFT) {
                telemetry.addData("VuMark Identified:", "Left");
                encoderDrive(-16, 0, 0, 0.5);
                detect = true;
            }
            if (vuMark == RelicRecoveryVuMark.CENTER) {
                telemetry.addData("VuMark Identified:", "Center");
                encoderDrive(-22, 0, 0, 0.5);
                detect = true;
            }
            if (vuMark == RelicRecoveryVuMark.RIGHT) {
                telemetry.addData("VuMark Identified:", "Right");
                encoderDrive(-2, 0, 0, 0.5);
                detect = true;
            }

            telemetry.update();
        }

        encoderDrive(0, 0, -90, 0.5);
        encoderDrive(10, 0, 0, 0.5);

        telemetry.addData("Task", "At safe zone");
        telemetry.update();

        stop();

    }
}