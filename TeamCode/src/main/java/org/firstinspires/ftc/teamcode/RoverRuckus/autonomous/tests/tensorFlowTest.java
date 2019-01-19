package org.firstinspires.ftc.teamcode.RoverRuckus.autonomous.tests;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.robotcore.external.hardware.camera.CameraName;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.RoverRuckus.autonomous.autonomousFrame;
import org.firstinspires.ftc.teamcode.RoverRuckus.autonomous.tensorFlow;

/**
 * Purpose: Test program for tensor flow
 */

@Autonomous(name="Tensor Flow Test")
public class tensorFlowTest extends autonomousFrame {

    @Override
    public void runOpMode() {
        //initializeRobot();
        tensorFlow tensorFlowSampling = new tensorFlow(this);

        tensorFlowSampling.initialize();

        waitForStart();

        int goldLocation = tensorFlowSampling.scan();
        telemetry.addData("Location", goldLocation);
        telemetry.update();

        /*
        if (goldLocation == 1) {
            left(45, 0.5);
        }
        if (goldLocation == 2) {
            forward(2.5, 0.5);
        }
        if (goldLocation == 3) {
            right(45, 0.5);
        }*/
    }
}