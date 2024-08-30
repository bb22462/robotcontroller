package org.firstinspires.ftc.teamcode.robot;

import com.qualcomm.robotcore.hardware.Servo;

public class Manipulator {
    Robot robot;

    public Servo ManipulatorServoLeft;
    public Servo ManipulatorServoRight;
    public Servo VerticalServo;
    public Servo Samolet;
    public Servo LeftHangServo;
    public Servo RightHangServo;

    public double LeftmaxPos = 0.5;
    public double LeftminPos = 0.2;
    public double RightmaxPos = 0.5;
    public double RightminPos = 0.2;
    public double increment = 0.1;
    public double openPos = 0.1;

    public Manipulator(Robot robot) {
        this.robot = robot;

        ManipulatorServoLeft = robot.linearOpMode.hardwareMap.get(Servo.class, "left_manipulator_servo");
        ManipulatorServoRight = robot.linearOpMode.hardwareMap.get(Servo.class, "right_manipulator_servo");
        VerticalServo = robot.linearOpMode.hardwareMap.get(Servo.class, "vertical_servo");
        Samolet = robot.linearOpMode.hardwareMap.get(Servo.class, "samolet");
        LeftHangServo = robot.linearOpMode.hardwareMap.get(Servo.class, "left_hang_servo");
        RightHangServo = robot.linearOpMode.hardwareMap.get(Servo.class, "right_hang_servo");

        ManipulatorServoLeft.setDirection(Servo.Direction.FORWARD);
        ManipulatorServoRight.setDirection(Servo.Direction.REVERSE);
    }

    public void setPosAll(double posLeft, double posRight) {
        ManipulatorServoLeft.setPosition(posLeft);
        ManipulatorServoRight.setPosition(posRight);
    }

    public void setPosLeft(double pos) {
        ManipulatorServoLeft.setPosition(pos);
    }

    public void setPosRight(double pos) {
        ManipulatorServoRight.setPosition(pos);
    }

    public void setVerticalPos(double pos) {
        VerticalServo.setPosition(pos);
    }

    public void moveSamolet(double pos) {
        Samolet.setPosition(pos);
    }
}