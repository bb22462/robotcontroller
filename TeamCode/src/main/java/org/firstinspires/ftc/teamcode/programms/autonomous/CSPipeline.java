package org.firstinspires.ftc.teamcode.programms.autonomous;

import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.openftc.easyopencv.OpenCvPipeline;

public class CSPipeline extends OpenCvPipeline
{
    Mat YCbCr = new Mat();
    Mat leftCrop;
    Mat rightCrop;
    double leftavgfin;
    double rightavgfin;
    Mat output = new Mat();
    Scalar rectColor = new Scalar(255.0, 0, 0);
    @Override
    public Mat processFrame(Mat input) {




        return null;
    }
}
