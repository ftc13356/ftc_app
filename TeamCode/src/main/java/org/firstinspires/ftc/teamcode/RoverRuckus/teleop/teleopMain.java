package org.firstinspires.ftc.teamcode.RoverRuckus.teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "Teleop")
public class teleopMain extends OpMode {

    private final String teleopVersionNumber = "1.1 - 12/3/18 ";

    private chassisHex chassis = new chassisHex(this);
    //private chassisAndyMark chassis = new chassisAndyMark(this);

    private intake intake = new intake(this);

    @Override
    public void init() {
        telemetry.addData("Teleop Program Version", teleopVersionNumber);
        telemetry.addData("Robot", "Initializing");

        chassis.init();
        intake.init();

        telemetry.addData("Robot", "Initialized");
    }

    @Override
    public void loop() {
        chassis.loop();
        intake.loop();
    }
}
