package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.RedPipeline;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvWebcam;


@Autonomous
public class PipelineTest_Red extends LinearOpMode {

    DcMotor FrontL;
    DcMotor BackL;
    DcMotor BackR;
    DcMotor FrontR;
    Servo ClawServo;
    OpenCvWebcam webcam;
    RedPipeline rPropPL;


    org.firstinspires.ftc.teamcode.RedPipeline.PropPosition position = null;

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
        rPropPL = new RedPipeline();
        webcam.setPipeline(rPropPL);

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


        while (!isStarted() && !isStopRequested()) {
            position = rPropPL.returnPos();
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

            }
            if (position.toString().equals("CENTER")) {

            }
            else {

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