package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
@TeleOp(name="Tele_1", group="Linear Opmode")
public class Tele_1 extends LinearOpMode {
    DcMotor motor;
    double pwr;

    @Override
    public void runOpMode() throws InterruptedException {
        motor=hardwareMap.get(DcMotor.class, "M1");
        motor.setDirection(DcMotorSimple.Direction.FORWARD);
        motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        waitForStart();

        while(opModeIsActive(  )){
            if(Math.abs(gamepad1.left_stick_y)<0.1)
                pwr=0;
            else
                 pwr=gamepad1.left_stick_y;
            motor.setPower(pwr);
        }
    }
}
