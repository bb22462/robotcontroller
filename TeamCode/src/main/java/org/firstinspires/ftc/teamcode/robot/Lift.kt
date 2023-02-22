package org.firstinspires.ftc.teamcode.robot

import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorSimple

class Lift(var robot: Robot) {
    // Declare each motor in lift.
    private var leftLiftDrive: DcMotor = robot.linearOpMode.hardwareMap.get(DcMotor::class.java, "left_lift_drive")
    private var rightLiftDrive: DcMotor = robot.linearOpMode.hardwareMap.get(DcMotor::class.java, "right_lift_drive")

    init {
        // Initialize the hardware variables. Note that the strings used here must correspond
        // to the names assigned during the robot configuration step on the DS or RC devices.
        // Most robots need the motors on one side to be reversed to drive forward.
        leftLiftDrive.direction = DcMotorSimple.Direction.FORWARD
        rightLiftDrive.direction = DcMotorSimple.Direction.REVERSE
        leftLiftDrive.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.BRAKE
        rightLiftDrive.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.BRAKE
    }

    fun setPower(power: Double) {
        leftLiftDrive.power = power
        rightLiftDrive.power = power
    }
}