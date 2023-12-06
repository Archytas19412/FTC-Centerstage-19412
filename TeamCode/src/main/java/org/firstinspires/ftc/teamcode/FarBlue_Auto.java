package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@Autonomous (name = "FarBlue_Auto")
public class FarBlue_Auto extends LinearOpMode{
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
        ClawServo = hardwareMap.get(Servo.class, "Claw Servo");

        FrontL.setDirection(DcMotorSimple.Direction.REVERSE);
        BackL.setDirection(DcMotorSimple.Direction.REVERSE);

        waitForStart();
        if (opModeIsActive()) {
            while (!isStopRequested()) {
                //Claw close and go backward
                ClawServo.setPosition(0.2);
                FrontL.setPower(-1);
                BackL.setPower(-1);
                FrontR.setPower(-1);
                BackR.setPower(-1);
                sleep(250);
                //Claw open and continues to go backward
                ClawServo.setPosition(0);
                FrontL.setPower(-1);
                BackL.setPower(-1);
                FrontR.setPower(-1);
                BackR.setPower(-1);
                sleep(250);
                //Strafe right
                FrontL.setPower(0.5);
                BackL.setPower(-0.5);
                FrontR.setPower(-0.5);
                BackR.setPower(0.5);
                sleep(250);

                telemetry.update();
            }
        }
    }
}
