package org.firstinspires.ftc.teamcode.RoverRuckus.autonomous.tests;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.teamcode.RoverRuckus.autonomous.autonomousFrame;


/**
 * Purpose: Vuforia Navigation Test In Autonomous (W/ Webcam)
 */

@Autonomous(name = "FunctionTest")
@Disabled
public class FunctionTest extends autonomousFrame {

    @Override
    public void runOpMode() {

        final int desiredX = 20;
        final int desiredY = 20;
        final int desiredHeading = 180;

        final double x3 = 30;
        final double y3 = 20 + 10 * Math.sqrt(3);
        final double z3 = 80;

        final double x2 = desiredX;
        final double y2 = desiredY;
        final double z2 = desiredHeading;

        final double deltaX = Math.abs(x3 - x2);
        final double deltaY = Math.abs(y3 - y2);
        final double CG = Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2));
        final double theta = Math.toDegrees(Math.atan(deltaX/deltaY));
        final double firstTurn = theta + 90 - z3;
        double Straight = CG;
        final double secondTurn = z2 + 90 - theta + z3;

        /*
        if (y3>y2) {
            Straight = -1 * Straight;
        }

        if (x3>x2) {
            Straight = -1 * Straight;
        }
        */

        telemetry.addData("x3, y3 z3", x3 + ", " + y3  + ", "+ z3);

        telemetry.addData("x2, y2, z2", x2 + ", " + y2 + ", " + z2);

        telemetry.addData("First Turn, Straight, Second Turn", firstTurn + ", " + Straight  + ", " + secondTurn);

        telemetry.update();

        sleep(10000);
    }
}