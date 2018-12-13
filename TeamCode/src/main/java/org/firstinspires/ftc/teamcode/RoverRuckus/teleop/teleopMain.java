package org.firstinspires.ftc.teamcode.RoverRuckus.teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

/**
 * <h2>teleopMain</h2>
 * Purpose:
 * <p>
 *
 *
 * temp: This class creates instances of the chassis and intake and runs each of their functions.
 */

@TeleOp(name = "Teleop")
public class teleopMain extends OpMode {

    // VERSION NUMBER(MAJOR.MINOR) - DATE
    // DO BEFORE EVERY COMMIT!
    private final String teleopVersionNumber = "1.4 - 12/12/18 ";

    // Creates (instances of) chassis, and intake
    private chassisHex chassis = new chassisHex(this);
    //private chassisAndyMark chassis = new chassisAndyMark(this);

    private intake intake = new intake(this);

    // Calls methods in chassis and arm
    @Override
    public void init() {
        telemetry.addData("Teleop Program Version", teleopVersionNumber);
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
