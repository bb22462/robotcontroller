package org.firstinspires.ftc.teamcode.programms.opmode

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.util.ElapsedTime
import org.firstinspires.ftc.teamcode.robot.Robot
import kotlin.math.abs
import kotlin.math.max
import com.acmerobotics.dashboard.FtcDashboard
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry
import com.acmerobotics.dashboard.config.Config


@Config
@TeleOp
class Podsvetka : LinearOpMode() {
    private val runtime = ElapsedTime();
    var robot: Robot? = null
    override fun runOpMode() {
        // Add the initialization status to the telemetry
        telemetry = MultipleTelemetry(telemetry, FtcDashboard.getInstance().telemetry)
        robot = Robot(this)
        var isOpen = true

        // Wait for the game to start (driver presses PLAY)
        waitForStart()
        runtime.reset()

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            robot!!.podsvetka.setPowerAll(0.2)
        }
    }
}