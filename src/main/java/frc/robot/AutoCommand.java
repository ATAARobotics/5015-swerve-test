package frc.robot;

import java.util.List;

import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj.trajectory.TrajectoryConfig;
import edu.wpi.first.wpilibj.trajectory.TrajectoryGenerator;
import edu.wpi.first.wpilibj.trajectory.Trajectory.State;

public class AutoCommand {

    Trajectory trajectory;

    double targetAngle = -999.0;

    int actionType = 0;

    /**
     * Creates an AutoCommand to perform an action without moving the drivetrain.
     * 
     * @param stationaryAction The ID of the action to perform
     */
    public AutoCommand(int stationaryAction) {
        this.actionType = stationaryAction;
    }

    /**
     * Creates an AutoCommand to move the robot through a set of points, following a cubic spline.
     * 
     * @param startPoint The Pose2d where the robot is beginning this path
     * @param waypoints A list of Translation2d objects to pass through in order
     * @param endPoint The Pose2d where the robot should stop
     */
    public AutoCommand(Pose2d startPoint, List<Translation2d> waypoints, Pose2d endPoint) {
        TrajectoryConfig trajectoryConfig = new TrajectoryConfig(RobotMap.MAXIMUM_SPEED, RobotMap.MAXIMUM_ACCELERATION);

        this.targetAngle = endPoint.getRotation().getRadians();

        trajectory = TrajectoryGenerator.generateTrajectory(startPoint, waypoints, endPoint, trajectoryConfig);
    }

    public int getCommandType() {
        return actionType;
    }

    public double getTargetAngle() {
        return targetAngle;
    }

    public State getState(double timestamp) {
        if (trajectory != null) {
            return trajectory.sample(timestamp);
        } else {
            return null;
        }
    }

    public double getTotalTime() {
        if (trajectory != null) {
            return trajectory.getTotalTimeSeconds();
        } else {
            return -999;
        }
    }
}
