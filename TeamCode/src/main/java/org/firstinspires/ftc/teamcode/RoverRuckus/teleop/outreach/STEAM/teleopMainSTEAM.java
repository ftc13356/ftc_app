package org.firstinspires.ftc.teamcode.RoverRuckus.teleop.outreach.STEAM;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "MSJE STEAM Event")
public class teleopMainSTEAM extends OpMode {

    private chassisSTEAM chassis = new chassisSTEAM(this);
    private intakeShooterSTEAM intake = new intakeShooterSTEAM(this);

    @Override
    public void init() {
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
