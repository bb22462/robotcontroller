package org.firstinspires.ftc.teamcode.robot

import com.qualcomm.robotcore.hardware.Servo

class Manipulator(var robot: Robot) {
    @JvmField
    var ManipulatorServo: Servo = robot.linearOpMode.hardwareMap.get(Servo::class.java, "manipulator_servo")
    var MoveServo: Servo = robot.linearOpMode.hardwareMap.get(Servo::class.java, "move_servo")

    @JvmField
    val maxPos = 0.9
    @JvmField
    val minPos = 0.0
    @JvmField
    val increment = 0.1;


    fun setPos(pos: Double) {
        ManipulatorServo.position = pos
    }
    fun moveSetPos(pos: Double) {
        MoveServo.position = pos
    }
    fun increment() {
        MoveServo.position = 0.3;
    }
    fun decrement() {
        MoveServo.position = 0.0
    }
}