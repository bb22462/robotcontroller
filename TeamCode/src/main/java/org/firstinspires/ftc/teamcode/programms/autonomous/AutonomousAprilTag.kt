package org.firstinspires.ftc.teamcode.programms.autonomous

import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.util.ElapsedTime
import org.firstinspires.ftc.teamcode.robot.Robot

@Autonomous(name = "Autonomous April Tag")
class AutonomousAprilTag : LinearOpMode() {
    private var tag = 0
    private val runtime = ElapsedTime()
    var robot: Robot? = null

    // throws InterruptedException
    override fun runOpMode() {
        robot = Robot(this)
        robot!!.camera.initCamera()

        telemetry.let {
            it.addData("Status", "Initialized")
            it.update()
        }

        while (!isStarted && !isStopRequested) {
            tag = robot!!.camera.findTag()
            telemetry.let {
                it.addData("Camera", "AprilTag: $tag")
                it.update()
            }
        }
        // Wait for the game to start (driver presses PLAY)
        waitForStart()

        when (tag) {
            1 -> {
                robot!!.wheelBase.moveEncoder(0.0, -60.5, 0.0, 0.8)
                robot!!.wheelBase.moveEncoder(80.5, 0.0, 0.0, 0.8)
            }
            2 -> {
                robot!!.wheelBase.moveEncoder(0.0, -60.5, 0.0, 0.8)
                robot!!.wheelBase.moveEncoder(121.0, 0.0, 0.0, 0.8)
                robot!!.wheelBase.moveEncoder(0.0, 60.5, 0.0, 0.8)
            }
            3 -> {
                robot!!.wheelBase.moveEncoder(0.0, 60.5, 0.0, 0.8)
                robot!!.wheelBase.moveEncoder(80.5, 0.0, 0.0, 0.8)
            }
            else -> {
                robot!!.wheelBase.moveEncoder(0.0, 60.5, 0.0, 0.8)
                robot!!.wheelBase.moveEncoder(80.5, 0.0, 0.0, 0.8)
            }
        }

        runtime.reset()
    }
}