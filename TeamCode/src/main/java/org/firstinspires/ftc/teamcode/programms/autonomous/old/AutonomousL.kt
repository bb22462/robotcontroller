package org.firstinspires.ftc.teamcode.programms.autonomous.old

import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.eventloop.opmode.Disabled
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.util.ElapsedTime
import org.firstinspires.ftc.teamcode.robot.Robot

@Disabled
@Autonomous(name = "Autonomous L")
class AutonomousL : LinearOpMode() {
    private val runtime = ElapsedTime()
    var robot: Robot? = null

    // throws InterruptedException
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

        // Close the manipulator
        robot!!.manipulator.setPos(0.2)
        sleep(1000)

        // Move lift up
        robot!!.lift.setPowerRaw(1.0)
        sleep(1500)
        robot!!.lift.setPowerRaw(0.0)

        // Drive forward
        robot!!.wheelBase.move(0.5, 0.0, 0.0)
        sleep(830)
        robot!!.wheelBase.move(0.0, 0.0, 0.0)

        // Drive to the ground
        robot!!.wheelBase.move(0.0, -0.5, 0.0)
        sleep(1800)
        robot!!.wheelBase.move(0.0, 0.0, 0.0)

        // Open the manipulator
        robot!!.manipulator.setPos(0.0)
        sleep(1000)

        // Drive backwards
        robot!!.wheelBase.move(-0.5, 0.0, 0.0)
        sleep(850)
        robot!!.wheelBase.move(0.0, 0.0, 0.0)

        // Drive to the parking
        robot!!.wheelBase.move(0.0, -0.5, 0.0)
        sleep(1800)
        robot!!.wheelBase.move(0.0, 0.0, 0.0)

        // Move lift up
        robot!!.lift.setPowerRaw(-1.0)
        sleep(1500)
        robot!!.lift.setPowerRaw(0.0)

        telemetry.let {
            it.addData("Status", "Run Time: $runtime")
            it.addData("Manipulator's left position", "Position: ${robot!!.manipulator.ManipulatorServo.position}")
            it.update()
        }
    }
}