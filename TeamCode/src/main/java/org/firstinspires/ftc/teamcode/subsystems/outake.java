package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class outake {
    Servo twist, claw, bratDreapta, bratStanga, pivot;
    public outake(HardwareMap hm) {
        twist = hm.get(Servo.class, "twist");
        claw = hm.get(Servo.class, "claw");
        bratDreapta = hm.get(Servo.class, "bratDreapta");
        bratStanga = hm.get(Servo.class, "bratStanga");
        pivot = hm.get(Servo.class, "pivot");
    }
    public void setTwist(double pos) {
        twist.setPosition(pos);
    }
    public void setClaw(float pos) {
        claw.setPosition(pos);
    }
    public void setBrat(double pos) {
        bratDreapta.setPosition(pos);
        bratStanga.setPosition(pos);
    }

    public void setPivot(double pos) {pivot.setPosition(pos);
    }
}
