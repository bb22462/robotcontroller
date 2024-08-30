package org.firstinspires.ftc.teamcode.robot;

import com.qualcomm.robotcore.hardware.AnalogInput;

public class Sensor {
    public AnalogInput sensorRight;
    public AnalogInput sensorLeft;
    Robot robot;

    public Sensor(Robot robot) {
        this.robot = robot;

        this.sensorRight = robot.linearOpMode.hardwareMap.get(AnalogInput.class, "sensor_right");
        this.sensorLeft = robot.linearOpMode.hardwareMap.get(AnalogInput.class, "left");
    }

}