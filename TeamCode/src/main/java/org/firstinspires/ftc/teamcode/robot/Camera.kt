package org.firstinspires.ftc.teamcode.robot


import org.firstinspires.ftc.robotcore.external.Telemetry
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName
import org.firstinspires.ftc.teamcode.programms.autonomous.apriltag.AprilTagDetectionPipeline
import org.openftc.apriltag.AprilTagDetection
import org.openftc.easyopencv.OpenCvCamera
import org.openftc.easyopencv.OpenCvCamera.AsyncCameraOpenListener
import org.openftc.easyopencv.OpenCvCameraFactory
import org.openftc.easyopencv.OpenCvCameraRotation


class Camera(var robot: Robot) {
    var telemetry = robot.linearOpMode.telemetry
    @JvmField
    var cameraMonitorViewId: Int = robot.linearOpMode.hardwareMap.appContext.getResources()
        .getIdentifier("cameraMonitorViewId", "id", robot.linearOpMode.hardwareMap.appContext.getPackageName())

    @JvmField
    var camera: OpenCvCamera = OpenCvCameraFactory.getInstance().createWebcam(
        robot.linearOpMode.hardwareMap.get<WebcamName>(
            WebcamName::class.java, "webcam"
        ), cameraMonitorViewId
    )
    val FEET_PER_METER = 3.28084

    // Lens intrinsics
    // UNITS ARE PIXELS
    // NOTE: this calibration is for the C920 webcam at 800x448.
    // You will need to do your own calibration for other configurations!
    var fx = 578.272
    var fy = 578.272
    var cx = 402.145
    var cy = 221.506

    // UNITS ARE METERS
    var tagsize = 0.166

    var tagOfInterest1 = 9
    var tagOfInterest2 = 18
    var tagOfInterest3 = 27

    var aprilTagDetectionPipeline: AprilTagDetectionPipeline =
        AprilTagDetectionPipeline(tagsize, fx, fy, cx, cy)

    fun initCamera() {
        camera.setPipeline(aprilTagDetectionPipeline)
        camera.openCameraDeviceAsync(object : AsyncCameraOpenListener {
            override fun onOpened() {
                camera.startStreaming(800, 600, OpenCvCameraRotation.UPRIGHT)
            }
            override fun onError(errorCode: Int) {}
        })
    }

    fun closeCamera() {
        camera.closeCameraDeviceAsync(object : OpenCvCamera.AsyncCameraCloseListener {
            override fun onClose() {
                camera.stopStreaming()
            }
        })
    }

    fun findTag(): Int {
        var tagOfInterest: Int = 0
        val currentDetections: ArrayList<AprilTagDetection> =
            aprilTagDetectionPipeline.getLatestDetections()

        if (currentDetections.size != 0) {
            var tagFound = false
            for (tag in currentDetections) {
                if (tag.id == tagOfInterest1) {
                    tagOfInterest = 1
                    tagFound = true
                    break
                }
                else if (tag.id == tagOfInterest2) {
                    tagOfInterest = 2
                    tagFound = true
                    break
                }
                else if (tag.id == tagOfInterest3) {
                    tagOfInterest = 3
                    tagFound = true
                    break
                }
            }
        }
        return tagOfInterest
    }
}