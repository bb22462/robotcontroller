package org.firstinspires.ftc.teamcode.robot

import com.qualcomm.robotcore.hardware.Servo

class Manipulator(var robot: Robot) {
    @JvmField
    var ManipulatorServoLeft: Servo = robot.linearOpMode.hardwareMap.get(Servo::class.java, "left_manipulator_servo")
    @JvmField
    var ManipulatorServoRight: Servo = robot.linearOpMode.hardwareMap.get(Servo::class.java, "right_manipulator_servo")
    @JvmField
    var MoveServo: Servo = robot.linearOpMode.hardwareMap.get(Servo::class.java, "move_servo")
    @JvmField
    var Samolet: Servo = robot.linearOpMode.hardwareMap.get(Servo::class.java, "samolet")
    @JvmField
    var LeftHangServo: Servo = robot.linearOpMode.hardwareMap.get(Servo::class.java, "left_hang_servo")
    @JvmField
    var RightHangServo: Servo = robot.linearOpMode.hardwareMap.get(Servo::class.java, "right_hang_servo")

    @JvmField
    val LeftmaxPos = 0.5
    @JvmField
    val LeftminPos = 0.2
    @JvmField
    val RightmaxPos = 0.5
    @JvmField
    val RightminPos = 0.2
    @JvmField
    val increment = 0.1;
    @JvmField
    val openPos = 0.1;




    fun setPosAll(posl: Double, posr: Double) {
        ManipulatorServoLeft.direction = Servo.Direction.FORWARD
        ManipulatorServoRight.direction = Servo.Direction.REVERSE
        ManipulatorServoLeft.position = posl
        ManipulatorServoRight.position = posr
    }
    fun setPos(pos: Double) {
        ManipulatorServoLeft.position = pos
        ManipulatorServoRight.position = pos
    }
    fun moveSetPos(pos: Double) {
        MoveServo.position = pos
    }
    fun moveSamolet(pos: Double) {
        Samolet.position = pos
    }
}