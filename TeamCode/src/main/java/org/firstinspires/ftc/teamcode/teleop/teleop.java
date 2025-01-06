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
    statesSequence sequenceState = statesSequence.DONE;
    mode modeRob = mode.BASKET;
    statesOuttake outtakeState = statesOuttake.DEFAULT;
    SampleMecanumDrive drive;
    GAMEPAD GAMEPAD1, GAMEPAD2;
    crane crane;
    outake outake;
    ElapsedTime timer;
    double posBrat, posTwist, posPivot, posGlisieraOriz;
    @Override
    public void runOpMode() throws InterruptedException {
        telemetry = new MultipleTelemetry(this.telemetry, FtcDashboard.getInstance().getTelemetry());

        drive = new SampleMecanumDrive(hardwareMap);
        GAMEPAD1 = new GAMEPAD(this.gamepad1, telemetry);
        GAMEPAD2 = new GAMEPAD(this.gamepad2, telemetry);
        crane = new crane(hardwareMap);
        outake = new outake(hardwareMap);
        timer = new ElapsedTime();
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
        telemetry.addData("wait step", sequenceState);
        telemetry.addData("req state", reqState);
        telemetry.addData("robot state", outtakeState);
        // Ca sa adaugi ceva in telemetrie trebuie sa i dai si update
        telemetry.update();
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
                            if(outtakeState == statesOuttake.DEFAULT) {
                                sequenceState = statesSequence.WAITING1;
                            } else {
                           // crane.setSliderPosition(universalConstants.GLISIERA_HIGH_BASKET);
                            posBrat = universalConstants.bratSus;
                            posTwist = universalConstants.twistScore;
                            posPivot = universalConstants.pivotSus;
                            posGlisieraOriz = universalConstants.GLISIERA_ORIZ_FORWARD;

                            }
                            timer.reset();
                        case CHAMBER:
                            if(outtakeState == statesOuttake.DEFAULT) {
                                sequenceState = statesSequence.WAITING1;
                            } else {
                                //  crane.setSliderPosition(universalConstants.GLISIERA_HIGH_CHAMBER);
                                posBrat = universalConstants.bratIntermediary;
                                posTwist =  universalConstants.twistScore;
                                posPivot  = universalConstants.pivotJos;
                                posGlisieraOriz= universalConstants.GLISIERA_ORIZ_FORWARD;

                            }
                            timer.reset();


                    }
                    // TODO:
                    outtakeState = statesOuttake.HIGH;
                    reqState = null;
                case LOW:
                    switch (modeRob) {
                        case BASKET:
                            if(outtakeState == statesOuttake.DEFAULT) {
                                sequenceState = statesSequence.WAITING1;
                            } else {
                               // crane.setSliderPosition(universalConstants.GLISIERA_LOW_BASKET);
                                posBrat = universalConstants.bratSus;
                                posTwist =  universalConstants.twistScore;
                                posPivot = universalConstants.pivotSus;
                                posGlisieraOriz = universalConstants.GLISIERA_ORIZ_FORWARD;

                            }
                            timer.reset();
                        case CHAMBER:
                            if(outtakeState == statesOuttake.DEFAULT) {
                                sequenceState = statesSequence.WAITING1;
                            } else {
                               // crane.setSliderPosition(universalConstants.GLISIERA_HIGH_CHAMBER);
                                posBrat = universalConstants.bratIntermediary;
                                posTwist=  universalConstants.twistScore;
                                posPivot = universalConstants.pivotJos;
                                posGlisieraOriz=  universalConstants.GLISIERA_ORIZ_FORWARD;

                            }
                            timer.reset();
                    }
                    outtakeState = statesOuttake.LOW;
                    reqState = null;
            }
        } else if(!GAMEPAD1.right_bumper.toggle && reqState == null && outtakeState != statesOuttake.DEFAULT) {
            reqState = outtakeState;
            outtakeState = statesOuttake.DEFAULT;
           // crane.setSliderPosition(universalConstants.GLISIERA_DEFAULT);
            outake.setBrat(universalConstants.bratIntermediary);
            outake.setTwist(universalConstants.twistDef);
            outake.setPivot(universalConstants.pivotJos);
            crane.setglisieraOriz(universalConstants.GLISIERA_ORIZ_DEFAULT);

        }


        if(outtakeState == statesOuttake.DEFAULT && reqState != null && sequenceState != statesSequence.DONE) {
            switch (sequenceState) {
                case WAITING1:
                    // Deci asta o sa fie prima actiune pe care o face din secventa
                    outake.setBrat(posBrat);
                    // TODO: First step of sequence
                    break;
                case WAITING2:
                    if(timer.milliseconds() > 200) {
                        // Cand timeru atinge 200 de ms o sa o faca pe asta
                        outake.setTwist(posTwist);
                        outake.setPivot(posPivot);
                        // TODO: 2nd step of sequence
                        sequenceState = statesSequence.WAITING3;
                    }
                    break;
                case WAITING3:
                    if(timer.milliseconds() > 1000) {
                        // cand timeru atinge 1000 de ms o face pe asta
                        crane.setglisieraOriz((float) posGlisieraOriz);
                        sequenceState = statesSequence.WAITING4;
                    }
                        // TODO: 3rd step of sequence
                    break;

                case WAITING4:
                    if(timer.milliseconds() > 1000) {
                        // Asta e ultimu pas pe care il face cand timeru ajunge la 1000 de ms
                        // TODO: Last step of sequence
                        outtakeState = reqState;
                        reqState = null;
                        sequenceState = statesSequence.DONE;
                    }
                    break;
            }
    }

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
enum statesSequence {
    DONE,
    WAITING1,
    WAITING2,
    WAITING3,
    WAITING4,
    WAITING5
}

