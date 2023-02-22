package org.firstinspires.ftc.teamcode.robot

import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorSimple

class WheelBase(var robot: Robot) {
    // Declare each motor in drivetrain
    private var leftFrontDrive: DcMotor = robot.linearOpMode.hardwareMap.get(DcMotor::class.java, "left_front_drive")
    private var rightFrontDrive: DcMotor = robot.linearOpMode.hardwareMap.get(DcMotor::class.java, "right_front_drive")
    private var leftBackDrive: DcMotor = robot.linearOpMode.hardwareMap.get(DcMotor::class.java, "left_back_drive")
    private var rightBackDrive: DcMotor = robot.linearOpMode.hardwareMap.get(DcMotor::class.java, "right_back_drive")
    private val wheelRadius = 4.9
    private val wheelLength = wheelRadius * 2 * Math.PI
    private val cmToEncoder = 1440 / wheelLength

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

    fun moveEncoder(cm: Double, direction: Double, side: Double, rotation: Double) {
        resetEncoder()
        while (leftFrontDrive.currentPosition < cm * cmToEncoder) {
            move(direction, side, rotation)
        }
    }

    fun setPowerAll(leftFrontPower: Double, rightFrontPower: Double, leftBackPower: Double, rightBackPower: Double) {
        leftFrontDrive.power = leftFrontPower
        rightFrontDrive.power = rightFrontPower
        leftBackDrive.power = leftBackPower
        rightBackDrive.power = rightBackPower
    }
}