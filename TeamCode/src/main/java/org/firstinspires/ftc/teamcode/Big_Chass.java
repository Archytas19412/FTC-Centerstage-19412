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
    CRServo IntakeServo;
    CRServo Intake_Lift;

    //Hanging arm variables
    Servo Hang_Top;
    Servo Hang_Bottom;
    DcMotor RopeMotor;

    //Variables of the wheels
    DcMotor BackR;
    DcMotor FrontR;
    DcMotor FrontL;
    DcMotor BackL;

    //This function is executed when this Op Mode is selected from the Driver Station (triangle button pressed)
    public void init() {
        ClawServo = hardwareMap.servo.get("ClawServo");
        LaunchSwitch = hardwareMap.servo.get("LaunchSwitch");
        LaunchRaiser = hardwareMap.servo.get("LaunchRaiser");
        UpArm2 = hardwareMap.dcMotor.get("UpArm2");
        UpArm = hardwareMap.dcMotor.get("UpArm");
        ExpandArm = hardwareMap.dcMotor.get("ExpandArm");

        IntakeServo = hardwareMap.crservo.get("IntakeServo");
        Intake_Lift = hardwareMap.crservo.get("intake_Lift");

        Hang_Top = hardwareMap.servo.get("Hang_Top");
        Hang_Bottom = hardwareMap.servo.get("Hang_Bottom");
        RopeMotor = hardwareMap.dcMotor.get("RopeMotor");

        BackR = hardwareMap.dcMotor.get("BackR");
        FrontR = hardwareMap.dcMotor.get("FrontR");
        FrontL = hardwareMap.dcMotor.get("FrontL");
        BackL = hardwareMap.dcMotor.get("BackL");

        LaunchSwitch.setDirection(Servo.Direction.REVERSE);

        //Initial Positions
        ClawServo.setPosition(0);
        LaunchSwitch.setPosition(0.9);
        Hang_Top.setPosition(0.05);
        Hang_Bottom.setPosition(0.55);
        LaunchRaiser.setPosition(0);

        UpArm2.setDirection(DcMotorSimple.Direction.REVERSE);
        ExpandArm.setDirection(DcMotorSimple.Direction.REVERSE);
        BackR.setDirection(DcMotorSimple.Direction.REVERSE);
        FrontR.setDirection(DcMotorSimple.Direction.REVERSE);

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

        /*Gamepad1 Right Trigger = Turn the gears attach to the rope to reel the hook back
         *Gamepad1 Left Trigger = Turn the gears to release the rope
         */
        RopeMotor.setPower(1 * gamepad1.right_trigger);
        RopeMotor.setPower(-1 * gamepad1.left_trigger);

        // Lift put down the arm = Gamepad 2's Left Stick (Up and down)
        // Extend/shrink the arm length = Gamepad 2's Right Stick (Up and Down)
        UpArm.setPower(-0.8 * gamepad2.left_stick_y);
        UpArm2.setPower(-0.8 * gamepad2.left_stick_y);
        ExpandArm.setPower(-0.6 * gamepad2.right_stick_y);

        //Controller 1 Button X = set the hanging arm back to original position
        if(gamepad1.x){
            Hang_Bottom.setPosition(0.55);
            Hang_Top.setPosition(0.05);
        }

        //Controller 1 Button A = Lift the bottom part of the hanging arm up
        if(gamepad1.a){
            Hang_Bottom.setPosition(0.8);
        }

        //Controller 1 Button B = Lift the top part of the hanging arm up
        if(gamepad1.b){
            Hang_Top.setPosition(0.6);
        }

        //Gaming Pad 2's Right Bumper = Close Claw
        if (gamepad2.right_bumper) {
            ClawServo.setPosition(0.2);
        }

        //Gaming Pad 2's Left Bumper = Open Claw
        if (gamepad2.left_bumper) {
            ClawServo.setPosition(0);
        }

        //Controller 2 Button A = Flick drone launcher switch up
        if(gamepad2.a){
            LaunchSwitch.setPosition(0.8);
        }

        //Controller 2 Button B = Flick drone launcher up
        if(gamepad2.b){
            LaunchRaiser.setPosition(0.04);
        }

        //Controller 2 Button Y = reverse intake pixel roller's rotation
        if(gamepad2.y){
            IntakeServo.setPower(-1);
        }
        //Controller 1 Button X = activate the intake pixel roller to roll forward to get pixels
        else if (gamepad2.x){
            IntakeServo.setPower(1);
        } else{
            IntakeServo.setPower(0);
        }

        //Controller 1 Dpad Up Button = Release rope to lower the intake roller
        if(gamepad1.dpad_up){
            Intake_Lift.setPower(1);
        }
        //Controller 1 Dpad Down Button = Reel rope to lift the intake roller
        else if(gamepad1.dpad_down){
            Intake_Lift.setPower(-1);
        }
        else{
            Intake_Lift.setPower(0);
        }

        telemetry.update();
    }
}