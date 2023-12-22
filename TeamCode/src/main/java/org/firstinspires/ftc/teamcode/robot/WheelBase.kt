package org.firstinspires.ftc.teamcode.robot

import com.acmerobotics.dashboard.config.Config
import com.qualcomm.hardware.bosch.BNO055IMU
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorSimple
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference
import kotlin.math.abs
import kotlin.math.sign

@Config
class WheelBase(var robot: Robot) {
    companion object {
        @JvmField
        var forwardK = 0.036
        @JvmField
        var sideK = 0.12
        @JvmField
        var angleK = 0.03
    }
    // Declare each motor in drivetrain
    private var imu: BNO055IMU = robot.linearOpMode.hardwareMap.get<BNO055IMU>(BNO055IMU::class.java, "imu")
    var leftFrontDrive: DcMotor = robot.linearOpMode.hardwareMap.get(DcMotor::class.java, "left_front_drive")
    var rightFrontDrive: DcMotor = robot.linearOpMode.hardwareMap.get(DcMotor::class.java, "right_front_drive")
    var leftBackDrive: DcMotor = robot.linearOpMode.hardwareMap.get(DcMotor::class.java, "left_back_drive")
    var rightBackDrive: DcMotor = robot.linearOpMode.hardwareMap.get(DcMotor::class.java, "right_back_drive")
    private val wheelRadius = 4.917
    private val wheelLength = wheelRadius * 2 * Math.PI
    private val cmToEncoder = 480 / wheelLength
    var forwardDistance: Double = 0.0
    var sideDistance: Double = 0.0
    @JvmField
    var forwardError: Double = 0.0
    @JvmField
    var sideError: Double = 0.0
    var angleDistance: Double = 0.0
    @JvmField
    var angleError: Double = 0.0

    init {
        // Initialize the hardware variables. Note that the strings used here must correspond
        // to the names assigned during the robot configuration step on the DS or RC devices.
        // Motor 3
        // Motor 2
        // Motor 0
        // Motor 1

        // Most robots need the motors on one side to be reversed to drive forward.
        leftFrontDrive.direction = DcMotorSimple.Direction.FORWARD
        rightFrontDrive.direction = DcMotorSimple.Direction.REVERSE
        leftBackDrive.direction = DcMotorSimple.Direction.FORWARD
        rightBackDrive.direction = DcMotorSimple.Direction.REVERSE

        leftFrontDrive.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.BRAKE
        rightFrontDrive.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.BRAKE
        leftBackDrive.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.BRAKE
        rightBackDrive.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.BRAKE

        leftFrontDrive.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER
        leftFrontDrive.mode = DcMotor.RunMode.RUN_WITHOUT_ENCODER

        leftBackDrive.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER
        leftBackDrive.mode = DcMotor.RunMode.RUN_WITHOUT_ENCODER

        rightFrontDrive.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER
        rightFrontDrive.mode = DcMotor.RunMode.RUN_WITHOUT_ENCODER

        rightBackDrive.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER
        rightBackDrive.mode = DcMotor.RunMode.RUN_WITHOUT_ENCODER

        imu.initialize(BNO055IMU.Parameters())
    }

    /* Directions:
    * 1 - Forward
    * -1 - Backwards
    *
    * Side:
    * 1 - Right
    * -1 - Left */
    private fun resetEncoder() {
        leftFrontDrive.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER
        leftFrontDrive.mode = DcMotor.RunMode.RUN_WITHOUT_ENCODER

        leftBackDrive.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER
        leftBackDrive.mode = DcMotor.RunMode.RUN_WITHOUT_ENCODER

        rightFrontDrive.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER
        rightFrontDrive.mode = DcMotor.RunMode.RUN_WITHOUT_ENCODER

        rightBackDrive.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER
        rightBackDrive.mode = DcMotor.RunMode.RUN_WITHOUT_ENCODER
    }

    public fun move(direction: Double, side: Double, rotation: Double) {
        leftFrontDrive.power = direction + side + rotation
        rightFrontDrive.power = direction - side - rotation
        leftBackDrive.power = direction - side + rotation
        rightBackDrive.power = direction + side - rotation
    }

    fun moveEncoder(cmForward: Double, cmSide: Double, Angle: Double, power: Double) {
        resetEncoder()
        do {
            forwardDistance = (leftBackDrive.currentPosition + rightBackDrive.currentPosition) / 2.0 / cmToEncoder
            sideDistance = (leftFrontDrive.currentPosition - leftBackDrive.currentPosition) / 2.0 / cmToEncoder
            angleDistance = getGyroAngle()
            forwardError = cmForward - forwardDistance
            sideError = cmSide - sideDistance
            angleError = Angle - angleDistance
            while (abs(angleError) > 180)
                angleError -= angleError.sign * 360

            robot!!.linearOpMode.telemetry.let {
                it.addData("sideError", sideError)
                it.addData("forwardError", forwardError)
                it.addData("angleError", angleError)
                it.update()
            }


            move(
                    forwardError * power * forwardK, sideError * power * sideK, angleError * power * angleK
            )

        } while((abs(sideError) > 3|| abs(forwardError) > 3 || abs(angleError) > 3) && robot.linearOpMode.opModeIsActive())

    }


    public fun getGyroAngle(): Double {
        return -imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES).firstAngle.toDouble()
    }


    public fun setPowerAll(leftFrontPower: Double, rightFrontPower: Double, leftBackPower: Double, rightBackPower: Double) {
        leftFrontDrive.power = leftFrontPower
        rightFrontDrive.power = rightFrontPower
        leftBackDrive.power = leftBackPower
        rightBackDrive.power = rightBackPower
    }
}