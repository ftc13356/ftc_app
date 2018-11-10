package org.firstinspires.ftc.teamcode.RoverRuckus.autonomous.tests;

import android.graphics.Color;

import org.firstinspires.ftc.teamcode.RoverRuckus.autonomous.autonomousFrame;

import java.util.ArrayList;

public class colorChecker {

    private float hsvValues[] = {0F, 0F, 0F};

    private ArrayList checksSaturation = new ArrayList();

    private float checksSaturationAverage = 0;
    private float checksSaturationSigma = 0;

    private float goldSaturationSigma = 0.092f;
    private float silverSaturationSigma = 0.051f;

    private float goldSaturationBenchmark = 0.595f;
    private float silverSaturationBenchmark = 0.287f;

    private int sigmaRange = 2;

    private final int timesToCheck = 10;

    /*
    notes
    +- 1 sigma: 68% of data
    +- 2 sigma: 95% of data
    */

    private autonomousFrame frame;
    colorChecker(autonomousFrame inputFrame) {
        frame = inputFrame;
    }

    public void detectObject() {

        calibrate();

        if (goldSaturationBenchmark >= checksSaturationAverage - sigmaRange * goldSaturationSigma &&
                goldSaturationBenchmark <= checksSaturationAverage + sigmaRange * goldSaturationSigma) {

            frame.telemetry.addData("Object Detected", "Gold");
        }

        if (silverSaturationBenchmark >= checksSaturationAverage - sigmaRange * silverSaturationSigma &&
                silverSaturationBenchmark <= checksSaturationAverage + sigmaRange * goldSaturationSigma) {

            frame.telemetry.addData("Object Detected", "Silver");
        }
        frame.telemetry.update();
    }

    public void calibrate() {
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

        frame.sleep(30000);

    }

    public float calculateAverage(ArrayList myList) {
        float total = 0;
        for (int i = 0; i < myList.size(); i++) {

            total += (float) myList.get(i);
        }
        return total / myList.size();
    }

    //subtract average from data, square them, take average of the squares, take square root of average
    public float calculateSigma(ArrayList myList, float average) {
        float squaredTotals = 0;
        for (int i = 0; i < myList.size(); i++) {
            squaredTotals += Math.pow(((float) myList.get(i) - average), 2);
        }
        return (float) Math.sqrt(squaredTotals / myList.size());
    }
}
