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

        double x1 = 0;
        double y1 = 0;
        double z1 = 0;

        final double x2 = 0;
        final double y2 = -30;
        final double z2 = 0;

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

            targetVisible = false;
            for (VuforiaTrackable trackable : allTrackables) {
                if (((VuforiaTrackableDefaultListener)trackable.getListener()).isVisible()) {
                    telemetry.addData("Visible Target", trackable.getName());
                    targetVisible = true;

                    OpenGLMatrix robotLocationTransform = ((VuforiaTrackableDefaultListener)trackable.getListener()).getUpdatedRobotLocation();
                    if (robotLocationTransform != null) {
                        lastLocation = robotLocationTransform;
                    }
                    break;
                }
            }

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

            /*
            final double deltaX = Math.abs(x1 - x2);
            final double deltaY = Math.abs(y1 - y2);
            final double CG = Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2));
            final double theta = Math.toDegrees(Math.atan(deltaX/deltaY));
            final double firstTurn = theta + 90 - z1;
            double Straight = CG;
            final double secondTurn = z2 + 90 - theta + z1;

            telemetry.addData("FirstTurn", firstTurn);
            telemetry.addData("Straight", Straight);
            telemetry.addData("SecondTurn", secondTurn);
            */

            final double deltaX = Math.abs(x1 - x2);
            final double deltaY = Math.abs(y1 - y2);
            left(z1, 0.25);
            double angle1 = 0;
            double angle2 = 0;
            if (y2 >= y1) {
                angle1 = 0;
            }
            else if (y2 < y1) {
                angle1 = 180;
            }
            right(angle1, 0.25);
            forward(deltaY, 0.3);
            left(angle1, 0.25);
            if (x2 > x1) {
                angle2 = 90;
            }
            else if (x2 < x1) {
                angle2 = -90;
            }
            else if (x2 == x1) {
                angle2 = 0;
            }
            right(angle2, 0.25);
            forward(deltaX, 0.3);
            left(angle2, 0.25);
            right(z2, 0.25);

            /*
            right(firstTurn,0.25);
            forward(Straight,0.3);
            left(secondTurn,0.25);
            */

        }

        stop();

    }
}