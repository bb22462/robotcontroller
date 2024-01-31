package org.firstinspires.ftc.teamcode.robot

import com.qualcomm.robotcore.hardware.Servo

class Manipulator(var robot: Robot) {
    @JvmField
    var ManipulatorServo: Servo = robot.linearOpMode.hardwareMap.get(Servo::class.java, "manipulator_servo")
    @JvmField
    var MoveServo: Servo = robot.linearOpMode.hardwareMap.get(Servo::class.java, "move_servo")
    @JvmField
    var Samolet: Servo = robot.linearOpMode.hardwareMap.get(Servo::class.java, "samolet")
    @JvmField
    var LeftHangServo: Servo = robot.linearOpMode.hardwareMap.get(Servo::class.java, "left_hang_servo")
    @JvmField
    var RightHangServo: Servo = robot.linearOpMode.hardwareMap.get(Servo::class.java, "right_hang_servo")

    @JvmField
    val maxPos = 0.5
    @JvmField
    val minPos = 0.2
    @JvmField
    val increment = 0.1;




    fun setPos(pos: Double) {
        ManipulatorServo.position = pos
    }
    fun moveSetPos(pos: Double) {
        MoveServo.position = pos
    }
    fun moveSamolet(pos: Double) {
        Samolet.position = pos
    }
}