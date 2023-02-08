package org.firstinspires.ftc.teamcode.programms.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.robot.Robot;

@Autonomous(name="Autonomous Right [NEW]")
public class AutonomousRightNew extends LinearOpMode {

    Robot robot;
    private final double MAX_POS = 1.0;
    private final double MIN_POS = 0.0;

    @Override
    public void runOpMode() throws InterruptedException {
        robot = new Robot(this);
        waitForStart();


        //robot.driveTrain.move(0, 1, 0);
        //sleep(1500);

        // Close the manipulator
        robot.manipulator.setPos(0.0);
        sleep(1000);

        // Drive closer to the ground
        robot.wheelBase.move(1, 0, 0);
        sleep(500);
        robot.wheelBase.move(0, 0, 0);

        // Open the manipulator
        robot.manipulator.setPos(0.2);
        sleep(1000);

        // Drive backwards
        robot.wheelBase.move(-1, 0, 0);
        sleep(300);
        robot.wheelBase.move(0, 0, 0);

        // Drive to the parking
        robot.wheelBase.move(0, 1, 0);
        sleep(600);
        robot.wheelBase.move(0, 0, 0);



    }
}
