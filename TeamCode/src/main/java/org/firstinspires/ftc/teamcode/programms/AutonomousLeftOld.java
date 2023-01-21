package org.firstinspires.ftc.teamcode.programms;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.robot.Robot;

@Autonomous(name="Autonomous Left [OLD]")
public class AutonomousLeftOld extends LinearOpMode {

    Robot robot;
    private final double MAX_POS = 1.0;
    private final double MIN_POS = 0.0;

    @Override
    public void runOpMode() throws InterruptedException {
        robot = new Robot(this);
        waitForStart();


        robot.driveTrain.move(0, -1, 0);
        sleep(1500);




    }
}
