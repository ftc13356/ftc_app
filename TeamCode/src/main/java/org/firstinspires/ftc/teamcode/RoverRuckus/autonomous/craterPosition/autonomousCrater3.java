package org.firstinspires.ftc.teamcode.RoverRuckus.autonomous.craterPosition;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.RoverRuckus.autonomous.autonomousFrame;

///////////////////////////////////////////////////////////////////////////////
// Purpose: Autonomous Program for Crater Position Strategy 3
// Contributors: Ansh Gandhi, Jonathan Ma
///////////////////////////////////////////////////////////////////////////////

@Autonomous(name = "Autonomous Crater Strategy 3", group = "Crater")
public class autonomousCrater3 extends autonomousFrame {

    @Override
    public void runOpMode() {

        // GO TO
        // autonomousFrame.java TO
        // UPDATE VERSION NUMBER
        // BEFORE EVERY COMMIT

        versionPrint();

        initializeHardwareMap();
        setMotorDirection();

        waitForStart();

        forward(15, 0.5);
        left(90, 0.5);
        //do sampling
        backward(16, 0.5);
        forward(49, 0.5);//in place of sampling
        left(45, 0.5);
        forward(62, 0.5);
        backward(90, 0.5);

        telemetry.addData("Status", "Everything executed");
        telemetry.update();

        stop();

    }
}