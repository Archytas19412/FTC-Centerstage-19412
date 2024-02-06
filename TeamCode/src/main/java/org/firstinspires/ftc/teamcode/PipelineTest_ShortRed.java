package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvWebcam;


@Autonomous
public class PipelineTest_ShortRed extends LinearOpMode {

    DcMotor FrontL;
    DcMotor BackL;
    DcMotor BackR;
    DcMotor FrontR;
    DcMotor UpArm;
    DcMotor UpArm2;
    DcMotor ExpandArm;
    Servo ClawServo;
    CRServo intake_Lift;
    CRServo IntakeServo;
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
        UpArm = hardwareMap.get(DcMotor.class, "UpArm");
        UpArm2 = hardwareMap.get(DcMotor.class, "UpArm2");
        ExpandArm = hardwareMap.get(DcMotor.class, "ExpandArm");
        ClawServo = hardwareMap.get(Servo.class, "ClawServo");
        intake_Lift = hardwareMap.get(CRServo.class, "intake_Lift");
        IntakeServo = hardwareMap.get(CRServo.class, "IntakeServo");

        UpArm2.setDirection(DcMotorSimple.Direction.REVERSE);
        ExpandArm.setDirection(DcMotorSimple.Direction.REVERSE);
        BackR.setDirection(DcMotorSimple.Direction.REVERSE);
        FrontR.setDirection(DcMotorSimple.Direction.REVERSE);

        //Reduce slip in motors
        FrontL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BackL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BackR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        FrontR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        ClawServo.setPosition(0.2);

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
                // Go Forward
                intake_Lift.setPower(1);
                sleep(2650);
                intake_Lift.setPower(0);
                Drive(300, 300, 300, 300, 0.5);
                sleep(500);
                Drive(-200, -200, -200, -200, 0.5);
                sleep(500);
                Drive(900, 900, 900, 900, 0.5);
                // Lower Intake Roller
                sleep(2000);
                // Intake Roll Out
                IntakeServo.setPower(-1);
                sleep(500);
                // Intake Roller Stop Rolling
                IntakeServo.setPower(0);
                Drive(-100, -100, -100, -100, 0.5);
                sleep(500);
                Drive(1250, -1250, -1250, 1250, 0.5);
                sleep(1500);
                Drive(1125, -1125, 1125, -1125, 0.5);
                sleep(1500);
                Drive(-400, 400, 400, -400, 0.5);
                sleep(500);
                Drive(350, 350, 350, 350, 0.5);
                sleep(500);
                // Lift Arm
                Lift_Arm(100, 100, 1);
                sleep(1500);
                // Extend Arm
                Expand(2025, 0.8);
                sleep(1500);
                Lift_Arm(-100, 100, 0.5);
                Drive(-275, -275, -275, -275, 0.2);
                sleep(1500);
                // Open Claw
                ClawServo.setPosition(0);
                sleep(1000);
                Lift_Arm(100, 100, 0.75);
                ClawServo.setPosition(0.2);
                Expand(-2025, 1);
                sleep(1500);
                Lift_Arm(-100, 100, 0.75);
                Drive(1250, -1250, -1250, 1250, 0.5);
                sleep(30000);
            }
            else {
                // Go Forward
                intake_Lift.setPower(1);
                sleep(2650);
                intake_Lift.setPower(0);
                Drive(300, 300, 300, 300, 0.5);
                sleep(500);
                Drive(-200, -200, -200, -200, 0.5);
                sleep(1000);
                Drive(525, -525, -525, 525, 0.5);
                // Lower Intake Roller
                sleep(1500);
                Drive(725, 725, 725, 725, 0.5);
                sleep(1500);
                // Intake Roll Out
                IntakeServo.setPower(-1);
                sleep(500);
                // Intake Roller Stop Rolling
                IntakeServo.setPower(0);
                Drive(-100, -100, -100, -100, 0.5);
                sleep(500);
                Drive(725, -725, -725, 725, 0.5);
                sleep(1500);
                Drive(1125, -1125, 1125, -1125, 0.5);
                sleep(1500);
                Drive(-250, 250, 250, -250, 0.5);
                sleep(500);
                Drive(350, 350, 350, 350, 0.5);
                sleep(500);
                // Lift Arm
                Lift_Arm(100, 100, 1);
                sleep(1500);
                // Extend Arm
                Expand(2025, 0.8);
                sleep(1500);
                Lift_Arm(-100, 100, 0.5);
                Drive(-275, -275, -275, -275, 0.2);
                sleep(1500);
                // Open Claw
                ClawServo.setPosition(0);
                sleep(1000);
                Lift_Arm(100, 100, 0.75);
                Expand(-2025, 1);
                sleep(1500);
                Lift_Arm(-100, 100, 0.75);
                ClawServo.setPosition(0.2);
                Drive(1100, -1100, -1100, 1100, 0.5);
                sleep(30000);
            }

        }
    }

    /**
     * @param FrontLTarget: amount of ticks for the front left wheel (+ = forward & - = backwards)
     * @param FrontRTarget: amount of ticks for the front right wheel (+ = forward & - = backwards)
     * @param BackLTarget: amount of ticks for the back left wheel (+ = forward & - = backwards)
     * @param BackRTarget: amount of ticks for the back right wheel (+ = forward & - = backwards)
     * @param Speed: the speed/power of all the motors
     */
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
    }

    /**
     * @param ExpandArmTarget: amount of ticks for the arm to extend (+ = forward & - = backwards)
     * @param Speed: speed/power of the motor
     */
    private void Expand(int ExpandArmTarget, double Speed) {
        ExpandArm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        ExpandArm.setTargetPosition(ExpandArmTarget);
        ExpandArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        ExpandArm.setPower(Speed);
    }

    /**
     * @param UpArmTarget: amount of ticks for one of the motor lifting the arm (+ = forward & - = backwards)
     * @param UpArm2Target: amount of ticks for one of the motor lifting the arm (+ = forward & - = backwards)
     * @param Speed: the speed/power for all the motors
     */
    private void Lift_Arm(int UpArmTarget, int UpArm2Target, double Speed) {
        UpArm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        UpArm2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        UpArm.setTargetPosition(UpArmTarget);
        UpArm2.setTargetPosition(UpArm2Target);
        UpArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        UpArm2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        UpArm.setPower(Speed);
        UpArm2.setPower(Speed);
    }
}