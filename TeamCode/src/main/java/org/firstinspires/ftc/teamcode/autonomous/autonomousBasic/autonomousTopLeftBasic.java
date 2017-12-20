package org.firstinspires.ftc.teamcode.autonomous.autonomousBasic;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.teamcode.autonomous.autonomousFrame;

@Autonomous(name = "Autonomous Top Left Basic")
@Disabled
public class autonomousTopLeftBasic extends autonomousFrame {

    @Override
    public void runOpMode() {

        initializeHardwareMap();
        setMotorDirection();

        waitForStart();

        //Motor powers per encoderDrive();
        //forward -+-+
        //backward +-+-
        //left ++-- (+)
        //right--++ (-)

        //To safe zone
        encoderDrive(32, 0, 0, 0.5);
        encoderDrive(0, -12, 0, 0.5);
        telemetry.addData("Task", "At safe zone");
        telemetry.update();

        stop();

    }
}
