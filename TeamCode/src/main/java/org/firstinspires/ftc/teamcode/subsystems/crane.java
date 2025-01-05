package org.firstinspires.ftc.teamcode.subsystems;

import com.ThermalEquilibrium.homeostasis.Controllers.Feedforward.FeedforwardController;
import com.ThermalEquilibrium.homeostasis.Utils.WPILibMotionProfile;


import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class crane {
    DcMotorEx sliderRight, sliderLeft;
    Servo sliderFront;
    WPILibMotionProfile motionProfile;
    ElapsedTime timer;
    HardwareMap hardwareMap;
    Telemetry telemetry;
    FeedforwardController glisieraFeedforward;
    double power;
//    PController glisieraP;
    double lastVelocity = 0, lastTime = 0;

    public crane(HardwareMap hm) {
        sliderFront = hm.get(Servo.class, "sliderFront");
        sliderLeft = hm.get(DcMotorEx.class, "sliderLeft");
        sliderLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        sliderRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        sliderRight = hm.get(DcMotorEx.class, "sliderRight");
    }
    public void setSliderFront(float pos) {
        sliderFront.setPosition(pos);
    }
    public void setSliderPosition(int pos) {
        sliderRight.setTargetPosition(pos);
        sliderLeft.setTargetPosition(pos);
    }
    public void setPower(float pow) {
        sliderRight.setPower(pow);
        sliderLeft.setPower(pow);
    }
//    public void setPosition(int ticks) {
//        glisieraFeedforward = new FeedforwardController() {
//            @Override
//            public double calculate(double x, double v, double a) {
//                return universalConstants.kV * v + universalConstants.kA * a;
//            }
//        };
//        WPILibMotionProfile.Constraints constraints =
//                new WPILibMotionProfile.Constraints(universalConstants.MAX_VEL_GLISIERA, universalConstants.MAX_ACCEL_GLISIERA);
//        WPILibMotionProfile.State goal =
//                new WPILibMotionProfile.State(ticks, 0);
//        WPILibMotionProfile.State initial =
//                new WPILibMotionProfile.State(glisieraRight.getCurrentPosition(), glisieraRight.getVelocity());
//        motionProfile =
//                new WPILibMotionProfile(constraints, goal, initial);
//        glisieraP = new PController(universalConstants.kP);
//        timer.reset();
//        lastVelocity = 0;
//        lastTime = 0;
//        glisieraRight.setTargetPosition(ticks);
//        glisieraRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//        glisieraRight.setPower(1);
//
//    }
//    public double[] calculatePower() {
//        WPILibMotionProfile.State state = motionProfile.calculate(timer.milliseconds());
//        power = glisieraFeedforward.calculate(state.position, state.velocity, state.velocity - lastVelocity) / (timer.milliseconds() - lastTime)
//                + glisieraP.calculate(glisieraRight.getCurrentPosition(), state.position)
//                + getPassivePower(glisieraRight.getCurrentPosition());
//        lastVelocity = glisieraRight.getVelocity();
//        lastTime = timer.milliseconds();
//        return new double[]{power, glisieraRight.getCurrentPosition()};
//    }
//    public double getPassivePower(int ticks) {
//        return SubsystemConstants.kG * (Math.ceil(ticks / SubsystemConstants.tickPerPerecheGlisiera)) + SubsystemConstants.carriageWeight;
//    }
//    public void updateControlLoop() {
//        glisieraRight.setPower(calculatePower()[0]);
//    }

}
