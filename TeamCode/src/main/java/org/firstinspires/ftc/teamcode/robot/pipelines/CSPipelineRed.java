package org.firstinspires.ftc.teamcode.robot.pipelines;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;


public class CSPipelineRed extends OpenCvPipeline
{
    // RED - 1
    // BLUE - 2

    Mat YCbCr = new Mat();
    Mat leftCrop = new Mat();
    Mat centerCrop = new Mat();
    Mat rightCrop = new Mat();
    Mat leftExtract = new Mat();
    Mat centerExtract = new Mat();
    Mat rightExtract = new Mat();
    double leftavgfin;
    double centeravgfin;
    double rightavgfin;
    Mat output = new Mat();
    Scalar rectColor = new Scalar(255.0, 0, 0);
    Rect leftRect = new Rect(1, 1, 213, 479);
    Rect centerRect = new Rect(214, 1, 213, 479);
    Rect rightRect = new Rect(427, 1, 213, 479);
    public int location = 0;

    @Override
    public Mat processFrame(Mat input) {
        Imgproc.cvtColor(input, YCbCr, Imgproc.COLOR_RGB2YCrCb);


        input.copyTo(output);
        Imgproc.rectangle(output, leftRect, rectColor, 2);
        Imgproc.rectangle(output, centerRect, rectColor, 2);
        Imgproc.rectangle(output, rightRect, rectColor, 2);

        leftCrop = YCbCr.submat(leftRect);
        centerCrop = YCbCr.submat(centerRect);
        rightCrop = YCbCr.submat(rightRect);

        Core.extractChannel(leftCrop, leftExtract, 1);
        Core.extractChannel(rightCrop, rightExtract, 1);
        Core.extractChannel(centerCrop, centerExtract, 1);

        Scalar leftavg = Core.mean(leftExtract);
        Scalar centeravg = Core.mean(centerExtract);
        Scalar rightavg = Core.mean(rightExtract);

        leftavgfin = leftavg.val[0];
        centeravgfin = centeravg.val[0];
        rightavgfin = rightavg.val[0];

        if (leftavgfin > centeravgfin && leftavgfin > rightavgfin) {
            location = 1;
        }
        else if (centeravgfin > leftavgfin && centeravgfin > rightavgfin) {
            location = 2;
        }
        else {

            location = 3;
        }



        return output;
    }
}
