package org.firstinspires.ftc.teamcode.Core.util;

import org.firstinspires.ftc.teamcode.pedroPathing.localization.Pose;
import org.firstinspires.ftc.teamcode.pedroPathing.pathGeneration.Point;

public class Constants {

    /* ----------- NAMES ----------- */

    //TODO: ADD STUFF
    public static String pinpointName = "pinpoint";

    /* ----------- TELEOP ----------- */

    //TODO: ADD STUFF

    /* ----------- AUTO ----------- */

    /* ----------- Pinpoint Fix (remove eventually) ----------- */

    public static double forwardOffsetMM_X = -120.000014;
    public static double forwardOffsetMM_Y = -135.999982;
    public static double strafeOffsetMM_X = -120.000014;
    public static double strafeOffsetMM_Y = 135.999982;

    /* ----------- Pinpoint ----------- */

    public static double xOffsetMM = -120.000014;
    public static double yOffsetMM = 135.999982;
    public static double xOffsetInch = (xOffsetMM / 25.4); //-4.72441
    public static double yOffsetInch = (yOffsetMM / 25.4); //5.35433
    public static double maxPower = 1;

    /* ----------- PEDRO POSITIONS ----------- */

    public static Pose sampleStartPosition = new Pose(7.5, 112.5, Math.toRadians(90));
    public static Pose specimenStartPosition = new Pose(7.5, 72, Math.toRadians(180));
    /* ------- MOTOR SECTION ------- */

    /* ----------- JOINT ----------- */

    public static double jointSpecimenPlacePosition = 1000;
    public static double jointSamplePickupPosition = 1000;

    /* ----------- SLIDE ----------- */

    public static double slideMaxPosition = 3000;
    public static double slideMinPosition = 100;
    public static double slideHighBasketPosition = 2800;
    public static double slideLowBasketPosition = 2000;

    /* ------- SERVO SECTION ------- */

    /* ----------- CLAW ----------- */
    public static double clawOpenPosition = 0.55;
    public static double clawClosedPosition = 0.85;

    /* ----------- WRIST ----------- */
    public static double wristStartingPosition = 0.85;

}
