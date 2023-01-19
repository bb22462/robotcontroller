package org.firstinspires.ftc.teamcode.robot;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

public class Lift {
    // Declare each motor in lift
    public DcMotor leftLiftDrive = null;
    public DcMotor rightLiftDrive = null;
    Robot robot;


    public Lift() {
        // Initialize the hardware variables. Note that the strings used here must correspond
        // to the names assigned during the robot configuration step on the DS or RC devices.
        leftLiftDrive = robot.linearOpMode.hardwareMap.get(DcMotor.class, "left_lift_drive"); // Motor 0
        rightLiftDrive = robot.linearOpMode.hardwareMap.get(DcMotor.class, "right_lift_drive"); // Motor 2

        // Most robots need the motors on one side to be reversed to drive forward.
        leftLiftDrive.setDirection(DcMotor.Direction.FORWARD);
        rightLiftDrive.setDirection(DcMotor.Direction.REVERSE);

        leftLiftDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightLiftDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public void setPower(double power){
        leftLiftDrive.setPower(power);
        rightLiftDrive.setPower(power);
    }
}
