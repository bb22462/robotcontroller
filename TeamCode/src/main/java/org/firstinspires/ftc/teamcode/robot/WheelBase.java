package org.firstinspires.ftc.teamcode.robot;

import static java.lang.Math.PI;

import com.qualcomm.robotcore.hardware.DcMotor;

public class WheelBase {
    // Declare each motor in drivetrain
    public DcMotor leftFrontDrive = null;
    public DcMotor rightFrontDrive = null;
    public DcMotor leftBackDrive = null;
    public DcMotor rightBackDrive = null;
    final double WHEEL_RADIUS = 4.9;
    final double WHEEL_LENGTH = 4.9 * 2 * PI;
    final double CM_TO_ENCODER = 1440 / WHEEL_LENGTH;
    Robot robot;


    public WheelBase(Robot robot1) {
        robot = robot1;
        // Initialize the hardware variables. Note that the strings used here must correspond
        // to the names assigned during the robot configuration step on the DS or RC devices.
        leftFrontDrive = robot.linearOpMode.hardwareMap.get(DcMotor.class, "left_front_drive"); // Motor 3
        rightFrontDrive = robot.linearOpMode.hardwareMap.get(DcMotor.class, "right_front_drive"); // Motor 2
        leftBackDrive = robot.linearOpMode.hardwareMap.get(DcMotor.class, "left_back_drive"); // Motor 0
        rightBackDrive = robot.linearOpMode.hardwareMap.get(DcMotor.class, "right_back_drive"); // Motor 1

        // Most robots need the motors on one side to be reversed to drive forward.
        leftFrontDrive.setDirection(DcMotor.Direction.REVERSE);
        rightFrontDrive.setDirection(DcMotor.Direction.FORWARD);
        leftBackDrive.setDirection(DcMotor.Direction.REVERSE);
        rightBackDrive.setDirection(DcMotor.Direction.FORWARD);

        leftFrontDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightFrontDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftBackDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightBackDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        leftFrontDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftFrontDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    /* Directions:
    * 1 - Forward
    * -1 - Backwards
    *
    * Side:
    * 1 - Right
    * -1 - Left */

    void resetEncoder() {
        leftFrontDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftFrontDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        leftBackDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftBackDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightFrontDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightFrontDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightBackDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightBackDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }
    public void move(double direction, double side, double rotation){
        leftFrontDrive.setPower(direction + side + rotation);
        rightFrontDrive.setPower(direction - side - rotation);
        leftBackDrive.setPower(direction - side + rotation);
        rightBackDrive.setPower(direction + side - rotation);
    }

    public void setPowerAll(double power){
        leftFrontDrive.setPower(power);
        rightFrontDrive.setPower(power);
        leftBackDrive.setPower(power);
        rightBackDrive.setPower(power);
    }



    public void moveEncoder(double cm, double direction, double side, double rotation){
        resetEncoder();

        while (leftFrontDrive.getCurrentPosition() < cm * CM_TO_ENCODER) {
            move(direction, side, rotation);
        }

    }

    public void setPowerAll(double leftFrontPower, double rightFrontPower, double leftBackPower, double rightBackPower){
        leftFrontDrive.setPower(leftFrontPower);
        rightFrontDrive.setPower(rightFrontPower);
        leftBackDrive.setPower(leftBackPower);
        rightBackDrive.setPower(rightBackPower);
    }
}
