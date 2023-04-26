package org.firstinspires.ftc.teamcode.programms.autonomous

import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.util.ElapsedTime
import org.firstinspires.ftc.teamcode.robot.Robot

@Autonomous
class TestEncoder : LinearOpMode() {
    private var tag = 0
    private val runtime = ElapsedTime()
    var robot: Robot? = null

    // throws InterruptedException
    override fun runOpMode() {
        robot = Robot(this)

        telemetry.let {
            it.addData("Status", "Initialized")
            it.update()
        }

        // Wait for the game to start (driver presses PLAY)
        waitForStart()

        robot!!.wheelBase.moveEncoder(0.0, 60.0, 0.0, 0.3)


        runtime.reset()
    }
}