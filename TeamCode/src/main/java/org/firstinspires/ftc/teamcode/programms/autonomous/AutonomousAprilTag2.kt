package org.firstinspires.ftc.teamcode.programms.autonomous

import com.acmerobotics.dashboard.FtcDashboard
import com.acmerobotics.dashboard.config.Config
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry
import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.util.ElapsedTime
import org.firstinspires.ftc.teamcode.robot.Robot

@Config
@Autonomous(name = "Autonomous April Tag 2")
class AutonomousAprilTag2 : LinearOpMode() {
    companion object {
        @JvmField
        var firstForward1 = 80.0
        @JvmField
        var secondTurn1 = -90.0
        @JvmField
        var thirdForward1 = 60.0

        @JvmField
        var firstForward2 = 80.0

        @JvmField
        var firstForward3 = 80.0
        @JvmField
        var secondTurn3 = 90.0
        @JvmField
        var thirdForward3 = 60.0



    }
    private var tag = 0
    private val runtime = ElapsedTime()
    var robot: Robot? = null

    // throws InterruptedException
    override fun runOpMode() {
        telemetry = MultipleTelemetry(telemetry, FtcDashboard.getInstance().telemetry)
        robot = Robot(this)
        robot!!.camera.initCamera()

        telemetry.let {
            it.addData("Status", "Initialized")
            it.update()
        }

        while (!isStarted && !isStopRequested) {
            robot!!.lift.resetEncoder()
            tag = robot!!.camera.findTag()
            telemetry.let {
                it.addData("Camera", "AprilTag: $tag")
                it.update()
            }
        }
        // Wait for the game to start (driver presses PLAY)
        waitForStart()

        robot!!.manipulator.setPos(0.21)

        when (tag) {
            1 -> {
                robot!!.wheelBase.moveEncoder(firstForward1, 0.0, 0.0, 1.0)
                sleep(2000)
                robot!!.wheelBase.moveEncoder(0.0, 0.0, secondTurn1, 1.0)
                sleep(2000)
                robot!!.wheelBase.moveEncoder(thirdForward1, 0.0, 0.0, 1.0)
            }
            2 -> {
                robot!!.wheelBase.moveEncoder(firstForward2, 0.0, 0.0, 0.5)
            }
            3 -> {
                robot!!.wheelBase.moveEncoder(firstForward3, 0.0, 0.0, 1.0)
                sleep(2000)
                robot!!.wheelBase.moveEncoder(0.0, 0.0, secondTurn3, 1.0)
                sleep(2000)
                robot!!.wheelBase.moveEncoder(thirdForward3, 0.0, 0.0, 1.0)
            }
            else -> {
                robot!!.wheelBase.moveEncoder(firstForward2, 0.0, 0.0, 0.5)
            }
        }

        runtime.reset()
    }
}