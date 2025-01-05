package org.firstinspires.ftc.teamcode.teleop;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Libs.GAMEPAD;
import org.firstinspires.ftc.teamcode.roadrunner.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.subsystems.*;

@TeleOp(name="MAIN", group = "!")
public class teleop extends LinearOpMode {
    int glisieraTargetPos = 0;
    statesOuttake reqState = null;
    mode modeRob = mode.BASKET;
    statesOuttake outtakeState = statesOuttake.DEFAULT;
    SampleMecanumDrive drive;
    GAMEPAD GAMEPAD1, GAMEPAD2;
    crane crane;
    outake outake;
    @Override
    public void runOpMode() throws InterruptedException {
        telemetry = new MultipleTelemetry(this.telemetry, FtcDashboard.getInstance().getTelemetry());

        drive = new SampleMecanumDrive(hardwareMap);
        GAMEPAD1 = new GAMEPAD(this.gamepad1, telemetry);
        GAMEPAD2 = new GAMEPAD(this.gamepad2, telemetry);
        crane = new crane(hardwareMap);
        outake = new outake(hardwareMap);
        waitForStart();
        while (opModeIsActive() && !isStopRequested()) {
            GAMEPAD1.run();
            GAMEPAD2.run();
            outake();
            telemetry();
        }
    }
    public void telemetry() {
        telemetry.addData("mode", modeRob);
    }
    public void outake() {
        if(gamepad2.right_trigger > 0.3) {
            if(modeRob == mode.BASKET) {
                modeRob = mode.CHAMBER;
            }
            else if(modeRob == mode.CHAMBER) {
                modeRob = mode.BASKET;
            }
        }
        if(GAMEPAD2.a.value) {
            reqState = statesOuttake.HIGH;
        }
        if(GAMEPAD2.b.value) {
            reqState = statesOuttake.LOW;
        }
        if(GAMEPAD1.right_bumper.toggle && reqState != null) {
            switch (reqState) {
                case HIGH:
                    switch (modeRob) {
                        case BASKET:
                            // TODO: SET GLISIERA POS
                        case CHAMBER:
                            // TODO: SET GLISIERA POS

                    }
                    // TODO:
                    outtakeState = statesOuttake.HIGH;
                    reqState = null;
                case LOW:
                    switch (modeRob) {
                        case BASKET:

                        case CHAMBER:

                    }
                    outtakeState = statesOuttake.LOW;
                    reqState = null;
            }
        } else if(!GAMEPAD1.right_bumper.toggle && reqState == null && outtakeState != statesOuttake.DEFAULT) {
            reqState = outtakeState;
            outtakeState = statesOuttake.DEFAULT;
        }
    }
    enum statesOuttake {
        DEFAULT,
        LOW,
        HIGH
    }
    enum mode {
        BASKET,
        CHAMBER
    }
}
