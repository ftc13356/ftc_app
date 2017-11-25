package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;

@Autonomous(name = "Autonomous Top Left")
public class autonomousTopLeft extends autonomousFrame {

    @Override
    public void runOpMode() {

        motorLeftfront = hardwareMap.get(DcMotor.class, "motorLeftfront");
        motorRightfront = hardwareMap.get(DcMotor.class, "motorRightfront");
        motorLeftback = hardwareMap.get(DcMotor.class, "motorLeftback");
        motorRightback = hardwareMap.get(DcMotor.class, "motorRightback");
        //armMotor = hardwareMap.get(DcMotor.class, "armMotor");
        //clawLeft = hardwareMap.servo.get("clawLeft");
        //clawRight = hardwareMap.servo.get("clawRight");

        motorLeftfront.setDirection(DcMotor.Direction.FORWARD);
        motorRightfront.setDirection(DcMotor.Direction.FORWARD);
        motorLeftback.setDirection(DcMotor.Direction.FORWARD);
        motorRightback.setDirection(DcMotor.Direction.FORWARD);
        //armMotor.setDirection(DcMotor.Direction.REVERSE);

        waitForStart();

        //armMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        //To safe zone
        encoderDrive(32, 0, 0, 0.5);
        encoderDrive(0, 12, 0, 0.5);
        telemetry.addData("Task", "At safe zone");
        telemetry.update();
        sleep(500);

        stop();

    }
}
