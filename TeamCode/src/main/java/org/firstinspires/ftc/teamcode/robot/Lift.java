package org.firstinspires.ftc.teamcode.robot;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

public class Lift {
    Robot robot;

    double bottom = -130;
    double up = 1500;
    private final double spoolRadiusMm = 25.0;
    private final double spoolCircumference = spoolRadiusMm * 2.0 * Math.PI;
    private final double encoderTicksPerRevNoGearbox = 24.0;
    private final double gearboxRatio = 40.0;
    private final double encoderTicksPerRevGearbox = encoderTicksPerRevNoGearbox * gearboxRatio;
    private final double liftMmToEncoderRatio = encoderTicksPerRevGearbox / spoolCircumference;
    // Declare each motor in lift.
    public DcMotor lift; // DcMotor = robot.linearOpMode.hardwareMap.get(DcMotor::class.java, "lift_drive")

    public Lift(Robot robot) {
        this.robot = robot;

        lift = robot.linearOpMode.hardwareMap.get(DcMotor.class, "lift_drive");
    }

    public void setPowerRaw(double power) {
        lift.setPower(power);
    }

    private double encoderPowerLimit(double power) {
        if (power < 0 && lift.getCurrentPosition() <= bottom) {
            return 0;
        }
        else if (power > 0 && lift.getCurrentPosition() >= up) {
            return 0;
        }
        else {
            return power;
        }
    }

    void setPower(double power) {
        lift.setPower(encoderPowerLimit(power));
    }
}