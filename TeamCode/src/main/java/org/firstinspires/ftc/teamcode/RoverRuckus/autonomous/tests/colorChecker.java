package org.firstinspires.ftc.teamcode.RoverRuckus.autonomous.tests;

import android.graphics.Color;

import org.firstinspires.ftc.teamcode.RoverRuckus.autonomous.autonomousFrame;

import java.util.ArrayList;

/**
 * Purpose:
 * <p> To tell if a mineral is gold or silver during sampling
 * <p> It sees if a predetermined benchmark number is within ±2σ of the average saturation (how white) of the mineral
 *
 * <p> Benchmark Number:
 * Calculated by taking the average saturation after moving each mineral to various distances away from the color sensor
 * <p> Standard Deviation (σ):
 * We took the σ of the tests
 */

public class colorChecker {

    private float hsvValues[] = {0F, 0F, 0F};

    private ArrayList checksSaturation = new ArrayList();

    private float checksSaturationAverage = 0;

    private float checksSaturationSigma = 0; // for debugging

    // Mineral standard deviations (sigma):
    private float goldSaturationSigma = 0.092f;
    private float silverSaturationSigma = 0.051f;

    // Mineral benchmark numbers:
    private float goldSaturationBenchmark = 0.595f;
    private float silverSaturationBenchmark = 0.287f;

    private int sigmaRange = 2;
    private final int timesToCheck = 10;

    private int objectDetected = 0;

    /*
    notes
    +- 1 sigma: 68% of data
    +- 2 sigma: 95% of data
    */

    private autonomousFrame frame;
    colorChecker(autonomousFrame inputFrame) {
        frame = inputFrame;
    }

    public int detectObject() {

        calibrate();

        // Following if statements see if benchmark number is within ±2σ of the average saturation of the mineral
        if (goldSaturationBenchmark >= checksSaturationAverage - sigmaRange * goldSaturationSigma &&
                goldSaturationBenchmark <= checksSaturationAverage + sigmaRange * goldSaturationSigma) {

            objectDetected = 1;
            frame.telemetry.addData("Object Detected", "Gold");
        }

        if (silverSaturationBenchmark >= checksSaturationAverage - sigmaRange * silverSaturationSigma &&
                silverSaturationBenchmark <= checksSaturationAverage + sigmaRange * goldSaturationSigma) {

            objectDetected = 2;
            frame.telemetry.addData("Object Detected", "Silver");
        }
        frame.telemetry.update();

        return objectDetected;
    }

    public void calibrate() {
        // Takes 10 saturation readings from the color sensor and adds them to a list
        for (int i = 0; i <= timesToCheck; i++) {
            Color.RGBToHSV(frame.colorSensor.red(), frame.colorSensor.green(), frame.colorSensor.blue(), hsvValues);

            checksSaturation.add(hsvValues[1]);

            frame.sleep(50);
        }

        checksSaturationAverage = calculateAverage(checksSaturation);

        checksSaturationSigma = calculateSigma(checksSaturation, checksSaturationAverage);

        frame.telemetry.addData("Saturation Average", checksSaturationAverage);
        frame.telemetry.addData("Saturation Sigma", checksSaturationSigma);

        frame.telemetry.update();
    }

    // To calculate the average:
    // add up all data points, divide by number of data points
    public float calculateAverage(ArrayList myList) {
        float total = 0;
        for (int i = 0; i < myList.size(); i++) {

            total += (float) myList.get(i);
        }
        return total / myList.size();
    }

    // To calculate the standard deviation (sigma):
    // subtract average from each data point, square that, take average of the squares, take square root of average
    public float calculateSigma(ArrayList myList, float average) {
        float squaredTotals = 0;
        for (int i = 0; i < myList.size(); i++) {
            squaredTotals += Math.pow(((float) myList.get(i) - average), 2);
        }
        return (float) Math.sqrt(squaredTotals / myList.size());
    }
}
