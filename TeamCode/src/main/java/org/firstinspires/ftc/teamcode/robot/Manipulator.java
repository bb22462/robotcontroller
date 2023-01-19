package org.firstinspires.ftc.teamcode.robot;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

public class Manipulator {
    public Servo leftHandServo = null;
    public Servo rightHandServo = null;
    Robot robot;

    public Manipulator() {
        leftHandServo = robot.linearOpMode.hardwareMap.get(Servo.class, "left_hand_servo");
        rightHandServo = robot.linearOpMode.hardwareMap.get(Servo.class, "right_hand_servo");

        rightHandServo.setDirection(Servo.Direction.REVERSE);
        leftHandServo.setDirection(Servo.Direction.FORWARD);
    }

    public void setPos(double pos) {
        leftHandServo.setPosition(pos);
        rightHandServo.setPosition(pos);
    }

}
