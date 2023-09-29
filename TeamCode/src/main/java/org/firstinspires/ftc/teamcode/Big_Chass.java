package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp
public class Big_Chass extends LinearOpMode {
    Servo ClawServo;
    Servo LaunchSwitch;
    Servo LaunchRaiser;
    DcMotor BackR;
    DcMotor FrontR;
    DcMotor FrontL;
    DcMotor BackL;
    DcMotor UpArm2;
    DcMotor UpArm;
    DcMotor ExpandArm;

     //This function is executed when this Op Mode is selected from the Driver Station.
    @Override
    public void runOpMode() {

        //all variables for motion and assign Servo/DcMotor variable to the motor of the same name
        float vertical;
        float strafe;
        float turn;

        ClawServo = hardwareMap.get(Servo.class, "Claw Servo");
        LaunchSwitch = hardwareMap.get(Servo.class,"LaunchSwitch");
        LaunchRaiser = hardwareMap.get(Servo.class,"LaunchRaiser");
        BackR = hardwareMap.get(DcMotor.class, "BackR");
        FrontR = hardwareMap.get(DcMotor.class, "FrontR");
        FrontL = hardwareMap.get(DcMotor.class, "FrontL");
        BackL = hardwareMap.get(DcMotor.class, "BackL");
        UpArm2 = hardwareMap.get(DcMotor.class, "UpArm2");
        UpArm = hardwareMap.get(DcMotor.class, "UpArm");
        ExpandArm = hardwareMap.get(DcMotor.class, "ExpandArm");

        // Put initialization blocks here.
        ClawServo.setPosition(0);
        waitForStart();
        if (opModeIsActive()) {

            // Put run blocks here.
            BackR.setDirection(DcMotorSimple.Direction.REVERSE);
            FrontR.setDirection(DcMotorSimple.Direction.REVERSE);
            FrontL.setDirection(DcMotorSimple.Direction.FORWARD);
            BackL.setDirection(DcMotorSimple.Direction.REVERSE);
            UpArm2.setDirection(DcMotorSimple.Direction.REVERSE);

            while (opModeIsActive()) {

                /* Put loop blocks here.
                Forward/backwards = Gamepad 1's Left stick (Up and down)
                Strafe = Gamepad 1's Left stick (Left and right)
                Turn = Gamepad 1's Right Stick (Right and Left)*/
                vertical = gamepad1.left_stick_y;
                strafe = gamepad1.left_stick_x;
                turn = gamepad1.right_stick_x;

                /* + forward/backward + strafe + turning (negative reverse wheel motion)
                # X ... =  set the power (below 1 makes it use less power, thus, go slower by nature)*/
                BackR.setPower(0.6 * (vertical - (strafe - turn)));
                FrontR.setPower(0.6 * (vertical + (strafe + turn)));
                BackL.setPower(0.6 * (vertical + (strafe - turn)));
                FrontL.setPower(0.6 * (vertical - (strafe + turn)));
                // Lift/put down the arm = Gamepad 2's Left Stick (Up and down)
                // Extend/shrink the arm length = Gamepad 2's Right Stick (Up and Down)
                UpArm.setPower(-0.6 * gamepad2.left_stick_y);
                UpArm2.setPower(-0.6 * gamepad2.left_stick_y);
                ExpandArm.setPower(-0.6 * gamepad2.right_stick_y);

                /* Close claw = Gaming Pad 2's Left Bumper
                Open claw = Gaming Pad 2's Right Bumper
                0-1 on a line. 0 = Left & 1 = Right (Theoretically, so use a tape marker to test the placement)
                A = FLick drone launcher switch
                B = Flick drone launch switch to original position
                C = activate the lever to raise the drone launcher in a 45 degree angle*/
                if (gamepad2.left_bumper) {
                    ClawServo.setPosition(0.2);
                }
                if (gamepad2.right_bumper) {
                    ClawServo.setPosition(0);
                }
                if(gamepad2.a){
                    LaunchSwitch.setPosition(0.08);
                }
                if(gamepad2.b){
                    LaunchRaiser.setPosition(1);
                }
                telemetry.update();
            }
        }
    }
}