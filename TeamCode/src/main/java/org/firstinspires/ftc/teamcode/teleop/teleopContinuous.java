package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

///////////////////////////////////////////////////////////////////////////////
// Purpose: Main Teleop Program
// Author: Jonathan Ma, Ansh Gandhi
///////////////////////////////////////////////////////////////////////////////

@TeleOp(name = "Teleop Drivetrain and Arm (Continuous Servo)")
public class teleopContinuous extends OpMode {

    // Creates instances of chassis, glyph claw, and arm
    private chassis chassis = new chassis(this);
    private continuousGlyphC continuousGlyphC = new continuousGlyphC(this);
    private arm arm = new arm(this);

    // Calls methods in chassis and arm
    @Override
    public void init() {
        chassis.init();
        continuousGlyphC.init();
        arm.init();
    }

    @Override
    public void init_loop() {
        chassis.init_loop();
        continuousGlyphC.init_loop();
        arm.init_loop();
    }

    @Override
    public void start() {
        chassis.start();
        continuousGlyphC.start();
        arm.start();
    }

    @Override
    public void loop() {
        chassis.loop();
        continuousGlyphC.loop();
        arm.loop();
    }

    @Override
    public void stop() {
    }
}