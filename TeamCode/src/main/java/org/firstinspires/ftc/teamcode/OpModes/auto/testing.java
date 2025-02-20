package org.firstinspires.ftc.teamcode.OpModes.auto;

import static org.firstinspires.ftc.teamcode.Core.util.AutonomousHelpers.HeadingInterpolation;
import static org.firstinspires.ftc.teamcode.Core.util.AutonomousHelpers.buildCurve;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Core.Commands.PedroCommands.PathCommand;
import org.firstinspires.ftc.teamcode.Core.Robot;
import org.firstinspires.ftc.teamcode.Core.util.Constants;
import org.firstinspires.ftc.teamcode.pedroPathing.follower.Pose;
import org.firstinspires.ftc.teamcode.pedroPathing.follower.PoseUpdater;
import org.firstinspires.ftc.teamcode.pedroPathing.pathGeneration.Path;
import org.firstinspires.ftc.teamcode.pedroPathing.pathGeneration.Point;
import org.firstinspires.ftc.teamcode.pedroPathing.util.DashboardPoseTracker;
import org.firstinspires.ftc.teamcode.pedroPathing.util.Drawing;

@Config
@Autonomous
@Disabled
public class testing extends LinearOpMode {

    private PoseUpdater poseUpdater;
    private DashboardPoseTracker dashboardPoseTracker;

    private Robot robot;

    public static Path[] paths = new Path[2];
    private final Pose forwardStart = new Pose(0, 0, Math.toRadians(270));
    private final Point midPoint = new Point(0, 30);
    private final Pose end = new Pose(0, 60, Math.toRadians(270));
    private final Pose backward = new Pose(0, 0, Math.toRadians(270));

    public void buildPaths() {
        paths[0] = buildCurve(forwardStart, midPoint, end, HeadingInterpolation.CONSTANT);
        paths[1] = buildCurve(end, midPoint, forwardStart, HeadingInterpolation.CONSTANT);
    }

    @Override
    public void runOpMode() {
        robot = Robot.getInstance();

        robot.initialize(hardwareMap, telemetry);

        buildPaths();

        while(!isStarted()) {

            CommandScheduler.getInstance().run();

            robot.claw.setPosition(Constants.clawClosedPosition);
            robot.wrist.setPosition(Constants.wristStartingPosition);

            updateTelemetry();

            poseUpdater = new PoseUpdater(hardwareMap);

            dashboardPoseTracker = new DashboardPoseTracker(poseUpdater);

            Drawing.drawRobot(poseUpdater.getPose(), "#4CAF50");
            Drawing.sendPacket();
        }

        robot.setPose(forwardStart);

        CommandScheduler.getInstance().schedule(

                new SequentialCommandGroup(

                        new PathCommand(paths[0]),
                        new WaitCommand(500),
                        new PathCommand(paths[1]),
                        new PathCommand(paths[0]),
                        new WaitCommand(500),
                        new PathCommand(paths[1]),
                        new PathCommand(paths[0]),
                        new WaitCommand(500),
                        new PathCommand(paths[1]),
                        new PathCommand(paths[0]),
                        new WaitCommand(500),
                        new PathCommand(paths[1]),
                        new PathCommand(paths[0]),
                        new WaitCommand(500),
                        new PathCommand(paths[1])

                )
        );

        while(opModeIsActive() && !isStopRequested()) {

            CommandScheduler.getInstance().run();

            updateTelemetry();



            poseUpdater.update();
            dashboardPoseTracker.update();

            Drawing.drawPoseHistory(dashboardPoseTracker, "#4CAF50");
            Drawing.drawRobot(poseUpdater.getPose(), "#4CAF50");
            Drawing.sendPacket();
        }

    }

    public void updateTelemetry() {
        telemetry.addData("x", robot.getPose().getX());
        telemetry.addData("y", robot.getPose().getY());
        telemetry.addData("heading", robot.getPose().getHeading());
        telemetry.addData("SlideTarget", robot.slide.getTargetPosition());
        telemetry.addData("SlideCurrent", robot.slide.getCurrentPosition());
        telemetry.addData("Claw", robot.claw.getPosition());
        telemetry.addData("Power", robot.slide.getPower());
        telemetry.update();
    }
}
