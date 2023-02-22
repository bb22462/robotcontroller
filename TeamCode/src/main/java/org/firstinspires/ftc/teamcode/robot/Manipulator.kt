package org.firstinspires.ftc.teamcode.robot

import com.qualcomm.robotcore.hardware.Servo

class Manipulator(var robot: Robot) {
    @JvmField
    var leftManipulatorServo: Servo = robot.linearOpMode.hardwareMap.get(Servo::class.java, "left_manipulator_servo")

    @JvmField
    var rightManipulatorServo: Servo = robot.linearOpMode.hardwareMap.get(Servo::class.java, "right_manipulator_servo")

    @JvmField
    val maxPos = 1.0
    @JvmField
    val minPos = 0.0

    init {
        rightManipulatorServo.direction = Servo.Direction.FORWARD
        leftManipulatorServo.direction = Servo.Direction.REVERSE
    }

    fun setPos(pos: Double) {
        leftManipulatorServo.position = pos
        rightManipulatorServo.position = pos
    }
}