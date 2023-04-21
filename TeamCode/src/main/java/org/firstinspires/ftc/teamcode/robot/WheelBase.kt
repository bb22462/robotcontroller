package org.firstinspires.ftc.teamcode.robot

import com.qualcomm.hardware.bosch.BNO055IMU
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorSimple
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference
import kotlin.math.abs
import kotlin.math.sign


class WheelBase(var robot: Robot) {
    // Declare each motor in drivetrain
    private var imu: BNO055IMU = robot.linearOpMode.hardwareMap.get<BNO055IMU>(BNO055IMU::class.java, "imu")
    private var leftFrontDrive: DcMotor = robot.linearOpMode.hardwareMap.get(DcMotor::class.java, "left_front_drive")
    private var rightFrontDrive: DcMotor = robot.linearOpMode.hardwareMap.get(DcMotor::class.java, "right_front_drive")
    private var leftBackDrive: DcMotor = robot.linearOpMode.hardwareMap.get(DcMotor::class.java, "left_back_drive")
    private var rightBackDrive: DcMotor = robot.linearOpMode.hardwareMap.get(DcMotor::class.java, "right_back_drive")
    private val wheelRadius = 4.9
    private val wheelLength = wheelRadius * 2 * Math.PI
    private val cmToEncoder = 480 / wheelLength

    init {
        // Initialize the hardware variables. Note that the strings used here must correspond
        // to the names assigned during the robot configuration step on the DS or RC devices.
        // Motor 3
        // Motor 2
        // Motor 0
        // Motor 1

        // Most robots need the motors on one side to be reversed to drive forward.
        leftFrontDrive.direction = DcMotorSimple.Direction.REVERSE
        rightFrontDrive.direction = DcMotorSimple.Direction.FORWARD
        leftBackDrive.direction = DcMotorSimple.Direction.REVERSE
        rightBackDrive.direction = DcMotorSimple.Direction.FORWARD

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

    fun move(direction: Double, side: Double, rotation: Double) {
        leftFrontDrive.power = direction + side + rotation
        rightFrontDrive.power = direction - side - rotation
        leftBackDrive.power = direction - side + rotation
        rightBackDrive.power = direction + side - rotation
    }

    fun moveEncoder(cmForward: Double, cmSide: Double, Angle: Double, power: Double) {
        resetEncoder()
        var forwardDistance: Double
        var sideDistance: Double
        var forwardError: Double
        var sideError: Double
        var angleDistance: Double
        var angleError: Double
        do {
            forwardDistance = (leftFrontDrive.currentPosition + leftBackDrive.currentPosition + rightFrontDrive.currentPosition + rightBackDrive.currentPosition) / 4.0 / cmToEncoder
            sideDistance = ((leftFrontDrive.currentPosition - leftBackDrive.currentPosition - rightFrontDrive.currentPosition + rightBackDrive.currentPosition) / 4.0 / cmToEncoder)
            angleDistance = getGyroAngle()
            forwardError = forwardDistance - cmForward
            sideError = sideDistance - cmSide
            angleError = angleDistance - Angle
            while (abs(angleError) > 180)
                angleError -= angleError.sign * 360


            move(forwardError.sign * power, sideError.sign * power , angleError.sign * power) // * 0.0004
        } while(abs(sideError) > 2.5 || abs(forwardError) > 2.5 || abs(angleError) > 2.5)

    }


    private fun getGyroAngle(): Double {
        return imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES).firstAngle.toDouble()
    }


    fun setPowerAll(leftFrontPower: Double, rightFrontPower: Double, leftBackPower: Double, rightBackPower: Double) {
        leftFrontDrive.power = leftFrontPower
        rightFrontDrive.power = rightFrontPower
        leftBackDrive.power = leftBackPower
        rightBackDrive.power = rightBackPower
    }
}