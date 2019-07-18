package org.firstinspires.ftc.teamcode.RoverRuckus.teleop.outreach.ATT;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

/**
 * Program used for the hands-on driving at the AT&T Discovery Day Outreach
 * <p> Controls our special drum robot
 * <p> After 60 seconds, the robot locks and cannot be controlled
 * <p> Time can be extended/reduced by 5 sec by pressing dpad up/down
 */

@TeleOp(name = "AT&T 2018 Outreach Program")
@Disabled
public class teleopMainATT extends OpMode {

    private ChassisATT chassis = new ChassisATT(this);
    private ArmATT arm = new ArmATT(this);

    @Override
    public void init() {
       chassis.init();
       arm.init();
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
}