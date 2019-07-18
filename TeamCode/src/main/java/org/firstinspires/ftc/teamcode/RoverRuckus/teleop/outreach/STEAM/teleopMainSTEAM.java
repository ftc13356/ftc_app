package org.firstinspires.ftc.teamcode.RoverRuckus.teleop.outreach.STEAM;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

/**
 * Program used for the hands-on driving booth at the MSJE STEAM Night Outreach
 * <p> Only chassis, intake, and shooter enabled
 * <p> After 60 seconds, the robot locks and cannot be controlled
 * <p> Time can be extended/reduced by 5 sec by pressing dpad up/down
 */

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
    public void start() {
        chassis.start();
        intake.start();
    }

    @Override
    public void loop() {
        chassis.loop();
        intake.loop();
    }
}
