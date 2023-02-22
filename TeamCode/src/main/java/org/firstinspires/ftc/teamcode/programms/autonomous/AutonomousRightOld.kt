package org.firstinspires.ftc.teamcode.programms.autonomous

import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import org.firstinspires.ftc.teamcode.robot.Robot

@Autonomous(name = "Autonomous Right [OLD]")
class AutonomousRightOld : LinearOpMode() {
    var robot: Robot? = null

    // throws InterruptedException
    override fun runOpMode() {
        robot = Robot(this)
        waitForStart()
        robot!!.wheelBase.move(0.0, 1.0, 0.0)
        sleep(1500)
    }
}