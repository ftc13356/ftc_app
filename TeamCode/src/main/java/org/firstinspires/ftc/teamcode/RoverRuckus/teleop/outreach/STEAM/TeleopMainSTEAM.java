package org.firstinspires.ftc.teamcode.RoverRuckus.teleop.outreach.STEAM;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "MSJE STEAM Event")
public class TeleopMainSTEAM extends OpMode {

    private ChassisSTEAM chassis = new ChassisSTEAM(this);

    private IntakeSTEAM intake = new IntakeSTEAM(this);

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
