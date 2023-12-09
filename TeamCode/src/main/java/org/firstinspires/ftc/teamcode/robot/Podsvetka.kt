package org.firstinspires.ftc.teamcode.robot

import com.acmerobotics.dashboard.config.Config
import com.qualcomm.hardware.bosch.BNO055IMU
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorSimple
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference
import kotlin.math.abs
import kotlin.math.sign

@Config
class Podsvetka(var robot: Robot) {

    var podvetka: DcMotor = robot.linearOpMode.hardwareMap.get(DcMotor::class.java, "podsvetka")

    public fun setPower(power: Double) {
        podvetka.power = power
    }

}