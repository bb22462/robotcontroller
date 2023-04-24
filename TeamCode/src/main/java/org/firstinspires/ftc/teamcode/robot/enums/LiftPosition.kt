package org.firstinspires.ftc.teamcode.robot.enums

enum class LiftPosition(val encoderValue: Int) {
    // TODO Изменить значения на нужные
    BOTTOM(0),
    GROUND_JUNCTION(100),
    LOW_JUNCTION(200),
    MIDDLE_JUNCTION(400),
    UP_JUNCTION(500)
}