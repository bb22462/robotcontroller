package org.firstinspires.ftc.teamcode.programms.autonomous

import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.util.ElapsedTime
import org.firstinspires.ftc.teamcode.robot.Robot

@Autonomous
class ResetEncoder : LinearOpMode() {
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

        robot!!.lift.resetEncoder()

        robot!!.podsvetka.setPowerBack(1.0)
        sleep(1000)
        robot!!.podsvetka.setPowerFront(1.0)
        sleep(1000)
        robot!!.podsvetka.setPowerBack(0.0)
        sleep(1000)
        robot!!.podsvetka.setPowerFront(0.0)


        runtime.reset()
    }
}