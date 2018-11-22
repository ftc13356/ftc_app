package org.firstinspires.ftc.teamcode.RoverRuckus.autonomous;

import com.qualcomm.robotcore.hardware.DcMotor;

public class andyMarkChassis extends baseChassis {

    andyMarkChassis() {
        type = e_type.AndyMark;
        counts_per_motor_rev = 1680;
        wheel_diameter_inches = 4.0;
        counts_per_inch = (counts_per_motor_rev * drive_gear_reduction) /
                (wheel_diameter_inches * Math.PI);
        counts_per_degree = counts_per_inch * robot_diameter * Math.PI / 360;
    }

    @Override
    public void initializeMotors() {

        motorRightFront.setDirection(DcMotor.Direction.FORWARD);
        motorLeftBack.setDirection(DcMotor.Direction.FORWARD);
        motorLeftFront.setDirection(DcMotor.Direction.FORWARD);
        motorRightBack.setDirection(DcMotor.Direction.FORWARD);

        super.initializeMotors();
    }

    //@Override
    public void encoderDrive(double driveFB, double turnDegrees, double speed, boolean opModeIsActive) {

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

            // Displays Target and Current Position When Active OpMode and Active Motor(s)
            while (opModeIsActive && (motorLeftFront.isBusy() || motorRightFront.isBusy() ||
                    motorLeftBack.isBusy() || motorRightBack.isBusy())) {
                // telemetry for debug only
                /*// Displays Target and Current Positions
                telemetry.addData("Target Value", "Running to %7d :%7d :%7d :%7d",
                        newLeftFrontTarget, newRightFrontTarget, newLeftBackTarget, newRightBackTarget);
                telemetry.addData("Current Value", "Running at %7d :%7d: %7d :%7d",
                        motorLeftFront.getCurrentPosition(),
                        motorRightFront.getCurrentPosition(),
                        motorLeftBack.getCurrentPosition(),
                        motorRightBack.getCurrentPosition());
                telemetry.update();*/
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
