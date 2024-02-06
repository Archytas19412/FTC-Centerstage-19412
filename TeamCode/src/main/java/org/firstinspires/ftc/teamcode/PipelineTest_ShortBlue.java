package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvWebcam;


@Autonomous
public class PipelineTest_ShortBlue extends LinearOpMode {
    DcMotor FrontL;
    DcMotor BackL;
    DcMotor BackR;
    DcMotor FrontR;
    Servo ClawServo;
    OpenCvWebcam webcam;
    BluePipeline bPropPL;

    org.firstinspires.ftc.teamcode.BluePipeline.PropPosition position = null;

    @Override
    public void runOpMode() {
        /**
         * NOTE: Many comments have been omitted from this sample for the
         * sake of conciseness. If you're just starting out with EasyOpenCv,
         * you should take a look at {@link InternalCamera1Example} or its
         * webcam counterpart, {@link WebcamExample} first.
         */


        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        webcam = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "Webcam 1"), cameraMonitorViewId);
        bPropPL = new BluePipeline();
        webcam.setPipeline(bPropPL);

        webcam.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
            @Override
            public void onOpened() {
                webcam.startStreaming(640, 480, OpenCvCameraRotation.UPRIGHT);
            }

            @Override
            public void onError(int errorCode) {
                /*
                 * This will be called if the camera could not be opened
                 */
            }
        });

        FrontL = hardwareMap.get(DcMotor.class, "FrontL");
        BackL = hardwareMap.get(DcMotor.class, "BackL");
        BackR = hardwareMap.get(DcMotor.class, "BackR");
        FrontR = hardwareMap.get(DcMotor.class, "FrontR");
        ClawServo = hardwareMap.get(Servo.class, "ClawServo");

        FrontL.setDirection(DcMotor.Direction.REVERSE);
        BackL.setDirection(DcMotor.Direction.REVERSE);

        //Reduce slip in motors
        FrontL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BackL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BackR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        FrontR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        ClawServo.setPosition(0.2);

        while (!isStarted() && !isStopRequested()) {
            position = bPropPL.returnPos();
            telemetry.addData("PROP FOUND @", position );
            telemetry.update();
            sleep(50);
        }

        waitForStart();

        while (opModeIsActive()) {

            webcam.stopStreaming();


            sleep(1000);

            telemetry.addData("Auto running using", position );
            telemetry.update();

            if (position.toString().equals("LEFT")) {
                // Go Forward
                Drive(1000, 1000, 1000, 1000, 0.5);
                sleep(2000);
                // Strafe Left
                Drive(-525, 525, 525, -525, 0.5);
                sleep(2000);
                // Claw Opens
                ClawServo.setPosition(0);
                sleep(500);
                // Go Backward
                Drive(-850, -850, -850, -850, 0.5);
                sleep(3000);
                //Strafe Left
                Drive(-1475 ,1475, 1475, -1475, 0.5);
                sleep(30000);
            }
            if (position.toString().equals("CENTER")) {
                //Go Forward
                Drive(1250, 1250, 1250, 1250, 0.5);
                sleep(2000);
                //Claw Opens
                ClawServo.setPosition(0);
                sleep(500);
                //Go Backward
                Drive(-1100, -1100, -1100, -1100, 0.5);
                sleep(3000);
                //Strafe Left
                Drive(-2000, 2000, 2000, -2000, 0.5);
                sleep(30000);
            }
            else {
                //Go Forward
                Drive(1250, 1250, 1250, 1250, 0.5);
                sleep(2000);
                //Turn Right
                Drive(1000, -1000, 1000, -1000, 0.5);
                sleep(3000);
                //Go Forward
                Drive(100, 100, 100, 100, 0.5);
                sleep(1000);
                //Claw Opens
                ClawServo.setPosition(0);
                sleep(500);
                //Go Backwards
                Drive(-300, -300, -300, -300, 0.5);
                sleep(3000);
                //Straighten Robot
                Drive(-1000, 1000, -1000, 1000, 0.5);
                sleep(3000);
                //Go Backwards
                Drive(-1100, -1100, -1100, -1100, 0.5);
                sleep(3000);
                //Strafe Left
                Drive(-1800, 1800, 1800, -1800, 0.5);
                sleep(30000);
                telemetry.update();
            }

        }
    }
    public void Drive(int FrontLTarget, int FrontRTarget, int BackLTarget, int BackRTarget, double Speed) {
        FrontL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        FrontR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BackL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BackR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        FrontL.setTargetPosition(FrontLTarget);
        FrontR.setTargetPosition(FrontRTarget);
        BackL.setTargetPosition(BackLTarget);
        BackR.setTargetPosition(BackRTarget);

        FrontL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        FrontR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        BackL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        BackR.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        FrontL.setPower(Speed);
        FrontR.setPower(Speed);
        BackL.setPower(Speed);
        BackR.setPower(Speed);

        // Blocking While Loop: doesn't break until all 4 motors have stopped moving
        // Sets power of motors to 0 after the loop breaks
        while (opModeIsActive() && (BackR.isBusy() || BackL.isBusy() || FrontR.isBusy() || FrontL.isBusy())) {
        }

        FrontL.setPower(0);
        FrontR.setPower(0);
        BackL.setPower(0);
        BackR.setPower(0);
    }
}