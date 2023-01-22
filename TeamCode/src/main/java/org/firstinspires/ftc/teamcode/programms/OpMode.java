package org.firstinspires.ftc.teamcode.programms;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.robot.Robot;

@TeleOp(name = "OpMode [1 Driver]", group = "Linear Opmode")
public class OpMode extends LinearOpMode {

    // Declare OpMode members for each motor and servo
    private ElapsedTime runtime = new ElapsedTime();
    Robot robot;
    private final double MAX_POS = 1.0;
    private final double MIN_POS = 0.0;

    @Override
    public void runOpMode() {

        robot = new Robot(this);

        // Wait for the game to start (driver presses PLAY)
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();
        runtime.reset();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            double max;

            double rightHandServoPos = robot.manipulator.rightHandServo.getPosition();
            double leftHandServoPos = robot.manipulator.leftHandServo.getPosition();


            // POV Mode uses left joystick to go forward & strafe, and right joystick to rotate.
            double axial = -gamepad1.left_stick_y;  // Note: pushing stick forward gives negative value
            double lateral = gamepad1.left_stick_x;
            double yaw = gamepad1.right_stick_x;

            // Combine the joystick requests for each axis-motion to determine each wheel's power.
            // Set up a variable for each drive wheel to save the power level for telemetry.
            double leftFrontPower = axial + lateral + yaw;
            double rightFrontPower = axial - lateral - yaw;
            double leftBackPower = axial - lateral + yaw;
            double rightBackPower = axial + lateral - yaw;

            // Normalize the values so no wheel power exceeds 100%
            // This ensures that the robot maintains the desired motion.
            max = Math.max(Math.abs(leftFrontPower), Math.abs(rightFrontPower));
            max = Math.max(max, Math.abs(leftBackPower));
            max = Math.max(max, Math.abs(rightBackPower));

            if (max > 1.0) {
                leftFrontPower /= max;
                rightFrontPower /= max;
                leftBackPower /= max;
                rightBackPower /= max;
            }

            if (gamepad1.b) {
                if (robot.manipulator.leftHandServo.getPosition() == MAX_POS && robot.manipulator.leftHandServo.getPosition() == MAX_POS) {
                    System.out.println("Hand is already opened.");
                } else {
                    robot.manipulator.setPos(0.3);
                }
            }

           else if (gamepad1.x) {
                if (robot.manipulator.leftHandServo.getPosition() == MIN_POS && robot.manipulator.leftHandServo.getPosition() == MIN_POS) {
                    System.out.println("Hand is already closed.");
                } else {
                    robot.manipulator.setPos(0.0);
                }
            }

           if (gamepad1.dpad_up) {
               robot.lift.setPower(1);
               sleep(1);
               robot.lift.setPower(0);
           }

            if (gamepad1.dpad_down) {
                robot.lift.setPower(-1);
                sleep(1);
                robot.lift.setPower(0);
            }

            // Dividing stick position to slow down motor and setting power

            // Send calculated power to wheels
            robot.driveTrain.setPowerAll(leftFrontPower, rightFrontPower, leftBackPower, rightBackPower);

            // Show the elapsed game time and wheel power.
            telemetry.addData("Status", "Run Time: " + runtime.toString());
            telemetry.addData("Front left/Right", "%4.2f, %4.2f", leftFrontPower, rightFrontPower);
            telemetry.addData("Back  left/Right", "%4.2f, %4.2f", leftBackPower, rightBackPower);
            telemetry.update();
        }
    }
}

