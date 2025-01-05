package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class outake {
    Servo twister, claw, armRight, armLeft, joint;
    public outake(HardwareMap hm) {
        twister = hm.get(Servo.class, "twister");
        claw = hm.get(Servo.class, "claw");
        armRight = hm.get(Servo.class, "armRight");
        armLeft = hm.get(Servo.class, "armLeft");
        joint = hm.get(Servo.class, "joint");
    }
    public void setTwister(float pos) {
        twister.setPosition(pos);
    }
    public void setClaw(float pos) {
        claw.setPosition(pos);
    }
    public void setArm(float pos) {
        armRight.setPosition(pos);
        armLeft.setPosition(pos);
    }

    public void setJoint(float pos) {
        joint.setPosition(pos);
    }
}
