package org.firstinspires.ftc.teamcode.robot

import com.qualcomm.robotcore.hardware.Servo

class Manipulator(var robot: Robot) {
    @JvmField
    var manipulatorServo: Servo = robot.linearOpMode.hardwareMap.get(Servo::class.java, "manipulator_servo")

    @JvmField
    val maxPos = 1.0
    @JvmField
    val minPos = 0.0


    fun setPos(pos: Double) {
        manipulatorServo.position = pos
    }

    fun getPos(): Double {
        return manipulatorServo.position
    }
}