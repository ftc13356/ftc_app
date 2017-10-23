/* Copyright (c) 2017 FIRST. All rights reserved.
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

package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name="Android Studio Demo")
// @Disabled
public class androidStudioDemo extends OpMode
{
    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor motorLeftfront;
    private DcMotor motorRightfront;
    private DcMotor motorLeftback;
    private DcMotor motorRightback;

    /*
     * Code to run ONCE when the driver hits INIT
     */
    @Override
    public void init() {
        telemetry.addData("Status", "Initializing");

        // Initialize the hardware variables. Note that the strings used here as parameters
        // to 'get' must correspond to the names assigned during the robot configuration
        // step (using the FTC Robot Controller app on the phone).
        motorLeftfront = hardwareMap.get(DcMotor.class, "motorLeftfront");
        motorRightfront = hardwareMap.get(DcMotor.class, "motorRightfront");
        motorLeftback = hardwareMap.get(DcMotor.class, "motorLeftback");
        motorRightback = hardwareMap.get(DcMotor.class, "motorRightback");

        // Tell the driver that initialization is complete.
        telemetry.addData("Status", "Initialized");
    }

    /*
     * Code to run REPEATEDLY after the driver hits INIT, but before they hit PLAY
     */
    @Override
    public void init_loop() {
    }

    /*
     * Code to run ONCE when the driver hits PLAY
     */
    @Override
    public void start() {
        runtime.reset();

    }

    /*
     * Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
     */
    @Override
    public void loop() {
        // Setup a variable for each drive wheel to save power level for telemetry
        double motorLeftfrontPower;
        double motorRightfrontPower;
        double motorLeftbackPower;
        double motorRightbackPower;

        // POV Mode uses left stick to go forward, and right stick to turn.
        // - This uses basic math to combine motions and is easier to drive straight.
        //double driveFW = gamepad1.left_stick_y;
        //double driveS = gamepad1.left_stick_x;
        //double turn = gamepad1.right_stick_x;

        //motorLeftfrontPower = Range.clip((-driveFW + driveS + turn), -1.0, 1.0) ;
        //motorRightfrontPower = Range.clip((driveFW + driveS + turn), -1.0, 1.0) ;
        //motorLeftbackPower = Range.clip((-driveFW - driveS + turn), -1.0, 1.0) ;
        //motorRightbackPower = Range.clip((driveFW - driveS + turn), -1.0, 1.0) ;

        motorRightfrontPower = gamepad1.left_stick_y;
        motorLeftbackPower = -gamepad1.left_stick_y;
        motorLeftfrontPower = gamepad1.left_stick_x;
        motorRightbackPower = -gamepad1.left_stick_x;

        // Send calculated power to wheels
        motorLeftfront.setPower(motorLeftfrontPower);
        motorRightfront.setPower(motorRightfrontPower);
        motorLeftback.setPower(motorLeftbackPower);
        motorRightback.setPower(motorRightbackPower);

        // Show the elapsed game time and wheel power.
        telemetry.addData("Status", "Run Time: " + runtime.toString());
        telemetry.addData("Gamepad 1", "(%.2f)", gamepad1.left_stick_y);
        //telemetry.addData("Motors", "Left Front (%.2f), Right Front (%.2f), Left Back (%.2f), Right Back (%2f)", motorLeftfrontPower, motorRightfrontPower, motorRightbackPower, motorRightbackPower);
    }

    /*
     * Code to run ONCE after the driver hits STOP
     */
    @Override
    public void stop() {
    }

}
