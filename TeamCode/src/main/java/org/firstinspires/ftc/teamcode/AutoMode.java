package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

@Autonomous(name="AutoMode")
public class AutoMode extends LinearOpMode {

    private DcMotor leftFrontDrive = null;
    private DcMotor leftBackDrive = null;
    private DcMotor rightFrontDrive = null;
    private DcMotor rightBackDrive = null;

    void move(double forward, double side, double rotation){
        leftFrontDrive.setPower(forward + side + rotation);
        rightFrontDrive.setPower(forward - side - rotation);
        leftBackDrive.setPower(forward - side + rotation);
        rightBackDrive.setPower(forward + side - rotation);
    }

    @Override
    public void runOpMode() throws InterruptedException {
        leftFrontDrive  = hardwareMap.get(DcMotor.class, "left_front_drive");
        leftBackDrive  = hardwareMap.get(DcMotor.class, "left_back_drive");
        rightFrontDrive = hardwareMap.get(DcMotor.class, "right_front_drive");
        rightBackDrive = hardwareMap.get(DcMotor.class, "right_back_drive");
        leftFrontDrive.setDirection(DcMotor.Direction.REVERSE);
        leftBackDrive.setDirection(DcMotor.Direction.REVERSE);
        rightFrontDrive.setDirection(DcMotor.Direction.FORWARD);
        rightBackDrive.setDirection(DcMotor.Direction.FORWARD);
        waitForStart();

        move(1, 0, 0);

        sleep(2000);

        move(0, 1, 0);
        sleep(2000);


        move(-1, 0, 0);
        sleep(2000);

        move(0, -1, 0);
        sleep(2000);




    }
}
