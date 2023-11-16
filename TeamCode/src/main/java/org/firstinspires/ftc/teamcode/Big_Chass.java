package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp (name = "Big_Chass")
public class Big_Chass extends OpMode {
    //Variables for the arm
    Servo ClawServo;
    Servo LaunchSwitch;
    Servo LaunchRaiser;
    DcMotor UpArm2;
    DcMotor UpArm;
    DcMotor ExpandArm;

    //Variable for intake box
    CRServo intakeServo;

    //Variables of the wheels
    DcMotor BackR;
    DcMotor FrontR;
    DcMotor FrontL;
    DcMotor BackL;

    //Variables for encoders
    //This function is executed when this Op Mode is selected from the Driver Station.
    public void init() {
        ClawServo = hardwareMap.servo.get("Claw Servo");
        LaunchSwitch = hardwareMap.servo.get("LaunchSwitch");
        LaunchRaiser = hardwareMap.servo.get("LaunchRaiser");
        UpArm2 = hardwareMap.dcMotor.get("UpArm2");
        UpArm = hardwareMap.dcMotor.get("UpArm");
        ExpandArm = hardwareMap.dcMotor.get("ExpandArm");
        intakeServo = hardwareMap.crservo.get("intakeServo");
        BackR = hardwareMap.dcMotor.get("BackR");
        FrontR = hardwareMap.dcMotor.get("FrontR");
        FrontL = hardwareMap.dcMotor.get("FrontL");
        BackL = hardwareMap.dcMotor.get("BackL");

        ClawServo.setPosition(0);
        LaunchSwitch.setPosition(1);

        UpArm2.setDirection(DcMotorSimple.Direction.REVERSE);
        ExpandArm.setDirection(DcMotorSimple.Direction.REVERSE);
        BackR.setDirection(DcMotorSimple.Direction.REVERSE);
        FrontR.setDirection(DcMotorSimple.Direction.REVERSE);
        BackL.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    public void loop(){
        /* Forward/backwards = Gamepad 1's Left stick (Up and Down)
          Strafe = Gamepad 1's Left stick (Left and Right)
          Turn = Gamepad 1's Right Stick (Right and Left)*/
        double vertical = gamepad1.left_stick_y;
        double strafe = gamepad1.left_stick_x;
        double turn = gamepad1.right_stick_x;

        /* + forward/backward + strafe + turning (negative reverse wheel motion)
           # X ... =  set the power (below 1 makes it use less power, thus, go slower by nature)*/
        BackR.setPower(0.6 * (vertical - (strafe - turn)));
        FrontR.setPower(0.6 * (vertical + (strafe + turn)));
        BackL.setPower(0.6 * (vertical + (strafe - turn)));
        FrontL.setPower(0.6 * (vertical - (strafe + turn)));

        // Lift put down the arm = Gamepad 2's Left Stick (Up and down)
        // Extend/shrink the arm length = Gamepad 2's Right Stick (Up and Down)
        UpArm.setPower(-0.8 * gamepad2.left_stick_y);
        UpArm2.setPower(-0.8 * gamepad2.left_stick_y);
        ExpandArm.setPower(-0.6 * gamepad2.right_stick_y);

        /* Close claw = Gaming Pad 2's Right Bumper
           Open claw = Gaming Pad 2's Left Bumper
           A = Flick drone launcher switch up
           B = Flick drone launch switch to original position
           X = activate the intake pixel roller to roll forward to grad pixels
           Y = deactivate intake pixel roller*/
        if (gamepad2.right_bumper) {
            ClawServo.setPosition(0.2);
        }
        if (gamepad2.left_bumper) {
            ClawServo.setPosition(0);
        }
        if(gamepad2.a){
            LaunchSwitch.setPosition(0.8);
        }
        if(gamepad2.b){
            LaunchRaiser.setPosition(0.55);
        }
        if(gamepad2.y){
            intakeServo.setPower(-1);
        } else if (gamepad2.x){
            intakeServo.setPower(1);
        } else{
            intakeServo.setPower(0);
        }

        telemetry.update();
    }
}