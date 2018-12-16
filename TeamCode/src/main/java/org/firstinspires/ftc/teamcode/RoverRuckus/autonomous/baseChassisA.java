package org.firstinspires.ftc.teamcode.RoverRuckus.autonomous;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * <h2>Base Chassis</h2>
 * Purpose:
 * <p> Contains all the common variables and functions involving the chassis
 * <p> so that we can easily create organized chassis programs
 */

public abstract class baseChassisA {

    // Initialize Motors
    DcMotor motorLeftFront;
    DcMotor motorRightFront;
    DcMotor motorLeftBack;
    DcMotor motorRightBack;

    // Initialize Encoder Variables
    final double robot_diameter = 14.0;
    final double drive_gear_reduction = 1.0;

    final double wheel_diameter_default = 4.0;
    final double wheel_diameter_high_grip = 5.0;

    // these encoder variables vary depending on chassis type
    double counts_per_motor_rev = 0;
    double counts_per_inch_default = 0;
    double counts_per_inch_high_grip = 0;
    double counts_per_degree_default = 0;
    double counts_per_degree_high_grip = 0;

    public enum e_type {
        AndyMark,
        CoreHex
    }

    public e_type type;

    /**
     * Maps chassis motors to their names in the robot config file
     * @param hardwareMap hardware map of robot in autonomousFrame (program extending LinerOpMode)
     */
    public void initializeHardwareMap(HardwareMap hardwareMap) {
        motorLeftFront = hardwareMap.dcMotor.get("motorLeftFront");
        motorRightFront = hardwareMap.dcMotor.get("motorRightFront");
        motorLeftBack = hardwareMap.dcMotor.get("motorLeftBack");
        motorRightBack = hardwareMap.dcMotor.get("motorRightBack");
    }

    /**
     * Tells motors to not apply brakes when power is 0
     */
    public void initializeMotors() {
        motorLeftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motorLeftBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motorRightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motorRightBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    /**
     * Allows each individual motor to be programmed to go forward (+), backward (-) a certain amount of inches
     * @param leftFrontInches Inches to move motorLeftFront
     * @param rightFrontInches Inches to move motorRightFront
     * @param leftBackInches Inches to move motorLeftBack
     * @param rightBackInches Inches to move motorRightBack
     * @param speed Speed of robot (min: 0, max: 1)
     * @param opModeIsActive Use opModeIsActive() boolean in autonomousFrame (program extending LinerOpMode)
     */
    public void encoderDriveBasic(double leftFrontInches, double rightFrontInches,
                                  double leftBackInches, double rightBackInches, double speed, boolean opModeIsActive) {

        counts_per_motor_rev = 1680;
        counts_per_inch_default = (counts_per_motor_rev * drive_gear_reduction) /
                (wheel_diameter_default * Math.PI);

        // Defines Target Position Variables
        int newLeftFrontTarget;
        int newRightFrontTarget;
        int newLeftBackTarget;
        int newRightBackTarget;

        // Ensure that the OpMode is still active
        if (opModeIsActive) {

            // Calculates Target Position by Adding Current Position and Distance To Target Position
            newLeftFrontTarget = motorLeftFront.getCurrentPosition() +
                    (int)(leftFrontInches * counts_per_inch_default);
            newRightFrontTarget = motorRightFront.getCurrentPosition() +
                    (int)(rightFrontInches * counts_per_inch_default);
            newLeftBackTarget = motorLeftBack.getCurrentPosition() +
                    (int)(leftBackInches * counts_per_inch_default);
            newRightBackTarget = motorRightBack.getCurrentPosition() +
                    (int)(rightBackInches * counts_per_inch_default);

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

            // Displays Target and Current Position When Active OpMode and Active Motor(s)
            while (opModeIsActive && (motorLeftFront.isBusy() || motorRightFront.isBusy() ||
                    motorLeftBack.isBusy() || motorRightBack.isBusy())) {
                // telemetry for debug only
                // Displays Target and Current Positions
                //telemetry.addData("Target Value",  "Running to %7d :%7d :%7d :%7d",
                  //      newLeftFrontTarget,  newRightFrontTarget, newLeftBackTarget, newRightBackTarget);
                //telemetry.addData("Current Value",  "Running at %7d :%7d: %7d :%7d",
                  //      motorLeftFront.getCurrentPosition(),
                    //    motorRightFront.getCurrentPosition(),
                      //  motorLeftBack.getCurrentPosition(),
                        //motorRightBack.getCurrentPosition());
             //   telemetry.update();
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
