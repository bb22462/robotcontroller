package org.firstinspires.ftc.teamcode.robot

import com.qualcomm.robotcore.hardware.AnalogInput
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorSimple

class Sensor(var robot: Robot) {
    companion object {
        @JvmField
        var pixel = 0;
    }
    public var sensorRight: AnalogInput = robot.linearOpMode.hardwareMap.get(AnalogInput::class.java, "sensor_right")
    public var sensorLeft: AnalogInput = robot.linearOpMode.hardwareMap.get(AnalogInput::class.java, "sensor_left")
}