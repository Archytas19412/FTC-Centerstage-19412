package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.tfod.TfodProcessor;

@Autonomous
public class ShortRed_Model extends LinearOpMode {
    DcMotor FrontL;
    DcMotor BackL;
    DcMotor BackR;
    DcMotor FrontR;
    Servo ClawServo;

    private double position = -100000;
    private final double turnSignal = 270;
    private TfodProcessor tfod;
    private VisionPortal visionPortal;
    private double confidence = 0;
    private int numRecognize = 0;

    //Model we are looking for
    private static final String[] MODEL = {"Archytas_Red Model"};

    @Override
    public void runOpMode() {
        FrontL = hardwareMap.get(DcMotor.class, "FrontL");
        BackL = hardwareMap.get(DcMotor.class, "BackL");
        BackR = hardwareMap.get(DcMotor.class, "BackR");
        FrontR = hardwareMap.get(DcMotor.class, "FrontR");
        ClawServo = hardwareMap.get(Servo.class, "Claw Servo");

        FrontL.setDirection(DcMotor.Direction.REVERSE);
        BackL.setDirection(DcMotor.Direction.REVERSE);

        //Claw close on pixel
        ClawServo.setPosition(0.2);

        //This function set up the tfod processor and VisionPortal
        initTfod();

        //Scan objects while the robot is not active
        while (!opModeIsActive()) {
            scanForObjects();
        }

        waitForStart();

        //Robot go to the middle spike if an object is detected
        if (position == -100000 || confidence < 0.9) {
            sleep(500);
            //GO forward
            Drive(1250, 1250, 1250, 1250, 0.5);
            sleep(2000);
            //Claw release pixel
            ClawServo.setPosition(0);
            sleep(500);
            //Go backward
            Drive(-1100, -1100, -1100, -1100, 0.5);
            sleep(30000);
            //Strafe right
            Drive(2000, -2000, -2000, 2000, 0.5);
            sleep(30000);
        }
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
    }
    private void scanForObjects() {
    }
}
