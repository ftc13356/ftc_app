package org.firstinspires.ftc.teamcode.autonomous.december10;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.teamcode.autonomous.autonomousFrame;

@Autonomous(name = "Autonomous Bottom Right Basic")
@Disabled
public class autonomousBottomRightBasic extends autonomousFrame {

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
        encoderDrive(-34, 0, 0, 0.5);
        encoderDrive(0, 11, 0, 0.5);
        telemetry.addData("Task", "At safe zone");
        telemetry.update();

        stop();

    }
}
