package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

///////////////////////////////////////////////////////////////////////////////
// Purpose: Main Teleop Program
// Author: Jonathan Ma
///////////////////////////////////////////////////////////////////////////////

@TeleOp(name = "Teleop Drivetrain and Arm")
// @Disabled
public class teleop extends OpMode {

    // Creates instances of chassis and arm
    private chassis chassis = new chassis(this);
    private arm arm = new arm(this);

    // Calls methods in chassis and arm
    @Override
    public void init() {
        chassis.init();
        arm.init();
    }

    @Override
    public void init_loop() {
        chassis.init_loop();
        arm.init_loop();
    }

    @Override
    public void start() {
        chassis.start();
        arm.start();
    }

    @Override
    public void loop() {
        chassis.loop();
        arm.loop();
    }

    @Override
    public void stop() {
    }
}