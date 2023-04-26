package org.firstinspires.ftc.teamcode.robot

import com.acmerobotics.dashboard.config.Config
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorSimple
import org.firstinspires.ftc.teamcode.robot.enums.LiftPosition

@Config
class Lift(var robot: Robot) {
    companion object {
        @JvmField
        var liftGravityConst = 0.09
    }
    // TODO Проверить значения
    private val spoolRadiusMm = 25.0
    private val spoolCircumference = spoolRadiusMm * 2.0 * Math.PI
    private val encoderTicksPerRevNoGearbox = 24.0
    private val gearboxRatio = 40.0
    private val encoderTicksPerRevGearbox = encoderTicksPerRevNoGearbox * gearboxRatio
    private val liftMmToEncoderRatio = encoderTicksPerRevGearbox / spoolCircumference

    // Declare each motor in lift.
    private var leftLiftDrive: DcMotor = robot.linearOpMode.hardwareMap.get(DcMotor::class.java, "left_lift_drive")
    private var rightLiftDrive: DcMotor = robot.linearOpMode.hardwareMap.get(DcMotor::class.java, "right_lift_drive")

    init {
        // Initialize the hardware variables. Note that the strings used here must correspond
        // to the names assigned during the robot configuration step on the DS or RC devices.
        // Most robots need the motors on one side to be reversed to drive forward.
        leftLiftDrive.direction = DcMotorSimple.Direction.REVERSE
        rightLiftDrive.direction = DcMotorSimple.Direction.FORWARD
        leftLiftDrive.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.BRAKE
        rightLiftDrive.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.BRAKE
    }

    fun liftEncoderToMm(encoder: Double): Double {
        return encoder / liftMmToEncoderRatio
    }
    fun liftMmToEncoder(mm: Double): Double {
        return mm * liftMmToEncoderRatio
    }

    private fun encoderPowerLimitLeft(power: Double): Double {
        return if (power < 0 && (leftLiftDrive.currentPosition <= LiftPosition.BOTTOM.encoderValue))
            0.0
        else if (power > 0 && (leftLiftDrive.currentPosition >= LiftPosition.UP_JUNCTION.encoderValue))
            0.0
        else
            power
    }
    private fun encoderPowerLimitRight(power: Double): Double {
        return if (power < 0 && (rightLiftDrive.currentPosition <= LiftPosition.BOTTOM.encoderValue))
            0.0
        else if (power > 0 && (rightLiftDrive.currentPosition >= LiftPosition.UP_JUNCTION.encoderValue))
            0.0
        else
            power
    }


    fun setPowerRaw(power: Double) {
        leftLiftDrive.power = power + liftGravityConst
        rightLiftDrive.power = power + liftGravityConst
    }

    fun setPower(power: Double) {
        leftLiftDrive.power = encoderPowerLimitLeft(power) + liftGravityConst
        rightLiftDrive.power = encoderPowerLimitRight(power) + liftGravityConst
    }

    fun getEncoder(): Array<Int> {
        return arrayOf(rightLiftDrive.currentPosition, leftLiftDrive.currentPosition)
    }

    fun resetEncoder() {
        leftLiftDrive.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER
        leftLiftDrive.mode = DcMotor.RunMode.RUN_WITHOUT_ENCODER

        rightLiftDrive.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER
        rightLiftDrive.mode = DcMotor.RunMode.RUN_WITHOUT_ENCODER

    }
}