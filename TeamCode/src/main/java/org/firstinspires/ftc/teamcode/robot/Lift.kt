package org.firstinspires.ftc.teamcode.robot

import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorSimple

class Lift(var robot: Robot) {
    companion object {
        @JvmField
        var bottom = 0;
        @JvmField
        var up = 1800;
    }
    private val spoolRadiusMm = 25.0
    private val spoolCircumference = spoolRadiusMm * 2.0 * Math.PI
    private val encoderTicksPerRevNoGearbox = 24.0
    private val gearboxRatio = 40.0
    private val encoderTicksPerRevGearbox = encoderTicksPerRevNoGearbox * gearboxRatio
    private val liftMmToEncoderRatio = encoderTicksPerRevGearbox / spoolCircumference
    // Declare each motor in lift.
    public var leftLiftDrive: DcMotor = robot.linearOpMode.hardwareMap.get(DcMotor::class.java, "left_lift_drive")
    public var rightLiftDrive: DcMotor = robot.linearOpMode.hardwareMap.get(DcMotor::class.java, "right_lift_drive")
    public var leftHangDrive: DcMotor = robot.linearOpMode.hardwareMap.get(DcMotor::class.java, "left_hang_drive")
    public var rightHangDrive: DcMotor = robot.linearOpMode.hardwareMap.get(DcMotor::class.java, "right_Hang_drive")

    init {
        // Initialize the hardware variables. Note that the strings used here must correspond
        // to the names assigned during the robot configuration step on the DS or RC devices.
        // Most robots need the motors on one side to be reversed to drive forward.
        leftLiftDrive.direction = DcMotorSimple.Direction.REVERSE
        rightLiftDrive.direction = DcMotorSimple.Direction.FORWARD
        leftLiftDrive.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.BRAKE
        rightLiftDrive.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.BRAKE
    }

    fun setPowerRaw(power: Double) {
        leftLiftDrive.power = power
        rightLiftDrive.power = power
    }
    fun resetEncoder() {
        leftLiftDrive.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER
        leftLiftDrive.mode = DcMotor.RunMode.RUN_WITHOUT_ENCODER

        rightLiftDrive.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER
        rightLiftDrive.mode = DcMotor.RunMode.RUN_WITHOUT_ENCODER

    }
    private fun encoderPowerLimitLeft(power: Double): Double {
        return if (power < 0 && (leftLiftDrive.currentPosition <= bottom))
            0.0
        else if (power > 0 && (leftLiftDrive.currentPosition >= up))
            0.0
        else
            power
    }
    private fun encoderPowerLimitRight(power: Double): Double {
        return if (power < 0 && (rightLiftDrive.currentPosition <= bottom))
            0.0
        else if (power > 0 && (rightLiftDrive.currentPosition >= up))
            0.0
        else
            power
    }
    fun setPower(power: Double) {
        leftLiftDrive.power = encoderPowerLimitLeft(power)
        rightLiftDrive.power = encoderPowerLimitRight(power)
    }

    fun setPowerRawHang(power: Double) {
        leftHangDrive.power = power
        rightHangDrive.power = power
    }
}