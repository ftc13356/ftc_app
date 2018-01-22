package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

///////////////////////////////////////////////////////////////////////////////
// Purpose: Main Teleop Program
// Author: Jonathan Ma, Ansh Gandhi
///////////////////////////////////////////////////////////////////////////////

@TeleOp(name = "Teleop Drivetrain and Arm")
public class teleop extends OpMode {

    // VERSION NUMBER(MAJOR.MINOR) - DATE
    // DO BEFORE EVERY COMMIT!
     private final String teleopVersionNumber = "8.13 - 1/21/18 ";

    // Creates instances of chassis, glyph claw, and arm
    private chassis chassis = new chassis(this);
    private glyphC glyphC = new glyphC(this);
    private arm arm = new arm(this);

    // Calls methods in chassis and arm
    @Override
    public void init() {

        telemetry.addData("Teleop Program Version", teleopVersionNumber);

        chassis.init();
        glyphC.init();
        arm.init();
    }

    @Override
    public void init_loop() {
        chassis.init_loop();
        glyphC.init_loop();
        arm.init_loop();
    }

    @Override
    public void start() {
        chassis.start();
        glyphC.start();
        arm.start();
    }

    @Override
    public void loop() {
        chassis.loop();
        glyphC.loop();
        arm.loop();
    }

    @Override
    public void stop() {
    }
}