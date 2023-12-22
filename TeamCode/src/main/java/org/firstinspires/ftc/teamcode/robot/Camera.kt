package org.firstinspires.ftc.teamcode.robot


import com.acmerobotics.dashboard.FtcDashboard
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName
import org.firstinspires.ftc.teamcode.robot.pipelines.CSPipelineBlue
import org.firstinspires.ftc.teamcode.robot.pipelines.CSPipelineRed
import org.openftc.apriltag.AprilTagDetection
import org.openftc.easyopencv.OpenCvCamera
import org.openftc.easyopencv.OpenCvCamera.AsyncCameraOpenListener
import org.openftc.easyopencv.OpenCvCameraFactory
import org.openftc.easyopencv.OpenCvCameraRotation



class Camera(var robot: Robot) {
    @JvmField
    var cameraMonitorViewId: Int = robot.linearOpMode.hardwareMap.appContext.getResources()
        .getIdentifier("cameraMonitorViewId", "id", robot.linearOpMode.hardwareMap.appContext.getPackageName())

    @JvmField
    var camera: OpenCvCamera = OpenCvCameraFactory.getInstance().createWebcam(
        robot.linearOpMode.hardwareMap.get<WebcamName>(
            WebcamName::class.java, "webcam"
        ), cameraMonitorViewId
    )


    private var CSPipeLineRed: CSPipelineRed =
            CSPipelineRed()
    private var CSPipeLineBlue: CSPipelineBlue =
            CSPipelineBlue()

    // RED - 1
    // BLUE - 2
    fun initCamera(alliance: Int) {
        if(alliance == 1) {
            camera.setPipeline(CSPipeLineRed)
        }
        else if (alliance == 2) {
            camera.setPipeline(CSPipeLineBlue)
        }
        camera.openCameraDeviceAsync(object : AsyncCameraOpenListener {
            override fun onOpened() {
                camera.startStreaming(1280, 720, OpenCvCameraRotation.UPRIGHT)
                FtcDashboard.getInstance().startCameraStream(camera, 60.0);

            }
            override fun onError(errorCode: Int) {}
        })
    }

    fun closeCamera() {
        camera.closeCameraDeviceAsync { camera.stopStreaming() }
    }

    fun findRed(): Int {
        val current = CSPipeLineRed.location
        return current;
    }
    fun findBlue(): Int {
        val current = CSPipeLineBlue.location
        return current;
    }
}