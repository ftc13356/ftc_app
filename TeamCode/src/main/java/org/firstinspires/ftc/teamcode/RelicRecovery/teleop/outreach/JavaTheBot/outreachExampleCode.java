// File Path
package org.firstinspires.ftc.teamcode.RelicRecovery.teleop.outreach.JavaTheBot;

// Additional Software needed to write program
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

// Mode of Program(Driver controlled), Name
@TeleOp(name = "Code for Fun Outreach Event")
@Disabled
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

        // Prints info to screen
        telemetry.addData("Status", "Initialized");
    }

    // Main program, it continuously checks controller input
    @Override
    public void loop() {
        // Power is set to motor
        motorLeft.setPower(gamepad1.left_stick_y);
        motorRight.setPower(-gamepad1.right_stick_y);
    }

    // Ends program
    @Override
    public void stop() {
    }
}