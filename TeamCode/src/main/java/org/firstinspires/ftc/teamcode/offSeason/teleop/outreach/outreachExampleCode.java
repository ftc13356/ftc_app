// File Path
package org.firstinspires.ftc.teamcode.offSeason.teleop.outreach;

// Additional Software needed to write program
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

// Mode of Program(Driver controlled), Name
@TeleOp(name = "Code for Fun Outreach Event", group = "Outreach")
public class outreachExampleCode extends OpMode {

    // Create motors in program
    private DcMotor motorLeft;
    private DcMotor motorRight;

    // Tells program where to find motors
    @Override
    public void init() {
        // Prints info to screen
        telemetry.addData("Status", "Initializing");

        // Points to specific place where motor is connected
        motorLeft = hardwareMap.dcMotor.get("motorLeft");
        motorRight = hardwareMap.dcMotor.get("motorRight");

        telemetry.addData("Status", "Initialized");
    }

    // Main program, it continuously checks controller input
    @Override
    public void loop() {
        // Create variable to store power
        double motorLeftPower;
        double motorRightPower;

        // Joystick values stored in speed variables, Left moves Forward/Backwards, Right Turns
        double driveFB = gamepad1.left_stick_y;
        double turn  = gamepad1.right_stick_x;

        // The speed values are calculated and stored in variables
        motorLeftPower = Range.clip((-driveFB - turn), -1.0, 1.0) ;
        motorRightPower = Range.clip((driveFB - turn), -1.0, 1.0) ;

        // Power is set to motor
        motorLeft.setPower(motorLeftPower);
        motorRight.setPower(motorRightPower);
    }

    // Ends program
    @Override
    public void stop() {
    }
}