package org.firstinspires.ftc.teamcode.programms.opmode

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.util.ElapsedTime
import org.firstinspires.ftc.teamcode.robot.Robot
import kotlin.math.abs
import kotlin.math.max

@TeleOp(name = "OpMode [1 Driver]", group = "Linear OpMode")
class OpModeOneDriver : LinearOpMode() {
    private val runtime = ElapsedTime();
    var robot: Robot? = null
    override fun runOpMode() {
        robot = Robot(this)

        // Add the initialization status to the telemetry
        telemetry.let {
            it.addData("Status", "Initialized")
            it.update()
        }
        // Wait for the game to start (driver presses PLAY)
        robot!!.lift.resetEncoder();
        robot!!.manipulator.moveSetPos(0.2)
        robot!!.manipulator.setPos(0.2)
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
                if (robot!!.manipulator.ManipulatorServo.position >= 0.44) {
                    println("Manipulator is already closed.")
                }
                else if (robot!!.manipulator.MoveServo.position >= 0.59) {
                    println("Уже все поднято")
                }

                else {
                    robot!!.manipulator.setPos(0.4)
                    sleep(500)
                    robot!!.manipulator.moveSetPos(0.59)
                }
            }
            else if (gamepad1.x) {
                if (robot!!.manipulator.ManipulatorServo.position == robot!!.manipulator.minPos) {
                    println("Manipulator is already opened.")
                } else {
                    robot!!.manipulator.setPos(0.19)
                }
            }

            else if (gamepad1.a) {
                robot!!.manipulator.moveSetPos(0.2)
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

            // Send calculated power to wheels
            robot!!.wheelBase.setPowerAll(leftFrontPower, rightFrontPower, leftBackPower, rightBackPower)

            // Show the elapsed game time, wheel power and manipulator's position.
            telemetry.let {
                it.addData("Status", "Run Time: $runtime")
                it.addData("Front Left/Right", "%4.2f, %4.2f", leftFrontPower, rightFrontPower)
                it.addData("Back  Left/Right", "%4.2f, %4.2f", leftBackPower, rightBackPower)
                it.update()
            }
        }
    }
}