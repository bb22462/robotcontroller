package org.firstinspires.ftc.teamcode.robot

import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorSimple
import org.firstinspires.ftc.teamcode.robot.enums.LiftPosition

class Lift(var robot: Robot) {
    // TODO Проверить значения
    private val spoolRadiusMm = 25.0
    private val spoolCircumference = spoolRadiusMm * 2.0 * Math.PI
    private val encoderTicksPerRevNoGearbox = 24.0
    private val gearboxRatio = 40.0
    private val encoderTicksPerRevGearbox = encoderTicksPerRevNoGearbox * gearboxRatio
    private val liftMmToEncoderRatio = encoderTicksPerRevGearbox / spoolCircumference
    private val liftGravityConst = 0.164

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

    fun liftEncoderToMm(encoder: Double): Double {
        return encoder / liftMmToEncoderRatio
    }
    fun liftMmToEncoder(mm: Double): Double {
        return mm * liftMmToEncoderRatio
    }

    private fun encoderPowerLimit(power: Double): Double {
        return if (power < 0 && (leftLiftDrive.currentPosition <= LiftPosition.BOTTOM.encoderValue && rightLiftDrive.currentPosition <= LiftPosition.BOTTOM.encoderValue))
            0.0
        else if (power > 0 && (leftLiftDrive.currentPosition <= LiftPosition.UP_JUNCTION.encoderValue && rightLiftDrive.currentPosition <= LiftPosition.UP_JUNCTION.encoderValue))
            0.0
        else
            power
    }

    private fun setPowerRaw(power: Double) {
        leftLiftDrive.power = power
        rightLiftDrive.power = power
    }

    fun setPower(power: Double) {
        leftLiftDrive.power = encoderPowerLimit(power) + liftGravityConst
        rightLiftDrive.power = encoderPowerLimit(power) + liftGravityConst
    }
}