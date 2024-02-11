package org.firstinspires.ftc.teamcode.robot;

import com.qualcomm.robotcore.util.ElapsedTime;

public class PD {
    private final double _p, _d;
    private double _errOld = 0;
    private final ElapsedTime _deltaTime = new ElapsedTime();

    public PD(double p, double d) {
        _p = p;
        _d = d;
    }

    public double update(double err){
        double u = err * _p + (err - _errOld) * _deltaTime.seconds() * _d;

        _errOld = err;

        _deltaTime.reset();

        return u;
    }

    public void start(){
        _deltaTime.reset();
    }
}
