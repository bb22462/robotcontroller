package org.firstinspires.ftc.teamcode.robot;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

public class Hang {
    DcMotor hang;
    public Servo left_servo;
    public Servo right_servo;

    Robot robot;
    public Hang(Robot robot) {
        hang = robot.linearOpMode.hardwareMap.get(DcMotor.class, "hang_drive");
        left_servo = robot.linearOpMode.hardwareMap.get(Servo.class, "left_hang_servo");
        right_servo = robot.linearOpMode.hardwareMap.get(Servo.class, "right_hang_servo");

        hang.setDirection(DcMotorSimple.Direction.FORWARD);

        hang.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        this.robot = robot;
    }

    public void setPower(double power){
        hang.setPower(power);
    }
}
