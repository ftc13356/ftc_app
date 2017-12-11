package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

@Autonomous(name = "Autonomous Top Left")
public class autonomousTopLeft extends autonomousFrame {

    @Override
    public void runOpMode() {

        initializeHardwareMap();
        setMotorDirection();

        glyphClawLeft.setPosition(0.3);
        glyphClawRight.setPosition(0.7);

        waitForStart();

        //Motor powers per encoderDrive();
        //forward -+-+
        //backward +-+-
        //left ++-- (+)
        //right--++ (-)

        //To safe zone - facing balancing stone
        encoderDrive(33.5, 0, 0, 0.5);
        encoderDrive(0, 0, 91, 0.4);
        encoderDrive(-9.75, 0, 0, 0.5);
        glyphClawLeft.setPosition(1);
        glyphClawRight.setPosition(0);
        telemetry.addData("Task", "Glyph In");
        telemetry.update();
        encoderDrive(3, 0, 0, 0.3);
        telemetry.addData("Task", "At safe zone");
        telemetry.update();

        stop();

    }
}
