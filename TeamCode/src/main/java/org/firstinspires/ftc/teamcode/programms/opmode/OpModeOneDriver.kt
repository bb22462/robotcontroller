package org.firstinspires.ftc.teamcode.programms.opmode

import com.acmerobotics.dashboard.FtcDashboard
import com.acmerobotics.dashboard.config.Config
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.util.ElapsedTime
import org.firstinspires.ftc.teamcode.robot.Robot
import kotlin.math.abs
import kotlin.math.max


// 1) Axial:    Driving forward and backward               Left-joystick Forward/Backward
// 2) Lateral:  Strafing right and left                     Left-joystick Right and Left
// 3) Yaw:      Rotating Clockwise and counter clockwise    Right-joystick Right and Left
@Config
@TeleOp(name = "OpMode [1 Driver]", group = "Linear OpMode")
class OpModeOneDriver : LinearOpMode() {

    //Companion object with variables for dashboard
    companion object {
        @JvmField
        var closePos = 0.4
        @JvmField
        var openPos = 0.7
        @JvmField
        var moveClosePos = 0.6
        @JvmField
        var moveOpenPos = 0.2
        @JvmField
        var samoletclose = 0.4
        @JvmField
        var samoletopen = 0.15
        @JvmField
        var closePosLeft = 0.5
        @JvmField
        var openPosLeft = 0.0
        @JvmField
        var closePosRight = 0.3
        @JvmField
        var openPosRight = 0.8
    }

    private val runtime = ElapsedTime();
    var robot: Robot? = null

    override fun runOpMode() {
        // Re-translate telemetry to dashboard
        telemetry = MultipleTelemetry(telemetry, FtcDashboard.getInstance().telemetry)

        robot = Robot(this)

        // Add the initialization status to the telemetry
        telemetry.let {
            it.addData("Status", "Initialized")
            it.update()
        }
        // Wait for the game to start (driver presses PLAY)
        robot!!.lift.resetEncoder();
        waitForStart()
        runtime.reset()

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            var max: Double

            // POV Mode uses left joystick to go forward & strafe, and right joystick to rotate.
            val axial = -gamepad1.left_stick_y.toDouble() // Note: pushing stick forward gives negative value
            val lateral = gamepad1.left_stick_x.toDouble()
            val yaw = gamepad1.right_stick_x.toDouble()

            // Combine the joystick requests for each axis-motion to determine each wheel's power.
            // Set up a variable for each drive wheel to save the power level for telemetry.
            var leftFrontPower = axial + lateral + yaw
            var rightFrontPower = axial - lateral - yaw
            var leftBackPower = axial - lateral + yaw
            var rightBackPower = axial + lateral - yaw

            // Normalize the values so no wheel power exceeds 100%
            // This ensures that the robot maintains the desired motion.
            max = max(abs(leftFrontPower), abs(rightFrontPower))
            max = max(max, abs(leftBackPower))
            max = max(max, abs(rightBackPower))
            if (max > 1.0) {
                leftFrontPower /= max
                rightFrontPower /= max
                leftBackPower /= max
                rightBackPower /= max
            }

            // Check the gamepad buttons and open/close the manipulator
            if (gamepad1.b) {
                if (robot!!.manipulator.ManipulatorServo.position == robot!!.manipulator.maxPos) {
                    println("Manipulator is already closed.")
                } else {
                    robot!!.manipulator.setPos(closePos)
                    sleep(500)
                    robot!!.manipulator.moveSetPos(moveClosePos)
                }
            }
            else if (gamepad1.x) {
                if (robot!!.manipulator.ManipulatorServo.position == robot!!.manipulator.minPos) {
                    println("Manipulator is already opened.")
                } else {
                    robot!!.manipulator.setPos(openPos)
                }
            }
            else if (gamepad1.a) {
                robot!!.manipulator.moveSetPos(moveOpenPos)
            }
            else if (gamepad1.y) {
                robot!!.manipulator.LeftHangServo.position = openPosLeft
                robot!!.manipulator.RightHangServo.position = openPosRight
            }



            // Check the gamepad buttons and move the lift up down
            if (gamepad1.dpad_up) {
                robot!!.lift.setPower(1.0)
            }
            else if (gamepad1.dpad_down) {
                robot!!.lift.setPower(-1.0)
            }
            else {
                robot!!.lift.setPower(0.0)
            }

            if(gamepad1.right_bumper) {
                robot!!.manipulator.Samolet.position = samoletopen
            }
            else if(gamepad1.left_bumper) {
                robot!!.manipulator.Samolet.position = samoletclose
            }
            //Sensors
            if(robot!!.sensor.sensorRight.voltage < 0.12) {
                robot!!.wheelBase.rightLight.power = 1.0
            }
            else {
                robot!!.wheelBase.rightLight.power = 0.0
            }

            if(robot!!.sensor.sensorLeft.voltage < 0.12) {
                robot!!.wheelBase.leftLight.power = 1.0
            }
            else {
                robot!!.wheelBase.leftLight.power = 0.0
            }

            // Send calculated power to wheels
            robot!!.wheelBase.setPowerAll(leftFrontPower, rightFrontPower, leftBackPower, rightBackPower)
            robot!!.hang.setPower(gamepad1.right_trigger.toDouble())
            robot!!.hang.setPower(-gamepad1.left_trigger.toDouble())
            // Show the elapsed game time, wheel power and manipulator's position.
            telemetry.let {
                it.addData("Status", "Run Time: $runtime")
                it.addData("Left Front Wheel", "Position: ${robot!!.wheelBase.leftFrontDrive.currentPosition}")
                it.addData("Left Back Wheel", "Position: ${robot!!.wheelBase.leftBackDrive.currentPosition}")
                it.addData("Right Front Wheel", "Position: ${robot!!.wheelBase.rightFrontDrive.currentPosition}")
                it.addData("Right Back Wheel", "Position: ${robot!!.wheelBase.rightBackDrive.currentPosition}")
                it.addData("Lift", "Position: ${robot!!.lift.lift.currentPosition}")
                it.update()
            }
        }
    }
}