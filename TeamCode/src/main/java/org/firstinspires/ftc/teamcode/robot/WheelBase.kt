package org.firstinspires.ftc.teamcode.robot

import com.acmerobotics.dashboard.config.Config
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorSimple
import com.qualcomm.robotcore.hardware.IMU
import com.qualcomm.robotcore.util.ElapsedTime
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference
import kotlin.math.abs
import kotlin.math.sign


@Config
class WheelBase(var robot: Robot) {
    companion object {
        @JvmField
        var forwardK = 0.07
        @JvmField
        var sideK = 0.12
        @JvmField
        var angleK = 0.06
        @JvmField
        var forwardKD = 0.02
        @JvmField
        var sideKD = 0.12
        @JvmField
        var angleKD = 0.02

    }
    // Declare each motor in drivetrain
    @JvmField
    public var imu: IMU = robot.linearOpMode.hardwareMap.get<IMU>(IMU::class.java, "imu")
    var leftFrontDrive: DcMotor = robot.linearOpMode.hardwareMap.get(DcMotor::class.java, "left_front_drive")
    var rightFrontDrive: DcMotor = robot.linearOpMode.hardwareMap.get(DcMotor::class.java, "right_front_drive")
    var leftBackDrive: DcMotor = robot.linearOpMode.hardwareMap.get(DcMotor::class.java, "left_back_drive")
    var rightBackDrive: DcMotor = robot.linearOpMode.hardwareMap.get(DcMotor::class.java, "right_back_drive")
    public var leftLight: DcMotor = robot.linearOpMode.hardwareMap.get(DcMotor::class.java, "left_light")
    public var rightLight: DcMotor = robot.linearOpMode.hardwareMap.get(DcMotor::class.java, "right_light")
    private val wheelRadius = 4.917
    @JvmField
    public var IMUparameters: IMU.Parameters? = null
    private val wheelLength = wheelRadius * 2 * Math.PI
    private val cmToEncoder = 480 / wheelLength
    var forwardDistance: Double = 0.0
    var sideDistance: Double = 0.0
    var angleDistance: Double = 0.0
    var forwardError: Double = 0.0
    var sideError: Double = 0.0
    var angleError: Double = 0.0
    var old_forwardError: Double = 0.0
    var old_sideError: Double = 0.0
    var old_angleError: Double = 0.0
    private var _deltaTime = ElapsedTime()

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

        IMUparameters = IMU.Parameters(
                RevHubOrientationOnRobot(
                        RevHubOrientationOnRobot.LogoFacingDirection.LEFT,
                        RevHubOrientationOnRobot.UsbFacingDirection.UP
                )
        )

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

    fun moveEncoderPD(cmForward: Double, cmSide: Double, Angle: Double, power: Double) {
        start()
        resetEncoder()
        do {
            forwardDistance = (leftBackDrive.currentPosition + rightBackDrive.currentPosition + rightFrontDrive.currentPosition + leftFrontDrive.currentPosition) / 4.0 / cmToEncoder
            sideDistance = (leftFrontDrive.currentPosition - leftBackDrive.currentPosition - rightFrontDrive.currentPosition + rightBackDrive.currentPosition) / 4.0 / cmToEncoder
            angleDistance = getGyroAngle()
            forwardError = cmForward - forwardDistance
            sideError = cmSide - sideDistance
            angleError = Angle - angleDistance
            var forwardD = (forwardError - old_forwardError) * forwardKD  * _deltaTime.seconds()
            var sideD = (sideError - old_sideError) * sideKD  * _deltaTime.seconds()
            var angleD = (angleError - old_angleError) * angleKD  * _deltaTime.seconds()
            while (abs(angleError) > 180)
                angleError -= angleError.sign * 360

            robot!!.linearOpMode.telemetry.let {
                it.addData("sideError", sideError)
                it.addData("forwardError", forwardError)
                it.addData("angleError", angleError)
                it.update()
            }


            move(
                    (forwardError * forwardK * power) + (forwardD * power), (sideError * sideK * power) + (sideD * power), (angleError * angleK * power) + (angleD * power)
            )

        } while((abs(sideError) > 0.5 || abs(forwardError) > 0.5 || abs(angleError) > 1) && robot.linearOpMode.opModeIsActive())
        old_sideError = sideError
        old_forwardError = forwardError
        old_angleError = angleError
        _deltaTime.reset()
    }
    fun moveEncoder(cmForward: Double, cmSide: Double, Angle: Double, power: Double) {
        resetEncoder()
        do {
            forwardDistance = (leftBackDrive.currentPosition + rightBackDrive.currentPosition + rightFrontDrive.currentPosition + leftFrontDrive.currentPosition) / 4.0 / cmToEncoder
            sideDistance = (leftFrontDrive.currentPosition - leftBackDrive.currentPosition - rightFrontDrive.currentPosition + rightBackDrive.currentPosition) / 4.0 / cmToEncoder
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

        } while((abs(sideError) > 0.5 || abs(forwardError) > 0.5 || abs(angleError) > 1) && robot.linearOpMode.opModeIsActive())
    }


    public fun getGyroAngle(): Double {
        return -imu.getRobotOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES).firstAngle.toDouble()
    }


    public fun setPowerAll(leftFrontPower: Double, rightFrontPower: Double, leftBackPower: Double, rightBackPower: Double) {
        leftFrontDrive.power = leftFrontPower
        rightFrontDrive.power = rightFrontPower
        leftBackDrive.power = leftBackPower
        rightBackDrive.power = rightBackPower
    }
    fun start() {
        _deltaTime.reset()
    }
}