package org.firstinspires.ftc.teamcode.OpModes.aoldcode;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.controller.PIDController;
import com.arcrobotics.ftclib.controller.PIDFController;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;

@Disabled
@Config
@TeleOp
public class jointpidtester extends OpMode {
    private PIDFController controller;
    public static double p = 0.03, i = 0, d = 0.001;
    public static double f = 0.0001;
    public static int target = 0;
    private final double ticksInDegree = 285.0 / 180.0; //1425
    private DcMotorEx jointMotor;
    @Override
    public void init() {
        controller = new PIDFController(p,i,d,f);
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());

        jointMotor = hardwareMap.get(DcMotorEx.class, "jointMotor");

    }

    @Override
    public void loop() {
        controller.setPIDF(p,i,d,f);
        int slidePos = jointMotor.getCurrentPosition();
        double pid = controller.calculate(slidePos,target);
        //double ff = Math.cos(Math.toRadians(target / ticksInDegree)) * f;
        double power = controller.calculate(slidePos, target);
        jointMotor.setPower(power);

        telemetry.addData("pos", slidePos);
        telemetry.addData("target", target);
        telemetry.update();
    }
}
