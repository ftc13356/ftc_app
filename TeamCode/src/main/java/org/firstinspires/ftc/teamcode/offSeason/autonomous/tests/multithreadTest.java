package org.firstinspires.ftc.teamcode.offSeason.autonomous.tests;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.offSeason.autonomous.autonomousFrame;

class threadMoveForward implements Runnable {
    private Thread moveForwardThread;
    private String threadName;
    private autonomousFrame frame;
    public boolean done = false;

    threadMoveForward(String name, autonomousFrame autonomousFrame) {
        frame = autonomousFrame;
        threadName = name;
        frame.print("Creating", threadName);
    }

    public void run() {
        frame.print("Running", threadName);
        frame.encoderDrive(48,0,0,0.5);
        done = true;
    }

    public void start () {
        frame.print("Starting", threadName);
        moveForwardThread = new Thread (this, threadName);
        moveForwardThread.start ();
    }
}

class threadMoveColorArm implements Runnable {
    private Thread moveColorArmThread;
    private String threadName;
    private autonomousFrame frame;
    public boolean done = false;

    threadMoveColorArm(String name, autonomousFrame autonomousFrame) {
        frame = autonomousFrame;
        threadName = name;
        frame.print("Creating", threadName);
    }

    public void run() {
        frame.print("Running", threadName);
        frame.extendColorArm();
        done = true;
    }

    public void start () {
        frame.print("Starting", threadName);
        moveColorArmThread = new Thread (this, threadName);
        moveColorArmThread.start ();
    }
}

@Autonomous(name = "Multi-thread Test")
public class multithreadTest extends autonomousFrame {

    public void runOpMode() {
        initializeHardwareMap();
        setMotorDirection();
        gripGlyphHolonomic();

        waitForStart();

        threadMoveForward moveForward = new threadMoveForward("Move Forward", this);
        moveForward.start();

        threadMoveColorArm moveColorArm = new threadMoveColorArm("Move Color Arm", this);
        moveColorArm.start();

        while (!(moveForward.done && moveColorArm.done)) {
            if (messages.size() > 0) {
                while(messages.size() > 0) {
                    telemetry.addData("Program", messages.get(0));
                    messages.remove(0);
                }
                telemetry.update();
            }
            sleep(10);
        }

        stop();
    }
}
