/* Copyright (c) 2018 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.teamcode.RoverRuckus.autonomous;

import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;

import java.util.List;

/**
 * <h2>TensorFlow</h2>
 * Used the TensorFlow Object Detection API to determine the position of the gold and silver minerals.
 *
 * <a href="https://github.com/ftctechnh/ftc_app/wiki/Java-Sample-TensorFlow-Object-Detection-Op-Mode">
 * How this code works</a>
 */

public class tensorFlow {

    private static final String tfod_model_asset = "RoverRuckus.tflite";
    private static final String gold_mineral_label = "Gold Mineral";
    private static final String silver_mineral_label = "Silver Mineral";

    /**
     * the variable we will use to store our instance of the Vuforia
     * localization engine.
     */
    private VuforiaLocalizer vuforia;

    /**
     * the variable we will use to store our instance of the Tensor Flow Object
     * Detection engine.
     */
    private TFObjectDetector tfod;

    private int timeout = 5000;
    private ElapsedTime sampleTime = new ElapsedTime();

    private autonomousFrame frame;
    /**
     * Constructs a tensor flow object
     * @param inputFrame the main program usually "this"
     */
    public tensorFlow(autonomousFrame inputFrame) {
        frame = inputFrame;
    }

    /**
     * Sets up tensor flow
     */
    public void initialize() {
        // The TFObjectDetector uses the camera frames from the VuforiaLocalizer, so we create that first.
        initVuforia();
        if (ClassFactory.getInstance().canCreateTFObjectDetector()) {
            initTfod();
        } else {
            frame.telemetry.addData("Sorry!", "This device is not compatible with TFOD");
        }
    }

    /**
     * Scans the minerals and returns the position of the gold mineral
     * @param scanMode 1- scan left 2 minerals, 2- scan right 2 minerals, 3- scan all 3 minerals
     * @return the position of the gold mineral- 1 = left, 2 = center, 3 = right
     */
    public int scan(int scanMode) {
        int position = 0;

        if (frame.opModeIsActive()) {
            /* Activate Tensor Flow Object Detection. */
            if (tfod != null) {
                tfod.activate();
            }

            sampleTime.reset();
            while (frame.opModeIsActive()) {
                if (tfod != null) {
                    /* getUpdatedRecognitions() will return null if no new information is available since
                    the last time that call was made. */
                    List<Recognition> updatedRecognitions = tfod.getUpdatedRecognitions();

                    if (updatedRecognitions != null) {
                        frame.telemetry.addData("# Object Detected", updatedRecognitions.size());

                        // sampling when seeing left 2 or right 2 minerals
                        if (updatedRecognitions.size() == 2) {
                            int goldMineralX = -1;
                            int silverMineral1X = -1;
                            int silverMineral2X = -1;

                            for (Recognition recognition : updatedRecognitions) {
                                if (recognition.getLabel().equals(gold_mineral_label)) {
                                    goldMineralX = (int) recognition.getLeft();
                                } else if (silverMineral1X == -1) {
                                    silverMineral1X = (int) recognition.getLeft();
                                } else {
                                    silverMineral2X = (int) recognition.getLeft();
                                }
                            }

                            /*frame.telemetry.addData("Gold", goldMineralX);
                            frame.telemetry.addData("Silver1", silverMineral1X);
                            frame.telemetry.addData("Silver2", silverMineral2X);
                            frame.telemetry.update();
                            frame.sleep(5000);*/

                            // sampling when seeing left 2 minerals
                            if (scanMode == 1) {
                                if (goldMineralX < silverMineral1X && silverMineral2X == -1) {
                                    frame.telemetry.addData("Gold Mineral Position", "Left");
                                    frame.telemetry.update();
                                    position = 1;
                                } else if (goldMineralX > silverMineral1X && silverMineral2X == -1) {
                                    frame.telemetry.addData("Gold Mineral Position", "Center");
                                    frame.telemetry.update();
                                    position = 2;
                                } else if (goldMineralX == -1 && silverMineral1X != -1 && silverMineral2X != -1) {
                                    frame.telemetry.addData("Gold Mineral Position", "Right");
                                    frame.telemetry.update();
                                    position = 3;
                                }
                            }

                            // sampling when seeing right 2 minerals
                            if (scanMode == 2){
                                if (goldMineralX == -1 && silverMineral1X != -1 && silverMineral2X != -1) {
                                    frame.telemetry.addData("Gold Mineral Position", "Left");
                                    frame.telemetry.update();
                                    position = 1;
                                } else if (goldMineralX < silverMineral1X && silverMineral2X == -1) {
                                    frame.telemetry.addData("Gold Mineral Position", "Center");
                                    frame.telemetry.update();
                                    position = 2;
                                } else if (goldMineralX > silverMineral1X && silverMineral2X == -1){
                                    frame.telemetry.addData("Gold Mineral Position", "Right");
                                    frame.telemetry.update();
                                    position = 3;
                                }
                            }
                        }

                        // sampling when seeing all 3 minerals
                        if (updatedRecognitions.size() == 3 && scanMode == 3) {
                            int goldMineralX = -1;
                            int silverMineral1X = -1;
                            int silverMineral2X = -1;

                            for (Recognition recognition : updatedRecognitions) {
                                if (recognition.getLabel().equals(gold_mineral_label)) {
                                    goldMineralX = (int) recognition.getLeft();
                                } else if (silverMineral1X == -1) {
                                    silverMineral1X = (int) recognition.getLeft();
                                } else {
                                    silverMineral2X = (int) recognition.getLeft();
                                }
                            }

                            if (goldMineralX != -1 && silverMineral1X != -1 && silverMineral2X != -1) {

                                if (goldMineralX < silverMineral1X && goldMineralX < silverMineral2X) {
                                    frame.telemetry.addData("Gold Mineral Position", "Left");
                                    frame.telemetry.update();
                                    position = 1;
                                } else if (goldMineralX > silverMineral1X && goldMineralX > silverMineral2X) {
                                    frame.telemetry.addData("Gold Mineral Position", "Right");
                                    frame.telemetry.update();
                                    position = 3;
                                } else {
                                    frame.telemetry.addData("Gold Mineral Position", "Center");
                                    frame.telemetry.update();
                                    position = 2;
                                }
                            }
                        }
                        frame.telemetry.update();

                        if (position != 0 || sampleTime.milliseconds() >= timeout) {
                            break;
                        }
                    }
                }
            }
        }

        if (tfod != null) {
            tfod.shutdown();
        }
        return position;
    }

    /**
     * Initialize the Vuforia localization engine.
     */
    private void initVuforia() {
        /*
         * Configure Vuforia by creating a Parameter object, and passing it to the Vuforia engine.
         */
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

        parameters.vuforiaLicenseKey = frame.vuforia_key;
        parameters.cameraName = frame.webcamTensor;

        // Instantiate the Vuforia engine
        vuforia = ClassFactory.getInstance().createVuforia(parameters);

        // Loading trackables is not necessary for the Tensor Flow Object Detection engine.
    }

    /**
     * Initialize the Tensor Flow Object Detection engine.
     */
    private void initTfod() {
        int tfodMonitorViewId = frame.hardwareMap.appContext.getResources().getIdentifier(
            "tfodMonitorViewId", "id", frame.hardwareMap.appContext.getPackageName());
        TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
        tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);
        tfod.loadModelFromAsset(tfod_model_asset, gold_mineral_label, silver_mineral_label);
    }
}
