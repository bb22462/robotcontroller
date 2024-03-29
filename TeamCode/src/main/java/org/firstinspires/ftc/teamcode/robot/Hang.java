package org.firstinspires.ftc.teamcode.robot;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
@Config
public class Hang {
    public static double close_pos_left = 0.5;
    public static double open_pos_left = 0.0;
    public static double close_pos_right = 0.25;
    public static double open_pos_right= 0.8;
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
