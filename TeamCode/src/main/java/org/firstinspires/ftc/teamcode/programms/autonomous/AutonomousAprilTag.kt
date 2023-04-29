package org.firstinspires.ftc.teamcode.programms.autonomous

import com.acmerobotics.dashboard.FtcDashboard
import com.acmerobotics.dashboard.config.Config
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry
import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.util.ElapsedTime
import org.firstinspires.ftc.teamcode.robot.Robot

@Config
@Autonomous(name = "Autonomous April Tag")
class AutonomousAprilTag : LinearOpMode() {
    companion object {
        @JvmField
        var forward1_1 = 52.0
        @JvmField
        var forward2_1 = 43.0

        @JvmField
        var firstForward2 = 80.0

        @JvmField
        var forward1_3 = 52.0
        @JvmField
        var forward2_3 = 43.0



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
                robot!!.wheelBase.moveEncoder(forward1_1, 0.0, 0.0, 1.0)
                sleep(2000)
                robot!!.wheelBase.moveEncoder(0.0, 0.0, -90.0, 1.0)
                sleep(2000)
                robot!!.wheelBase.moveEncoder(forward2_1, 0.0, -90.0, 1.0)
                sleep(2000)
            }
            2 -> {
                robot!!.wheelBase.moveEncoder(firstForward2, 0.0, 0.0, 0.5)
            }
            3 -> {
                robot!!.wheelBase.moveEncoder(forward1_3, 0.0, 0.0, 1.0)
                sleep(2000)
                robot!!.wheelBase.moveEncoder(0.0, 0.0, 90.0, 1.0)
                sleep(2000)
                robot!!.wheelBase.moveEncoder(forward2_3, 0.0, 90.0, 1.0)
                sleep(2000)
            }
            else -> {
                robot!!.wheelBase.moveEncoder(firstForward2, 0.0, 0.0, 0.5)
            }
        }

        runtime.reset()
    }
}