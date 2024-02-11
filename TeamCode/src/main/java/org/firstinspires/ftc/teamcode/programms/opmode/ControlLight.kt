package org.firstinspires.ftc.teamcode.programms.opmode

import com.acmerobotics.dashboard.FtcDashboard
import com.acmerobotics.dashboard.config.Config
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.util.ElapsedTime
import org.firstinspires.ftc.teamcode.robot.Robot
import kotlin.math.abs
import kotlin.math.max


// 1) Axial:    Driving forward and backward               Left-joystick Forward/Backward
// 2) Lateral:  Strafing right and left                     Left-joystick Right and Left
// 3) Yaw:      Rotating Clockwise and counter clockwise    Right-joystick Right and Left
@Config
@TeleOp(name = "Gamepad Podsvetka", group = "Linear OpMode")
class ControlLight : LinearOpMode() {
    companion object {
        @JvmField
        var pixel = 0;
    }

    //Companion object with variables for dashboard
    private val runtime = ElapsedTime();
    var robot: Robot? = null

    override fun runOpMode() {
        // Re-translate telemetry to dashboard
        telemetry = MultipleTelemetry(telemetry, FtcDashboard.getInstance().telemetry)

        robot = Robot(this)

        // Add the initialization status to the telemetry
        telemetry.let {
            it.addData("Status", "Initialized")
            it.update()
        }
        // Wait for the game to start (driver presses PLAY)
        robot!!.lift.resetEncoder();
        waitForStart()
        runtime.reset()

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            robot!!.wheelBase.leftLight.power = gamepad1.left_stick_y.toDouble();
            robot!!.wheelBase.rightLight.power = gamepad1.right_stick_y.toDouble();

            if(robot!!.sensor.sensorRight.voltage < pixel) {
                robot!!.wheelBase.rightLight.power = 1.0
            }
            else {
                robot!!.wheelBase.rightLight.power = 0.0
            }

            if(robot!!.sensor.sensorLeft.voltage < pixel) {
                robot!!.wheelBase.leftLight.power = 1.0
            }
            else {
                robot!!.wheelBase.leftLight.power = 0.0
            }

            telemetry.let {
                it.addData("Sensor Right", "Light: ${robot!!.sensor.sensorRight.voltage}")
                it.addData("Sensor Left", "Light: ${robot!!.sensor.sensorLeft.voltage}")
                it.update()
            }
        }
    }
}