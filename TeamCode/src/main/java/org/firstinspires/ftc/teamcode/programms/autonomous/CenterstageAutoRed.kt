package org.firstinspires.ftc.teamcode.programms.autonomous

import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.util.ElapsedTime
import org.firstinspires.ftc.teamcode.robot.Robot


@Autonomous(name = "Red Alliance Autonomous")
class CenterstageAutoRed : LinearOpMode() {
        private var tag = 0
        private val runtime = ElapsedTime()
        var robot: Robot? = null

        // throws InterruptedException
        override fun runOpMode() {

                robot = Robot(this)
                robot!!.camera.initCamera(1)

                telemetry.let {
                        it.addData("Status", "Initialized")
                        it.update()
                }

                while (!isStarted && !isStopRequested) {
                        robot!!.lift.resetEncoder()
                        tag = robot!!.camera.findRed()
                        telemetry.let {
                                it.addData("Camera", "Location: $tag")
                                it.update()
                        }
                }
                // Wait for the game to start (driver presses PLAY)
                waitForStart()



                runtime.reset()
        }
}