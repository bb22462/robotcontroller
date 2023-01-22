package org.firstinspires.ftc.teamcode.robot;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;


public class Robot {

    public DriveTrain driveTrain;
    public Manipulator manipulator;
    public Lift lift;
    public LinearOpMode linearOpMode;

    public Robot(LinearOpMode linearOpMode1) {
        linearOpMode = linearOpMode1;
        driveTrain = new DriveTrain();
        lift = new Lift();
        manipulator = new Manipulator();
    }
}
