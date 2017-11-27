package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

@Autonomous(name = "Autonomous Bottom Right")
public class autonomousBottomRight extends autonomousFrame {

    @Override
    public void runOpMode() {

        motorLeftfront = hardwareMap.get(DcMotor.class, "motorLeftfront");
        motorRightfront = hardwareMap.get(DcMotor.class, "motorRightfront");
        motorLeftback = hardwareMap.get(DcMotor.class, "motorLeftback");
        motorRightback = hardwareMap.get(DcMotor.class, "motorRightback");
        armMotor = hardwareMap.get(DcMotor.class, "armMotor");
        clawLeft = hardwareMap.servo.get("clawLeft");
        clawRight = hardwareMap.servo.get("clawRight");

        motorLeftfront.setDirection(DcMotor.Direction.FORWARD);
        motorRightfront.setDirection(DcMotor.Direction.FORWARD);
        motorLeftback.setDirection(DcMotor.Direction.FORWARD);
        motorRightback.setDirection(DcMotor.Direction.FORWARD);
        armMotor.setDirection(DcMotor.Direction.REVERSE);

        /*int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);
        // parameters.vuforiaLicenseKey = "Put License Key Here";
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.FRONT;
        this.vuforia = ClassFactory.createVuforiaLocalizer(parameters);
        VuforiaTrackables relicTrackables = this.vuforia.loadTrackablesFromAsset("RelicVuMark");
        VuforiaTrackable relicTemplate = relicTrackables.get(0);
        relicTemplate.setName("relicVuMarkTemplate"); // can help in debugging; otherwise not necessary
        telemetry.addData("Vuforia Status", "Initialized");
        telemetry.update();

        boolean detect = false;*/

        waitForStart();

        armMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        //To safe zone
        /*encoderDrive(-34, 0, 0, 0.5);
        encoderDrive(0, -11, 0, 0.5);
        telemetry.addData("Task", "At safe zone");
        telemetry.update();
        sleep(500);*/

        armPosition(-5700);
        armMotor.setPower(0.5);
        closeClaw();
        leftPosition = clawLeft.getPosition();
        rightPosition = clawRight.getPosition();

        stop();

    }

    // Arm Position Function (Arm, Encoder)
    public void armPosition(int position) {
        armMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        if (position == 0){
            targetValue = -100;
        }
        else if (position == 1){
            targetValue = -1900;
        }
        else if (position == 2){
            targetValue = -3000;
        }
        else if (position == 3){
            targetValue = -4400;
        }
        else if (position == 4){
            targetValue = -5700;
        }
        else {
          targetValue = position;
        }
        armMotor.setTargetPosition(targetValue);
        armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        telemetry.addData("Goal Position", "%7d",targetValue);
    }

    public void closeClaw() {
        leftPosition = 0.6;
        telemetry.addData("Servo Status", "Closed");
        clawLeft.setPosition(leftPosition);
        rightPosition = 1 - leftPosition;
        clawRight.setPosition(rightPosition);
    }

    public void partialClaw() {
        leftPosition = 0.48;
        telemetry.addData("Servo Status", "Partially Open");
        clawLeft.setPosition(leftPosition);
        rightPosition = 1 - leftPosition;
        clawRight.setPosition(rightPosition);
    }

    public void openClaw() {
        leftPosition = 0;
        telemetry.addData("Servo Status", "Open");
        clawLeft.setPosition(leftPosition);
        rightPosition = 1 - leftPosition;
        clawRight.setPosition(rightPosition);
    }
}
