package org.firstinspires.ftc.teamcode.programms.autonomous

import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.util.ElapsedTime
import org.firstinspires.ftc.teamcode.robot.Robot

@Autonomous(name = "CamTest")
class CamTest : LinearOpMode() {
    private val runtime = ElapsedTime()
    var robot: Robot? = null

    // throws InterruptedException
    override fun runOpMode() {
        robot = Robot(this)
        robot!!.camera.initCamera()

        while (!isStarted() && !isStopRequested()) {
            var tagTest: Int = robot!!.camera.findTag()
            telemetry.let {
                it.addData("Camera", "Position: ${tagTest}")
                it.update()
            }
        }
        // Wait for the game to start (driver presses PLAY)
        waitForStart()
        runtime.reset()

    }
}