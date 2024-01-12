package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@Autonomous (name = "FarRed_Auto")
public class FarRed_Auto extends LinearOpMode{
    DcMotor FrontL;
    DcMotor BackL;
    DcMotor BackR;
    DcMotor FrontR;
    Servo ClawServo;

    @Override
    public void runOpMode() {
        FrontL = hardwareMap.get(DcMotor.class, "FrontL");
        BackL = hardwareMap.get(DcMotor.class, "BackL");
        BackR = hardwareMap.get(DcMotor.class, "BackR");
        FrontR = hardwareMap.get(DcMotor.class, "FrontR");
        ClawServo = hardwareMap.get(Servo.class, "ClawServo");

        FrontL.setDirection(DcMotorSimple.Direction.REVERSE);
        BackL.setDirection(DcMotorSimple.Direction.REVERSE);

        // Put initialization blocks here.
        waitForStart();
        if (opModeIsActive()) {
            //Start the Sequence until the end or stopped
            while (!isStopRequested()) {
                //Close claw and go backward
                FrontL.setPower(-0.5);
                BackL.setPower(0.5);
                FrontR.setPower(0.5);
                BackR.setPower(-0.5);
                sleep(2000);
                //Open claw and continues to go backward
                FrontL.setPower(-1);
                BackL.setPower(-1);
                FrontR.setPower(-1);
                BackR.setPower(-1);
                sleep(2000);
                //Strafe left
                FrontL.setPower(0.5);
                BackL.setPower(-0.5);
                FrontR.setPower(-0.5);
                BackR.setPower(0.5);
                sleep(2000);

                telemetry.update();
            }

        }

    }
}