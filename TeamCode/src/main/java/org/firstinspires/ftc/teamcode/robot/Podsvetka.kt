package org.firstinspires.ftc.teamcode.robot

import com.acmerobotics.dashboard.config.Config
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorSimple
import org.firstinspires.ftc.teamcode.robot.enums.LiftPosition

@Config
class Podsvetka(var robot: Robot) {

    // Declare each motor in lift.
    private var front: DcMotor = robot.linearOpMode.hardwareMap.get(DcMotor::class.java, "front_podsvetka")
    private var back: DcMotor = robot.linearOpMode.hardwareMap.get(DcMotor::class.java, "back_podsvetka")

    fun setPowerFront(power: Double) {
        front.power = power
    }
    fun setPowerBack(power: Double) {
        back.power = power
    }
    fun setPowerAll(power: Double) {
        front.power = power
        back.power = power
    }


}