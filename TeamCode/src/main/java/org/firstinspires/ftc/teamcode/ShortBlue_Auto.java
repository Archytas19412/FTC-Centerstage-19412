package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@Autonomous
public class ShortBlue_Auto extends LinearOpMode{
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
        // Put initialization blocks here.
        waitForStart();
        if (opModeIsActive()) {
            // Put run blocks here.
            while (!isStopRequested()) {
                // Put loop blocks here.
                BackR.setPower(-0.5);
                FrontR.setPower(0.5);
                BackL.setPower(-0.5);
                FrontL.setPower(-0.5);
                sleep(2000);
                ClawServo.setPosition(0);
                BackR.setPower(0);
                FrontR.setPower(0);
                BackL.setPower(0);
                FrontL.setPower(0);
                sleep(3000);
                BackR.setPower(0.5);
                FrontR.setPower(0.5);
                BackL.setPower(0.5);
                FrontL.setPower(0.5);
                sleep(250);
                BackR.setPower(-0.5);
                FrontR.setPower(-0.5);
                BackL.setPower(0.5);
                FrontL.setPower(-0.5);
                sleep(250);
                BackR.setPower(0);
                FrontR.setPower(0);
                BackL.setPower(0);
                FrontL.setPower(0);
                sleep(30000);
                telemetry.update();
            }
        }
    }
}
