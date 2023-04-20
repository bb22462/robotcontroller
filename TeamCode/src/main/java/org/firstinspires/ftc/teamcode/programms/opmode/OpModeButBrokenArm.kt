package org.firstinspires.ftc.teamcode.programms.opmode

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.util.ElapsedTime
import org.firstinspires.ftc.teamcode.robot.Robot
import kotlin.math.abs
import kotlin.math.max

@TeleOp(name = "OpMode [MISHA EDITION]", group = "Linear Opmode")
class OpModeButBrokenArm : LinearOpMode() {
    private val runtime = ElapsedTime()
    var robot: Robot? = null
    override fun runOpMode() {
        robot = Robot(this)

        // Add the initialization status to the telemetry
        telemetry.let {
            it.addData("Status", "Initialized")
            it.update()
        }
        // Wait for the game to start (driver presses PLAY)
        waitForStart()
        runtime.reset()

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            var max: Double

            // POV Mode uses left joystick to go forward & strafe, and right joystick to rotate.
            val axial = -gamepad1.left_stick_y.toDouble() // Note: pushing stick forward gives negative value
            val lateral = gamepad1.left_stick_x.toDouble()

            // Combine the joystick requests for each axis-motion to determine each wheel's power.
            // Set up a variable for each drive wheel to save the power level for telemetry.
            var leftFrontPower = axial + lateral
            var rightFrontPower = axial - lateral
            var leftBackPower = axial - lateral
            var rightBackPower = axial + lateral

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

            // Check the gamepad buttons and open/close the manipulator or move the wheelbase
            if (gamepad1.y) {
                robot!!.manipulator.setPos(0.2)
            }
            else if (gamepad1.a) {
                robot!!.manipulator.setPos(0.0)
            }
            else if (gamepad1.b) {
                robot!!.wheelBase.setPowerAll(1.0, -1.0, -1.0, 1.0)
                sleep(1)
                robot!!.wheelBase.setPowerAll(0.0, 0.0, 0.0,0.0)
            }
            else if (gamepad1.x) {
                robot!!.wheelBase.setPowerAll(-1.0, 1.0, 1.0, -1.0)
                sleep(1)
                robot!!.wheelBase.setPowerAll(0.0, 0.0, 0.0,0.0)
            }
            else if (gamepad1.right_bumper) {
                robot!!.lift.setPower(-1.0)
            }

            robot!!.lift.setPower(gamepad1.right_trigger.toDouble());

            // Send calculated power to wheels
            robot!!.wheelBase.setPowerAll(leftFrontPower, rightFrontPower, leftBackPower, rightBackPower)

            // Show the elapsed game time, wheel power and manipulator's position.
            telemetry.let {
                it.addData("Status", "Run Time: $runtime")
                it.addData("Front left/Right", "%4.2f, %4.2f", leftFrontPower, rightFrontPower)
                it.addData("Back  left/Right", "%4.2f, %4.2f", leftBackPower, rightBackPower)
                it.addData("Manipulator's left position", "Position: ${robot!!.manipulator.ManipulatorServo.position}")
                it.update()
            }
        }
    }
}