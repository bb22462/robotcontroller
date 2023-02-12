package org.firstinspires.ftc.teamcode.programms.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.robot.Robot;

@Autonomous(name="Autonomous Right [OLD]")
public class AutonomousRightOld extends LinearOpMode {

    Robot robot;

    @Override
    // throws InterruptedException
    public void runOpMode() {
        robot = new Robot(this);
        waitForStart();


        robot.wheelBase.move(0, 1, 0);
        sleep(1500);




    }
}
