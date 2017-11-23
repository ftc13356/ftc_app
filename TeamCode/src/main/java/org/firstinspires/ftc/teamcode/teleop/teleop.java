package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "Teleop Drivetrain and Arm")
// @Disabled
public class teleop extends OpMode {

    private chassis chassis = new chassis(this);
    private arm arm = new arm(this);

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