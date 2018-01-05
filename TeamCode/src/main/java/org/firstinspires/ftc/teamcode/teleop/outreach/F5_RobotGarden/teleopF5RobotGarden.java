package org.firstinspires.ftc.teamcode.teleop.outreach.F5_RobotGarden;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

///////////////////////////////////////////////////////////////////////////////
// Purpose: Main Teleop Program
// Author: Jonathan Ma, Ansh Gandhi
///////////////////////////////////////////////////////////////////////////////

@TeleOp(name = "Drivetrain F5/Robot Garden Edition")
// @Disabled
public class teleopF5RobotGarden extends OpMode {

    // Creates instances of chassis
    private chassisF5RobotGarden chassisF5RobotGarden = new chassisF5RobotGarden(this);

    // Calls methods in chassis and arm
    @Override
    public void init() {
        chassisF5RobotGarden.init();
    }

    @Override
    public void init_loop() {
        chassisF5RobotGarden.init_loop();
    }

    @Override
    public void start() {
        chassisF5RobotGarden.start();
    }

    @Override
    public void loop() {
        chassisF5RobotGarden.loop();
    }

    @Override
    public void stop() {
    }
}