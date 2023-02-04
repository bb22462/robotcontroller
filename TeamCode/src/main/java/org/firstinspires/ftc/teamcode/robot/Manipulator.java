package org.firstinspires.ftc.teamcode.robot;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

public class Manipulator {
    public Servo leftManipulatorServo = null;
    public Servo rightManipulatorServo = null;
    private final double MAX_POS = 1.0;
    private final double MIN_POS = 0.0;
    Robot robot;

    public Manipulator(Robot robot1) {
        robot = robot1;

        leftManipulatorServo = robot.linearOpMode.hardwareMap.get(Servo.class, "left_manipulator_servo");
        rightManipulatorServo = robot.linearOpMode.hardwareMap.get(Servo.class, "right_manipulator_servo");

        rightManipulatorServo.setDirection(Servo.Direction.FORWARD);
        leftManipulatorServo.setDirection(Servo.Direction.REVERSE);
    }

    public void setPos(double pos) {
        leftManipulatorServo.setPosition(pos);
        rightManipulatorServo.setPosition(pos);
    }

}
