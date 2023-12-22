package org.firstinspires.ftc.teamcode.robot.pipelines;

import com.acmerobotics.dashboard.config.Config;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;

@Config
public class CSPipelineBlue extends OpenCvPipeline
{
    // RED - 1
    // BLUE - 2

    Mat color = new Mat();
    Mat output = new Mat();
    Mat leftCrop = new Mat();
    Mat centerCrop = new Mat();
    Mat rightCrop = new Mat();

    public static Scalar lower = new Scalar(0, 31.2, 136);
    public static Scalar upper = new Scalar(82.2, 165.8, 255);

    Scalar rectColor = new Scalar(255.0, 0, 0);
    Rect leftRect = new Rect(1, 180, 426, 359);
    Rect centerRect = new Rect(1+426+40, 180, 356, 280);
    Rect rightRect = new Rect(1+426+426, 180, 426, 359);

    double leftavgfin;
    double centeravgfin;
    double rightavgfin;

    public int location = 0;


    @Override
    public Mat processFrame(Mat frame) {
        Imgproc.cvtColor(frame, frame, Imgproc.COLOR_BGR2HLS);
        Core.inRange(frame, lower, upper, frame);

        frame.copyTo(output);
        Imgproc.rectangle(output, leftRect, rectColor, 2);
        Imgproc.rectangle(output, centerRect, rectColor, 2);
        Imgproc.rectangle(output, rightRect, rectColor, 2);

        leftCrop = frame.submat(leftRect);
        centerCrop = frame.submat(centerRect);
        rightCrop = frame.submat(rightRect);

        Scalar leftavg = Core.mean(leftCrop);
        Scalar centeravg = Core.mean(centerCrop);
        Scalar rightavg = Core.mean(rightCrop);

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
