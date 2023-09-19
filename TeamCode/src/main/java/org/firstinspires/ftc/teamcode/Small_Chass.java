package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp (name = "Small_Chass")
public class Small_Chass extends OpMode {

    DcMotor BackR;
    DcMotor FrontR;
    DcMotor BackL;
    DcMotor FrontL;

    public void init(){
        BackR = hardwareMap.dcMotor.get("BackR");
        FrontR = hardwareMap.dcMotor.get("FrontR");
        BackL = hardwareMap.dcMotor.get("BackL");
        FrontL = hardwareMap.dcMotor.get("FrontL");

        FrontR.setDirection(DcMotor.Direction.REVERSE);
    }

    public void loop(){
        double vertical = gamepad1.left_stick_y;
        double strafe = gamepad1.left_stick_x;
        double turn = gamepad1.right_stick_x;

        BackR.setPower(0.5 * (vertical - strafe + turn));
        FrontR.setPower(0.5 * (vertical + strafe + turn));
        BackL.setPower(0.5 * (vertical + strafe - turn));
        FrontL.setPower(0.5 * (vertical - strafe - turn));
    }
}
