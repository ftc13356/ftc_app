package org.firstinspires.ftc.teamcode.RoverRuckus.autonomous;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * <h2>AndyMark Motor Chassis</h2>
 * Purpose:
 * <p> Contains the variables and functions specific to an all andymark motor chassis
 * <p> so that we can switch from an all hex motor chassis without changing any code!
 */

public class andyMarkChassis extends baseChassis {

    andyMarkChassis() {
        type = e_type.AndyMark;

        // Set hex motor chassis encoder variables
        counts_per_motor_rev = 1680;
        wheel_diameter_inches = 4.0;
        counts_per_inch = (counts_per_motor_rev * drive_gear_reduction) /
                (wheel_diameter_inches * Math.PI);
        counts_per_degree = counts_per_inch * robot_diameter * Math.PI / 360;
    }

    /**
     * Sets andymark motor chassis motor direction
     */
    @Override
    public void initializeMotors() {

        motorLeftFront.setDirection(DcMotor.Direction.FORWARD);
        motorRightFront.setDirection(DcMotor.Direction.FORWARD);
        motorLeftBack.setDirection(DcMotor.Direction.FORWARD);
        motorRightBack.setDirection(DcMotor.Direction.FORWARD);

        super.initializeMotors();
    }

    /**
     * AndyMark Motor Chassis Specific encoderDrive()
     * @param driveFB Inches to move forward or backward (forward: +, backward: -)
     * @param turnDegrees Degrees to turn left or right (right: +, left: -)
     * @param speed Speed of robot (min: 0, max: 1)
     * @param turning Whether robot is currently turning
     * @param opModeIsActive Type "opModeIsActive()" boolean in autonomousFrame (program extending LinerOpMode)
     * @param frame Type "this" to pass in autonomousFrame (when calling in autonomousFrame)
     *              so this function can access it
     */
    public void encoderDrive(double driveFB, double turnDegrees, double speed, boolean turning,
                             boolean opModeIsActive, autonomousFrame frame) {

        // Turning Timeout Timer
        ElapsedTime turnTime = new ElapsedTime();

        // Defines Target Position Variables
        int newLeftFrontTarget;
        int newRightFrontTarget;
        int newLeftBackTarget;
        int newRightBackTarget;

        double motorLeftFrontEncoder;
        double motorRightFrontEncoder;
        double motorLeftBackEncoder;
        double motorRightBackEncoder;

        turnDegrees = turnDegrees * counts_per_degree / counts_per_inch;

        motorLeftFrontEncoder = (-driveFB - turnDegrees) * counts_per_inch;
        motorRightFrontEncoder = (driveFB - turnDegrees) * counts_per_inch;
        motorLeftBackEncoder = (-driveFB - turnDegrees) * counts_per_inch;
        motorRightBackEncoder = (driveFB - turnDegrees) * counts_per_inch;

        if (opModeIsActive) {

            // Calculates Target Position by Adding Current Position and Distance To Target Position
            newLeftFrontTarget = motorLeftFront.getCurrentPosition() + (int) (motorLeftFrontEncoder);
            newRightFrontTarget = motorRightFront.getCurrentPosition() + (int) (motorRightFrontEncoder);
            newLeftBackTarget = motorLeftBack.getCurrentPosition() + (int) (motorLeftBackEncoder);
            newRightBackTarget = motorRightBack.getCurrentPosition() + (int) (motorRightBackEncoder);

            // Sets Target Position for Motors
            motorLeftFront.setTargetPosition(newLeftFrontTarget);
            motorRightFront.setTargetPosition(newRightFrontTarget);
            motorLeftBack.setTargetPosition(newLeftBackTarget);
            motorRightBack.setTargetPosition(newRightBackTarget);

            // Changes Motor Mode So They Can Move to Target Position
            motorLeftFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            motorRightFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            motorLeftBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            motorRightBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            // Sets Power to Motors
            motorLeftFront.setPower(Math.abs(speed));
            motorRightFront.setPower(Math.abs(speed));
            motorLeftBack.setPower(Math.abs(speed));
            motorRightBack.setPower(Math.abs(speed));

            turnTime.reset();

            // Waits until all motors have reached the target position
            while (opModeIsActive && (motorLeftFront.isBusy() || motorRightFront.isBusy() ||
                    motorLeftBack.isBusy() || motorRightBack.isBusy())) {

                frame.telemetry.addData("Turning", turning);
                frame.telemetry.addData("Turn Timer", turnTime.milliseconds());
                frame.telemetry.update();

                // Stop telling robot to turn if it has been turning for 3 seconds
                if (turning && turnTime.milliseconds() >= 3000) {
                    frame.telemetry.addData("Motor Status", "Timeout");
                    frame.telemetry.update();
                    break;
                }

                // telemetry for debug only
                /*// Displays Target and Current Positions
                frame.telemetry.addData("Target Value", "Running to %7d :%7d :%7d :%7d",
                        newLeftFrontTarget, newRightFrontTarget, newLeftBackTarget, newRightBackTarget);
                frame.telemetry.addData("Current Value", "Running at %7d :%7d: %7d :%7d",
                        motorLeftFront.getCurrentPosition(),
                        motorRightFront.getCurrentPosition(),
                        motorLeftBack.getCurrentPosition(),
                        motorRightBack.getCurrentPosition());
                frame.telemetry.update();*/
            }

            // Stops Motors
            motorLeftFront.setPower(0);
            motorRightFront.setPower(0);
            motorLeftBack.setPower(0);
            motorRightBack.setPower(0);

            // Changes Motor Mode
            motorLeftFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            motorRightFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            motorLeftBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            motorRightBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
    }
}
