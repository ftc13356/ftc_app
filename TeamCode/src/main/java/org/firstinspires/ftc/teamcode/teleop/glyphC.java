package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

///////////////////////////////////////////////////////////////////////////////
// Purpose: Class for controlling the the glyph claw
// Author: Jonathan Ma, Ansh Gandhi
///////////////////////////////////////////////////////////////////////////////

@TeleOp(name = "Glyph Claw")
@Disabled
public class glyphC {

    // Initialize the variables
    private Servo glyphClawLeft;
    private Servo glyphClawRight;

    private double glyphLeftPosition = 1;
    private double glyphRightPosition = 0;

    // Creates OpMode
    private OpMode op;
    glyphC(OpMode opmode) {
        op = opmode;
    }

    public void init() {

        op.telemetry.addData("Arm", "Initializing");

        // Initializing the hardware variables
        glyphClawLeft = op.hardwareMap.servo.get("glyphClawLeft");
        glyphClawRight = op.hardwareMap.servo.get("glyphClawRight");

        op.telemetry.addData("Arm", "Initialized");
    }

    // This code is just waiting for the Play button to be pressed
    public void init_loop() {
    }

    // This code will do something once when the Play button is pressed
    public void start() {
    }

    // This code will run constantly after the previous part is ran
    public void loop() {

        // This closes the glyph claw when the "X" button is pressed
        if (op.gamepad2.x) {
            glyphLeftPosition = 0.2;
            glyphRightPosition = 0.8;
            op.telemetry.addData("Glyph Servo Status", "Closed");
        }
        // This closes the glyph claw when the "B" button is pressed
        else if (op.gamepad2.b) {
            glyphLeftPosition = 1;
            glyphRightPosition = 0;
            op.telemetry.addData("Glyph Servo Status", "Open Completely");
        }
        // This closes the glyph claw partially when the "Y" button is pressed
        else if (op.gamepad2.y) {
            glyphLeftPosition = 0.4;
            glyphRightPosition = 0.6;
            op.telemetry.addData("Glyph Servo Status", "Open Slightly");
        }

        // Sets Left Position and Right Position
        // Reads Left Position and Reads Right Position
        glyphClawLeft.setPosition(glyphLeftPosition);
        glyphClawRight.setPosition(glyphRightPosition);
        glyphLeftPosition = glyphClawLeft.getPosition();
        glyphRightPosition = glyphClawRight.getPosition();

        //This prints servo positions on the screen
        op.telemetry.addData("Left Glyph Servo Position", glyphLeftPosition);
        op.telemetry.addData("Right Glyph Servo Position", glyphRightPosition);
    }
}
