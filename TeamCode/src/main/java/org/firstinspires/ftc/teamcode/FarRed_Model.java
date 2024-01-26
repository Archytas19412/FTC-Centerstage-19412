package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.tfod.TfodProcessor;

import java.util.List;

@Autonomous(name = "FarRed_Model")
public class FarRed_Model extends LinearOpMode {

    //Motors and servo variables
    DcMotor FrontL;
    DcMotor BackL;
    DcMotor BackR;
    DcMotor FrontR;
    Servo ClawServo;

    //Location of the robot (default)
    private double position = -100000;
    //Separate the webcam's view into left, middle, and right sections
    private final double rightSignal = 270;

    //Tensor flow object detection variable
    private TfodProcessor tfod;
    //Variable to create visionportal
    private VisionPortal visionPortal;
    //Variable to measure the webcam's confidence in identifying the object
    private double confidence = 0;
    //Variable to indicate how many object it recognizes
    private int numRecognize = 0;

    //Create a list with the label of our model
    private static final String[] MODEL = {"RedOwl"};

    @Override
    public void runOpMode() {
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

        //Claw close on pixel
        ClawServo.setPosition(0.2);

        //This function set up the tfod processor and VisionPortal
        initTfod();

        //Scan objects while the robot is not active
        while (!opModeIsActive()) {
            scanForObjects();
        }

        waitForStart();

        //Robot go to the middle spike
        if (position > rightSignal){
            // Go Forward
            Drive(1250, 1250, 1250, 1250, 0.5);
            sleep(2000);
            // Open Claw
            ClawServo.setPosition(0);
            sleep(500);
            // Go Backward
            Drive(-1100, -1100, -1100, -1100, 0.5);
            sleep(3000);
            //Strafe Left
            Drive(-700, 700, 700, -700, 0.5);
            sleep(3000);
            //Go Forward
            Drive(2200, 2200, 2200, 2200, 0.5);
            sleep(3500);
            //Strafe Right
            Drive(500, -500, -500, 500, 0.5);
            sleep(1000);
            //Turn Right
            Drive(1125, -1125, 1125, -1125, 0.5);
            sleep(3000);
            //Go Forward
            Drive(4000, 4000, 4000, 4000, 0.5);
            sleep(30000);
        }
        //Robot go to the right spike
        else if(position < rightSignal){
            // Turn Right
            Drive(1070, -1070, 1070, -1070, 0.5);
            sleep(1500);
            // Go Backward
            Drive(-200, -200, -200, -200, 0.5);
            sleep(500);
            // Strafe Left
            Drive(-1600, 1600, 1600, -1600, 0.5);
            sleep(3000);
            // Go Forward
            Drive(100, 100, 100, 100, 0.5);
            sleep(300);
            // Open Claw
            ClawServo.setPosition(0);
            sleep(100);
            // Go Backward
            Drive(-100, -100, -100, -100, 0.5);
            sleep(30000);
        }
        //Robot go to the left spike
        else if(confidence < 0.80){
            // Turn Left
            Drive(-1070, 1070, -1070, 1070, 0.5);
            sleep(1500);
            // Go Forward
            Drive(100, 100, 100, 100, 0.5);
            sleep(100);
            // Strafe Right
            Drive(1600, -1600, -1600, 1600, 0.5);
            sleep(3000);
            // Go Forward
            Drive(170, 170, 170, 170, 0.5);
            sleep(1000);
            // Open Claw
            ClawServo.setPosition(0);
            sleep(500);
            // Go backwards
            Drive(-100, -100, -100, -100, 0.5);
            sleep(30000);
        }
    }

    /**
     * @param FrontLTarget: the position desired for the front left wheel to turn towards
     * @param FrontRTarget: the position desired for the front right wheel to turn towards
     * @param BackLTarget: the position desired for the back left wheel to turn towards
     * @param BackRTarget: the position desired for the back right wheel to turn towards
     * @param Speed: the speed and power the wheel will be going at
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
    private void initTfod() {

        //Create the tensorflow object processor
        tfod = new TfodProcessor.Builder()

                // Use setModelAssetName() if the TF Model is built in as an asset.
                // Use setModelFileName() if you have downloaded a custom team model to the Robot Controller.
                //.setModelAssetName(TFOD_MODEL_ASSET)
                .setModelFileName("RedModel.tflite")

                .setMaxNumRecognitions(4)
                .setTrackerMaxOverlap(0.25f)
                .setModelLabels(MODEL)
                .setNumDetectorThreads(4)
                .setNumExecutorThreads(4)
//
                .build();

        //Create the vision portal
        VisionPortal.Builder builder = new VisionPortal.Builder();

        // Set the camera (webcam).
        builder.setCamera(hardwareMap.get(WebcamName.class, "Webcam 1"));

        //Set and Enable processor
        builder.addProcessor(tfod);

        //Build VisionPortal
        visionPortal = builder.build();
    }
    //Scan for any object the webcam recognizes
    private boolean scanForObjects() {
        List<Recognition> recognitions = tfod.getRecognitions();
        numRecognize = recognitions.size();

        telemetry.addData("# of Objects Detected", numRecognize);

        if(recognitions.isEmpty()){
            return false;
        }

        //Go through a list of models and display label, confidence, position, and size for each
        for(Recognition model : recognitions){
            //Robot set the location and confidence here
            position = (model.getLeft() + model.getRight()) / 2;
            confidence = model.getConfidence();

            telemetry.addData("", " ");
            telemetry.addData("Object", "%s (%.Of %% COnf.)", model.getLabel(), model.getConfidence() * 100);
            telemetry.addData("- Position", "%.Of", position);
            telemetry.addData("- Size", "%.Of x %.Of", model.getWidth(), model.getHeight());
            break;
        }
        return true;
    }
}