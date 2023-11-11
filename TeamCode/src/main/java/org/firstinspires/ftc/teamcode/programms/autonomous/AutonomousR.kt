package org.firstinspires.ftc.teamcode.programms.autonomous

import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.util.ElapsedTime
import org.firstinspires.ftc.teamcode.robot.Robot

@Autonomous(name = "Autonomous R")
class AutonomousR : LinearOpMode() {
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


        // Drive to the parking
        robot!!.wheelBase.move(0.0, 0.5, 0.0)
        sleep(3000)
        robot!!.wheelBase.move(0.0,0.0, 0.0)

        telemetry.let {
            it.addData("Status", "Run Time: $runtime")
            it.update()
        }
    }
}