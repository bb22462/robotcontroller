package org.firstinspires.ftc.teamcode.robot;



import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;



public class DriveTrain {
    // Declare each motor in drivetrain
    public DcMotor leftFrontDrive = null;
    public DcMotor rightFrontDrive = null;
    public DcMotor leftBackDrive = null;
    public DcMotor rightBackDrive = null;
    Robot robot;


    public DriveTrain(Robot robot1) {
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
    }

    public void move(double forward, double side, double rotation){
        leftFrontDrive.setPower(forward + side + rotation);
        rightFrontDrive.setPower(forward - side - rotation);
        leftBackDrive.setPower(forward - side + rotation);
        rightBackDrive.setPower(forward + side - rotation);
    }

    public void setPowerAll(double power){
        leftFrontDrive.setPower(power);
        rightFrontDrive.setPower(power);
        leftBackDrive.setPower(power);
        rightBackDrive.setPower(power);
    }

    public void setPowerAll(double leftFrontPower, double rightFrontPower, double leftBackPower, double rightBackPower){
        leftFrontDrive.setPower(leftFrontPower);
        rightFrontDrive.setPower(rightFrontPower);
        leftBackDrive.setPower(leftBackPower);
        rightBackDrive.setPower(rightBackPower);
    }
}
