package org.firstinspires.ftc.teamcode.RoverRuckus.autonomous.tests;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.teamcode.RoverRuckus.autonomous.autonomousFrame;


/**
 * Purpose: Vuforia Navigation Test In Autonomous (W/ Webcam)
 */

@Autonomous(name = "FunctionTest")
//@Disabled
public class FunctionTest extends autonomousFrame {

    @Override
    public void runOpMode() {

        final int desiredX = 60;
        final int desiredY = 90;
        final int desiredHeading = 0;

        final double x1 = 0;
        final double y1 = 0;
        final double z1 = 0;

        final double x2 = desiredX;
        final double y2 = desiredY;
        final double z2 = desiredHeading;

        final double temp0 = x1 - x2;
        final double temp1 = Math.pow(temp0, 2);
        final double temp2 = Math.pow(y1 - y2, 2);
        final double CG = Math.sqrt(temp1 + temp2);
        double theta = Math.toDegrees(Math.asin(temp0/CG));
        final double firstTurn = z1 + 90 - theta;
        double Straight = CG;
        final double secondTurn = z2 + 90 - theta;

        if (y1>y2) {
            Straight = -1 * Straight;
        }

        if (x1>x2) {
            Straight = -1 * Straight;
        }

        telemetry.addData("x1, y1, z1", x1 + ", " + y1  + ", "+ z1);

        telemetry.addData("x2, y2, z2", x2 + ", " + y2 + ", " + z2);

        telemetry.addData("FirstTurn, Straight, SecondTurn", firstTurn + ", " + Straight  + ", " + secondTurn);

        telemetry.update();

        sleep(10000);

    }
}