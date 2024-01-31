package org.firstinspires.ftc.teamcode.robot;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;


public class Robot {

    public WheelBase wheelBase;
    public Manipulator manipulator;
    public Lift lift;

    public Hang hang;

    public Camera camera;
    public LinearOpMode linearOpMode;

    public Robot(LinearOpMode linearOpMode1) {
        linearOpMode = linearOpMode1;
        wheelBase = new WheelBase(this);
        lift = new Lift(this);
        manipulator = new Manipulator(this);
        camera = new Camera(this);
        hang = new Hang(this);
    }
}