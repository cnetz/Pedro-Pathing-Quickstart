package org.firstinspires.ftc.teamcode.OpModes.auto;

import static org.firstinspires.ftc.teamcode.Core.util.AutonomousHelpers.HeadingInterpolation;
import static org.firstinspires.ftc.teamcode.Core.util.AutonomousHelpers.buildCurve;
import static org.firstinspires.ftc.teamcode.Core.util.AutonomousHelpers.buildLine;

import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Core.Commands.PedroCommands.PathCommand;
import org.firstinspires.ftc.teamcode.Core.Commands.subsystems.motors.SetJoint;
import org.firstinspires.ftc.teamcode.Core.Commands.subsystems.motors.SetSlide;
import org.firstinspires.ftc.teamcode.Core.Robot;
import org.firstinspires.ftc.teamcode.Core.util.Constants;
import org.firstinspires.ftc.teamcode.pedroPathing.follower.Pose;
import org.firstinspires.ftc.teamcode.pedroPathing.pathGeneration.Path;
import org.firstinspires.ftc.teamcode.pedroPathing.pathGeneration.Point;

@Disabled /* REMOVE IF USING */
@Autonomous(name = "ExampleAuto", group = "example")
public class ExampleAuto extends LinearOpMode {

    /* Declares our Robot class which has all our subsystems */

    private Robot robot;

    /* The maximum is 2 paths, change value to
    increase or decrease (THIS DOES INCLUDE 0!!!) */

    public static Path[] paths = new Path[2];


    /* These are our Position points that we want to go to */

    private final Pose lineEndPosition1 = new Pose(36, 72, Math.toRadians(180));
    private final Pose curveEndPosition1 = new Pose(12, 106, Math.toRadians(90));

    /* Notice how its "point" instead of "pose," that's
    important for Curves, as its the middle point, this
    is very important that its NOT "Pose" (MUST BE
    "Point" for middle of curves ) */

    private final Point curvePoint1 = new Point(36, 72);


    /* Build our paths that we want */

    public void buildPaths() {
        paths[0] = buildLine(
                Constants.specimenStartPosition, // A start position
                lineEndPosition1, // A end position

                /* A heading it will follow.
                This could linear, constants, or tangential */

                HeadingInterpolation.CONSTANT
        );
        paths[1] = buildCurve(
                lineEndPosition1, // A start position
                curvePoint1, // A middle position
                curveEndPosition1, // A end position

                /* A heading it will follow.
                This could linear, constants, or tangential */

                HeadingInterpolation.LINEAR
        );

    }

    @Override
    public void runOpMode() {
        /* Get the actual instance of the robot. It's core. */

        robot = Robot.getInstance();

        /* Initialize it like any servo or motor */

        robot.initialize(hardwareMap, telemetry);


        /* Call our paths that we created above us */

        buildPaths();

        /* When not running, during init, do whatever is in here. */

        while(!isStarted()) {

            /* Setting the command schedule */

            CommandScheduler.getInstance().run();

            /* Set the default positions of the claw and wrist */

            robot.claw.setPosition(Constants.clawClosedPosition);
            robot.wrist.setPosition(Constants.wristStartingPosition);

            /* Call telemetry update */

            updateTelemetry();
        }

        /* This settings the Pose of the robot on the field */

        robot.setPose(Constants.specimenStartPosition);

        /* This is the most important area! This is where
        we call our commands that we created to run during
        auto */

        CommandScheduler.getInstance().schedule(

                /* Everything in here will run after one another */

                new SequentialCommandGroup(

                        /* We now call a PathCommand of our paths we created above to run in order */

                        new PathCommand(paths[0]),
                        new PathCommand(paths[1]),

                        /* Everything in here will run at the same time */

                        new ParallelCommandGroup(

                        /* Example of running slide and joint at same time
                        this is the same for servos, instead it SetBasket
                        or SetClaw */

                        new SetSlide(robot.slide, Constants.slideMiddlePosition),
                        new SetJoint(robot.joint, Constants.jointSpecimenPlacePosition)

                        )

                )


        );

        /* This is where we actually run the auto program */

        while(opModeIsActive() && !isStopRequested()) {

            /* Call the Command Scheduler we created just above this to run when started */

            CommandScheduler.getInstance().run();

            /* Call telemetry update */

            updateTelemetry();
        }

    }

    /* Update telemetry - FIX THIS TELEMETRY NOT WORKING */

    public void updateTelemetry() {
        telemetry.addData("x", robot.getPose().getX());
        telemetry.addData("y", robot.getPose().getY());
        telemetry.addData("heading", robot.getPose().getHeading());
        telemetry.addData("SlideTarget", robot.slide.getActualTargetPosition());
        telemetry.addData("SlideCurrent", robot.slide.getCurrentPosition());
        telemetry.addData("Claw", robot.claw.getPosition());
        telemetry.addData("Power", robot.slide.getPower());
        telemetry.update();
    }
}
