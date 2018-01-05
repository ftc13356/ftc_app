package org.firstinspires.ftc.teamcode.teleop.outreach;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

///////////////////////////////////////////////////////////////////////////////
// Purpose: Main Teleop Program
// Author: Jonathan Ma, Ansh Gandhi
///////////////////////////////////////////////////////////////////////////////

@TeleOp(name = "Teleop for Outreach")
public class teleopChassisOnly extends OpMode {

    // Creates instances of chassis
    private chassis chassis = new chassis(this);

    // Calls methods in chassis and arm
    @Override
    public void init() {
        chassis.init();
    }

    @Override
    public void init_loop() {
        chassis.init_loop();
    }

    @Override
    public void start() {
        chassis.start();
    }

    @Override
    public void loop() {
        chassis.loop();
    }

    @Override
    public void stop() {
    }
}