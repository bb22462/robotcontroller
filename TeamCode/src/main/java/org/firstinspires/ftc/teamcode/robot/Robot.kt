package org.firstinspires.ftc.teamcode.robot

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode

class Robot(var linearOpMode: LinearOpMode) {
    @JvmField
    var camera: Camera = Camera(this)

    @JvmField
    var wheelBase: WheelBase = WheelBase(this)

    @JvmField
    var manipulator: Manipulator = Manipulator(this)

    @JvmField
    var lift: Lift = Lift(this)

}