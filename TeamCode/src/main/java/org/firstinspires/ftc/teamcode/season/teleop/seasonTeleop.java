package org.firstinspires.ftc.teamcode.season.teleop;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

///////////////////////////////////////////////////////////////////////////////
// Purpose: Main Teleop Program
// Author: Jonathan Ma, Ansh Gandhi
///////////////////////////////////////////////////////////////////////////////

@TeleOp(name = "16-17 Season Teleop Drivetrain and Arm")
@Disabled
public class seasonTeleop extends OpMode {

    // VERSION NUMBER(MAJOR.MINOR) - DATE
    // DO BEFORE EVERY COMMIT!
    private final String teleopVersionNumber = "9.1 - 2/9/18 ";

    // Creates instances of chassis, glyph claw, and arm
    private seasonChassis seasonChassis = new seasonChassis(this);
    private seasonArm seasonArm = new seasonArm(this);

    // Calls methods in chassis and arm
    @Override
    public void init() {
        telemetry.addData("Teleop Program Version", teleopVersionNumber);
        seasonChassis.init();
        seasonArm.init();
    }

    @Override
    public void init_loop() {
        seasonChassis.init_loop();
        seasonArm.init_loop();
    }

    @Override
    public void start() {
        seasonChassis.start();
        seasonArm.start();
    }

    @Override
    public void loop() {
        seasonChassis.loop();
        seasonArm.loop();
    }

    @Override
    public void stop() {
    }
}