package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@Autonomous (name = "ShortBlue_Model")
public class ShortBlue_Model extends LinearOpMode{
    DcMotor FrontL;
    DcMotor BackL;
    DcMotor BackR;
    DcMotor FrontR;
    Servo ClawServo;



    public void Drive(int FrontLTarget,int FrontRTarget,int BackLTarget,int BackRTarget,double Speed){
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

    @Override
    public void runOpMode() {
        FrontL = hardwareMap.get(DcMotor.class, "FrontL");
        BackL = hardwareMap.get(DcMotor.class, "BackL");
        BackR = hardwareMap.get(DcMotor.class, "BackR");
        FrontR = hardwareMap.get(DcMotor.class, "FrontR");
        ClawServo = hardwareMap.get(Servo.class, "Claw Servo");

        FrontL.setDirection(DcMotorSimple.Direction.REVERSE);
        BackL.setDirection(DcMotorSimple.Direction.REVERSE);

        //Once the driver hub is initialized
        waitForStart();
        //Once the driver hub is activated
        if (opModeIsActive()) {
            //Run the code in a sequence until asked to stop
            while (!isStopRequested()) {

                //Claw grab pixel
                ClawServo.setPosition(0.2);
                sleep(500);
                //GO forward
                Drive(1250,1250,1250,1250,0.5);
                sleep(2000);
                //Claw release pixel
                ClawServo.setPosition(0);
                sleep(500);
                //Go backward
                Drive(-1100,-1100, -1100,-1100,0.5);
                sleep(30000);
                //Strafe left
                Drive(-2000,2000,2000,-2000,0.5);
                sleep(30000);
                telemetry.update();
            }
        }
    }
}