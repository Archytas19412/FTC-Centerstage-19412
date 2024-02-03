/*package org.firstinspires.ftc.teamcode.auto.pipelines;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;


public class rPropPL extends OpenCvPipeline {

    public enum PropPosition
    @@ -21,9 +20,9 @@ public enum PropPosition
    static final Scalar BLUE = new Scalar(0, 0, 255);
    static final Scalar GREEN = new Scalar(0, 255, 0);

    static final Point REGION1_TOPLEFT_ANCHOR_POINT = new Point(0,200);
    static final Point REGION2_TOPLEFT_ANCHOR_POINT = new Point(225,175);
    static final Point REGION3_TOPLEFT_ANCHOR_POINT = new Point(540,200);
    static final Point REGION1_TOPLEFT_ANCHOR_POINT = new Point(0,150);
    static final Point REGION2_TOPLEFT_ANCHOR_POINT = new Point(225,125);
    static final Point REGION3_TOPLEFT_ANCHOR_POINT = new Point(540,150);
    static final int REGION_WIDTH = 100;
    static final int REGION_HEIGHT = 75;

    @@ -223,11 +222,9 @@ else if(max == avg3) // Was it from region 3?
            return input;
}

    @Override
    public void onViewportTapped() {
        // Executed when the image display is clicked by the mouse or tapped
        // This method is executed from the UI thread, so be careful to not
        // perform any sort heavy processing here! Your app might hang otherwise
        public PropPosition returnPos()
        {
            return position;
        }

    }
  142 changes: 105 additions & 37 deletions142
          TeamCode/src/main/java/org/firstinspires/ftc/teamcode/auto/redSideAuto.java
@@ -4,71 +4,139 @@
        import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
        import com.qualcomm.robotcore.hardware.DcMotor;

        import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
        import org.firstinspires.ftc.teamcode.auto.pipelines.rPropPL;
        import org.openftc.easyopencv.OpenCvCamera;
        import org.openftc.easyopencv.OpenCvCameraFactory;
        import org.openftc.easyopencv.OpenCvCameraRotation;
        import org.openftc.easyopencv.OpenCvWebcam;


@Autonomous
public class redSideAuto extends LinearOpMode {
    OpenCvWebcam webcam;
    rPropPL rPropPL;

    public DcMotor leftFrontMotor, leftBackMotor, rightFrontMotor, rightBackMotor;

    @Override
    public void runOpMode() throws InterruptedException {
        private DcMotor leftBackMotor, leftFrontMotor, rightBackMotor, rightFrontMotor;

        leftFrontMotor = hardwareMap.dcMotor.get("leftFrontMotor");
        leftBackMotor = hardwareMap.dcMotor.get("leftBackMotor");
        rightFrontMotor = hardwareMap.dcMotor.get("rightFrontMotor");
        rightBackMotor = hardwareMap.dcMotor.get("rightBackMotor");
        org.firstinspires.ftc.teamcode.auto.pipelines.rPropPL.PropPosition position = null;

        @Override
        public void runOpMode() {
            /**
             * NOTE: Many comments have been omitted from this sample for the
             * sake of conciseness. If you're just starting out with EasyOpenCv,
             * you should take a look at {@link InternalCamera1Example} or its
             * webcam counterpart, {@link WebcamExample} first.
             */

            /*int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
            webcam = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "Webcam 1"), cameraMonitorViewId);
            rPropPL = new rPropPL();
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
                /*}
            });

            leftBackMotor  = hardwareMap.get(DcMotor.class, "leftBackMotor");
            leftFrontMotor  = hardwareMap.get(DcMotor.class, "leftFrontMotor");
            rightBackMotor  = hardwareMap.get(DcMotor.class, "rightBackMotor");
            rightFrontMotor  = hardwareMap.get(DcMotor.class, "rightFrontMotor");

            leftFrontMotor.setDirection(DcMotor.Direction.REVERSE);
            rightFrontMotor.setDirection(DcMotor.Direction.REVERSE);
            leftBackMotor.setDirection(DcMotor.Direction.REVERSE);

            waitForStart();

            if (isStopRequested()) return;
            DcMotor slideLift       = hardwareMap.dcMotor.get("slideLift");

            while (opModeIsActive()) {
                while (!isStarted() && !isStopRequested()) {
                    position = rPropPL.returnPos();
                    telemetry.addData("PROP FOUND @", position );
                    telemetry.update();
                    sleep(50);
                }

            /*
            just change the number in chassisMove. dont mess with anything else
             */
                /*waitForStart();

                chassisMove(-1000, 1000, 1000, -1000);
                sleep(500);
                while (opModeIsActive()) {

                    chassisMove(2500, 2500, 2500, 2500);
                    sleep(500);
                    webcam.stopStreaming();

                    slideLift.setTargetPosition(-500);
                    slideLift.setPower(0.75);
                    slideLift.setMode(DcMotor.RunMode.RUN_TO_POSITION);

                    sleep(1000);

                    telemetry.addData("Auto running using", position );
                    telemetry.update();

                    if (position.toString().equals("LEFT")) {
                        drive(750,750,750,750,0.35);
                        sleep(1000);
                        drive(1350,0,1350,0,0.35);
                        sleep(1000);
                        drive(-1350,0,-1350,0,0.35);
                        sleep(50000);
                    }
                    if (position.toString().equals("CENTER")) {
                        drive(1400,1400,1400,1400,0.35);
                        sleep(1000);
                        drive(-500,-500,-500,-500,0.35);
                        sleep(50000);
                    }
                    else {
                        drive(750,750,750,750,0.35);
                        sleep(1000);
                        drive(0,1350,0,1350,0.35);
                        sleep(1000);
                        drive(0,-1350,0,-1350,0.35);
                        sleep(50000);
                    }

                    chassisMove(750, -750, -750, 750);
                    sleep(30000);
                }
            }
            public void chassisMove (int fL, int fR, int bL, int bR) {
                public void drive(int rB, int lB, int rF, int lF, double power) {

                    leftFrontMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                    rightBackMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                    leftBackMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                    rightFrontMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                    rightBackMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                    leftFrontMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

                    leftFrontMotor.setTargetPosition(fL);
                    leftBackMotor.setTargetPosition(bL);
                    rightFrontMotor.setTargetPosition(-fR);
                    rightBackMotor.setTargetPosition(-bR);
                    rightBackMotor.setTargetPosition(rB);
                    leftBackMotor.setTargetPosition(lB);
                    rightFrontMotor.setTargetPosition(rF);
                    leftFrontMotor.setTargetPosition(lF);

                    leftFrontMotor.setPower(0.5);
                    leftBackMotor.setPower(0.5);
                    rightFrontMotor.setPower(0.5);
                    rightBackMotor.setPower(0.5);
                    rightBackMotor.setPower(power);
                    leftBackMotor.setPower(power);
                    rightFrontMotor.setPower(power);
                    leftFrontMotor.setPower(power);

                    leftFrontMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    rightBackMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    leftBackMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    rightFrontMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    rightBackMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    leftFrontMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

                    // Blocking While Loop: doesn't break until all 4 motors have stopped moving
                    // Sets power of motors to 0 after the loop breaks
                    while (opModeIsActive() && (rightBackMotor.isBusy() || leftBackMotor.isBusy() || rightFrontMotor.isBusy() || leftFrontMotor.isBusy())) {
                    }

                    leftFrontMotor.setPower(0);
                    rightBackMotor.setPower(0);
                    leftBackMotor.setPower(0);
                    rightFrontMotor.setPower(0);
                    rightBackMotor.setPower(0);
                    leftFrontMotor.setPower(0);
                }
            }*/