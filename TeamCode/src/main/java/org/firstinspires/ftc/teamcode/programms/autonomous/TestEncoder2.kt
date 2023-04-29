package org.firstinspires.ftc.teamcode.programms.autonomous

import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.util.ElapsedTime
import org.firstinspires.ftc.teamcode.robot.Robot

@Autonomous
class TestEncoder2 : LinearOpMode() {
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

        robot!!.wheelBase.moveEncoder(0.0, 0.0, 90.0, 1.0)
        sleep(3000)
        robot!!.wheelBase.moveEncoder(0.0, 0.0, -180.0, 1.0)


        runtime.reset()
    }
}