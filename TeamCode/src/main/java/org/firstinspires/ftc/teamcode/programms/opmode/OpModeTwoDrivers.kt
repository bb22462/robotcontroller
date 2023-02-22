package org.firstinspires.ftc.teamcode.programms.opmode

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.util.ElapsedTime
import org.firstinspires.ftc.teamcode.robot.Robot
import kotlin.math.abs
import kotlin.math.max

@TeleOp(name = "OpMode [2 Drivers]", group = "Linear Opmode")
class OpModeTwoDrivers : LinearOpMode() {
    // Declare OpMode members for each motor and servo
    private val runtime = ElapsedTime()
    var robot: Robot? = null
    override fun runOpMode() {
        robot = Robot(this)

        // Wait for the game to start (driver presses PLAY)
        telemetry.addData("Status", "Initialized")
        telemetry.update()
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
            if (gamepad2.b) {
                if (robot!!.manipulator.leftManipulatorServo.position == robot!!.manipulator.maxPos && robot!!.manipulator.rightManipulatorServo.position == robot!!.manipulator.maxPos) {
                    println("Manipulator is already opened.")
                } else {
                    robot!!.manipulator.setPos(0.3)
                }
            } else if (gamepad2.x) {
                if (robot!!.manipulator.leftManipulatorServo.position == robot!!.manipulator.minPos && robot!!.manipulator.rightManipulatorServo.position == robot!!.manipulator.minPos) {
                    println("Manipulator is already closed.")
                } else {
                    robot!!.manipulator.setPos(0.0)
                }
            }
            robot!!.lift.setPower(gamepad2.right_stick_y.toDouble())


            // Send calculated power to wheels
            robot!!.wheelBase.setPowerAll(leftFrontPower, rightFrontPower, leftBackPower, rightBackPower)

            // Show the elapsed game time and wheel power.
            telemetry.addData("Status", "Run Time: $runtime")
            telemetry.addData("Front left/Right", "%4.2f, %4.2f", leftFrontPower, rightFrontPower)
            telemetry.addData("Back  left/Right", "%4.2f, %4.2f", leftBackPower, rightBackPower)
            telemetry.update()
        }
    }
}