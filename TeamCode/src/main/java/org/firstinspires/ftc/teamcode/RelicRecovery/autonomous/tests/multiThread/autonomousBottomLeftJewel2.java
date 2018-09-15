package org.firstinspires.ftc.teamcode.RelicRecovery.autonomous.tests.multiThread;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

class detectVuMark implements Runnable {
    private Thread detectVuMarkThread;
    private String threadName;
    private autonomousFrame2 frame;
    public boolean detectDone = false;

    detectVuMark(String name, autonomousFrame2 autonomousFrame2) {
        frame = autonomousFrame2;
        threadName = name;
        frame.print2("Creating", threadName);
    }

    public void run() {
        frame.print2("Running", threadName);

        frame.moveArm(-0.2, 1000);
        frame.detectVuMark(-20, -12.5, -5);

        detectDone = true;
    }

    public void start () {
        frame.print2("Starting", threadName);
        detectVuMarkThread = new Thread (this, threadName);
        detectVuMarkThread.start ();
    }
}

class knockJewel implements Runnable {
    private Thread knockJewelThread;
    private String threadName;
    private autonomousFrame2 frame;
    public boolean knockDone = false;

    knockJewel(String name, autonomousFrame2 autonomousFrame2) {
        frame = autonomousFrame2;
        threadName = name;
        frame.print2("Creating", threadName);
    }

    public void run() {
        frame.print2("Running", threadName);

        frame.extendColorArm();
        frame.reactToJewelDetect(17.5);
        frame.encoderDrive(0, 0, frame.distanceJewel, 0.5);
        frame.retractColorArm();

        knockDone = true;
    }

    public void start () {
        frame.print2("Starting", threadName);
        knockJewelThread = new Thread (this, threadName);
        knockJewelThread.start ();
    }
}

@Autonomous(name = "Autonomous Bottom Left Jewel (HouseB)2")
public class autonomousBottomLeftJewel2 extends autonomousFrame2 {

    private boolean programDone = false;

    public void runOpMode() {

        versionPrint();
        initializeHardwareMap();
        setMotorDirection();
        initializeVuforia();

        allianceColor = 1;

        gripGlyphHolonomic();

        waitForStart();

        detectVuMark detectVuMark = new detectVuMark("Detect VuMark", this);
        detectVuMark.start();

        knockJewel knockJewel = new knockJewel("Knock Jewel", this);
        knockJewel.start();

        if (detectVuMark.detectDone && knockJewel.knockDone) {
            // Drive to appropriate cryptobox column
            encoderDrive(-28, 0, 0, 0.5);
            encoderDrive(0, distanceVuMark, 0, 0.5);
            encoderDrive(-7, 0, 0, 0.5);

            releaseGlyphSwerve();
            print2("Task", "Glyph In");

            // Back up from, shove in, back up from glyph
            encoderDrive(5, 0, 0, 0.5);
            encoderDrive(-5, 0, 0, 0.5);
            encoderDrive(4, 0, 0, 0.5);
            print2("Task", "At safe zone");

            armMotor.setPower(0.2);
            while (touchSensor.getState()) {
                print2("Touch Sensor", touchSensor.getState());
                telemetry.update();
            }
            armMotor.setPower(0);
        }

        while (!programDone) {
            if (queue.size() > 0) {
                while (queue.size() > 0) {
                    telemetry.addData("Robot", queue.get(0));
                    queue.remove(0);
                }
                telemetry.update();
            }
            sleep(10);
        }

        stop();
    }
}