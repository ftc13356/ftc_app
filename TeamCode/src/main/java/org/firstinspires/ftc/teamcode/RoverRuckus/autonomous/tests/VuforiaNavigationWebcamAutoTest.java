package org.firstinspires.ftc.teamcode.RoverRuckus.autonomous.tests;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;
import org.firstinspires.ftc.teamcode.RoverRuckus.autonomous.autonomousFrame;

import java.util.ArrayList;
import java.util.List;

import static org.firstinspires.ftc.robotcore.external.navigation.AngleUnit.DEGREES;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesOrder.XYZ;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesReference.EXTRINSIC;

import static org.firstinspires.ftc.teamcode.key.key;

/**
 * Purpose: Vuforia Navigation Test In Autonomous (W/ Webcam)
 */

@Autonomous(name = "VuforiaNavigationWebcamAutoTest")
// @Disabled
public class VuforiaNavigationWebcamAutoTest extends autonomousFrame {

    @Override
    public void runOpMode() {

        WebcamName webcamName;
        webcamName = hardwareMap.get(WebcamName.class, "Webcam");
        VuforiaLocalizer vuforia;
        final String vuforia_key = key;
        OpenGLMatrix lastLocation = null;
        boolean targetVisible = false;
        final float mmPerInch = 25.4f;
        final float mmFTCFieldWidth = (12 * 6) * mmPerInch;
        final float mmTargetHeight = (6) * mmPerInch;

        int desiredX = 0;
        int desiredY = 0;
        int desiredHeading = 0;

        int camera_forward_displacement = 0;
        int camera_left_displacement = 0;
        int camera_vertical_displacement = 0;

        waitForStart();

        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);

        parameters.vuforiaLicenseKey = vuforia_key;
        parameters.cameraName = webcamName;

        vuforia = ClassFactory.getInstance().createVuforia(parameters);

        VuforiaTrackables targetsRoverRuckus = vuforia.loadTrackablesFromAsset("RoverRuckus");
        VuforiaTrackable blueRover = targetsRoverRuckus.get(0);
        blueRover.setName("Blue-Rover");
        VuforiaTrackable redFootprint = targetsRoverRuckus.get(1);
        redFootprint.setName("Red-Footprint");
        VuforiaTrackable frontCraters = targetsRoverRuckus.get(2);
        frontCraters.setName("Front-Craters");
        VuforiaTrackable backSpace = targetsRoverRuckus.get(3);
        backSpace.setName("Back-Space");

        List<VuforiaTrackable> allTrackables = new ArrayList<VuforiaTrackable>();
        allTrackables.addAll(targetsRoverRuckus);

        OpenGLMatrix blueRoverLocationOnField = OpenGLMatrix
                .translation(0, mmFTCFieldWidth, mmTargetHeight)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0, 0));
        blueRover.setLocationFtcFieldFromTarget(blueRoverLocationOnField);

        OpenGLMatrix redFootprintLocationOnField = OpenGLMatrix
                .translation(0, -mmFTCFieldWidth, mmTargetHeight)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0, 180));
        redFootprint.setLocationFtcFieldFromTarget(redFootprintLocationOnField);

        OpenGLMatrix frontCratersLocationOnField = OpenGLMatrix
                .translation(-mmFTCFieldWidth, 0, mmTargetHeight)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0 , 90));
        frontCraters.setLocationFtcFieldFromTarget(frontCratersLocationOnField);

        OpenGLMatrix backSpaceLocationOnField = OpenGLMatrix
                .translation(mmFTCFieldWidth, 0, mmTargetHeight)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0, -90));
        backSpace.setLocationFtcFieldFromTarget(backSpaceLocationOnField);

        OpenGLMatrix robotFromCamera = OpenGLMatrix
                .translation(camera_forward_displacement, camera_left_displacement, camera_vertical_displacement)
                .multiplied(Orientation.getRotationMatrix(
                        AxesReference.EXTRINSIC, AxesOrder.XZY,
                        AngleUnit.DEGREES, 90, 90, 0));

        for (VuforiaTrackable trackable : allTrackables) {
            ((VuforiaTrackableDefaultListener)trackable.getListener()).setPhoneInformation(robotFromCamera, parameters.cameraDirection);
        }

        targetsRoverRuckus.activate();
        while (opModeIsActive()) {
            String targetName = "0";

            targetVisible = false;
            for (VuforiaTrackable trackable : allTrackables) {
                if (((VuforiaTrackableDefaultListener)trackable.getListener()).isVisible()) {
                    telemetry.addData("Visible Target", trackable.getName());
                    targetName = trackable.getName();
                    targetVisible = true;

                    OpenGLMatrix robotLocationTransform = ((VuforiaTrackableDefaultListener)trackable.getListener()).getUpdatedRobotLocation();
                    if (robotLocationTransform != null) {
                        lastLocation = robotLocationTransform;
                    }
                    break;
                }
            }

            if (targetName == "Blue-Rover") {
                desiredX = 45;
                desiredY = -45;
            }
            else if (targetName == "Red-Footprint") {
                desiredX = -45;
                desiredY = 45;
            }
            else if (targetName == "Front-Craters") {
                desiredX = -30;
                desiredY = -30;
            }
            else if (targetName == "Back-Space") {
                desiredX = 30;
                desiredY = 30;
            }

            double x1 = 0;
            double y1 = 0;
            double z1 = 0;

            if (targetVisible) {

                VectorF translation = lastLocation.getTranslation();
                x1 = translation.get(0) / mmPerInch;
                y1 = translation.get(1) / mmPerInch;
                telemetry.addData("Pos (in)", "{X, Y, Z} = %.1f, %.1f, %.1f", translation.get(0) / mmPerInch, translation.get(1) / mmPerInch, translation.get(2) / mmPerInch);

                Orientation rotation = Orientation.getOrientation(lastLocation, EXTRINSIC, XYZ, DEGREES);
                z1 = rotation.thirdAngle;
                telemetry.addData("Rot (deg)", "{Roll, Pitch, Heading} = %.0f, %.0f, %.0f", rotation.firstAngle, rotation.secondAngle, rotation.thirdAngle);
            }
            else {
                telemetry.addData("Visible Target", "none");
            }
            telemetry.update();

            double x2 = desiredX;
            double y2 = desiredY;
            double z2 = desiredHeading;

            double temp0 = x1-x2;
            double temp1 = Math.pow(temp0, 2);
            double temp2 = Math.pow(y1-y2, 2);
            double CG = Math.sqrt(temp1 + temp2);
            double theta = Math.asin(temp0/CG);
            double firstTurn = z1+90-theta;
            double Straight = CG;
            double secondTurn = z2+90-theta;

            telemetry.addData("FirstTurn",firstTurn);
            telemetry.addData("Straight",Straight);
            telemetry.addData("SecondTurn",secondTurn);

            /*
            right(firstTurn,0.25);
            forward(Straight,0.3);
            left(secondTurn,0.25);
            */

        }

        stop();

    }
}