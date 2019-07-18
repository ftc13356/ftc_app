package org.firstinspires.ftc.teamcode.RoverRuckus.teleop;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

/**
 * <h2>teleopMain</h2>
 * Purpose:
 * <p> This class creates instances of the chassis and intake and runs each of their functions
 * <p> This makes are code more organized as major parts of the robot are separated
 *
 * @version 2.0 on 2/1/19
 * @author Jonthan Ma and Ansh Gandhi
 */

@TeleOp(name = "Teleop")
public class teleopMain extends OpMode {

    // VERSION NUMBER(MAJOR.MINOR) - DATE
    // DO BEFORE EVERY COMMIT!
    private final String teleopVersionNumber = "2.0 - 2/1/19 ";

    // Creates (instances of) chassis, and intake
    private hexChassisT chassis = new hexChassisT(this);
    //private chassisAndyMark chassis = new chassisAndyMark(this);

    private intakeShooterClimbing intakeShooterClimbing = new intakeShooterClimbing(this);

    /**
     * Calls methods in chassis and arm to-
     * <p> Map chassis, intake, shooter, and winch motors to their names in the robot config file
     * and sets 0 power behavior
     */
    @Override
    public void init() {
        telemetry.addData("Teleop Program Version", teleopVersionNumber);
        telemetry.addData("Robot", "Initializing");

        chassis.init();
        intakeShooterClimbing.init();

        telemetry.addData("Robot", "Initialized");
    }

    /**
     * Runs main teleop code
     */
    @Override
    public void loop() {
        chassis.loop();
        intakeShooterClimbing.loop();
    }
}
