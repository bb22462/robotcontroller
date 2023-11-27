package org.firstinspires.ftc.teamcode.robot

import com.qualcomm.robotcore.hardware.AnalogSensor

class IRSensor(var robot: Robot) {
    @JvmField
    var sensor: AnalogSensor = robot.linearOpMode.hardwareMap.get(AnalogSensor::class.java, "irsensor");

    fun getSensor(): Double {
        return sensor.readRawVoltage()
    }

}