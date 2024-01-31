package org.firstinspires.ftc.teamcode.robot

import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorSimple

class Lift(var robot: Robot) {
    companion object {
        @JvmField
        var bottom = 0;
        @JvmField
        var up = -1500;
    }
    private val spoolRadiusMm = 25.0
    private val spoolCircumference = spoolRadiusMm * 2.0 * Math.PI
    private val encoderTicksPerRevNoGearbox = 24.0
    private val gearboxRatio = 40.0
    private val encoderTicksPerRevGearbox = encoderTicksPerRevNoGearbox * gearboxRatio
    private val liftMmToEncoderRatio = encoderTicksPerRevGearbox / spoolCircumference
    // Declare each motor in lift.
    public var lift: DcMotor = robot.linearOpMode.hardwareMap.get(DcMotor::class.java, "lift_drive")

    init {
        // Initialize the hardware variables. Note that the strings used here must correspond
        // to the names assigned during the robot configuration step on the DS or RC devices.
        // Most robots need the motors on one side to be reversed to drive forward.
        lift.direction = DcMotorSimple.Direction.REVERSE
        lift.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.BRAKE
    }

    fun setPowerRaw(power: Double) {
        lift.power = power
    }
    fun resetEncoder() {
        lift.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER
        lift.mode = DcMotor.RunMode.RUN_WITHOUT_ENCODER
    }
    private fun encoderPowerLimit(power: Double): Double {
        return if(power < 0 && (lift.currentPosition >= bottom)) {
            0.0
        }
        else if (power > 0 && (lift.currentPosition <= up)) {
            0.0
        }
        else {
            power
        }
    }
    fun setPower(power: Double) {
        lift.power = encoderPowerLimit(power)
    }
}