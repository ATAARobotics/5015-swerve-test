package frc.robot;

import java.util.List;

import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj.trajectory.TrajectoryConfig;
import edu.wpi.first.wpilibj.trajectory.TrajectoryGenerator;
import edu.wpi.first.wpilibj.trajectory.Trajectory.State;

public class AutoPath {

    Trajectory trajectory;

    double targetAngle;

    //TODO Fix this so we don't need a new path every time we want to change the heading
    public AutoPath(List<Pose2d> waypoints, double targetAngle) {
        TrajectoryConfig trajectoryConfig = new TrajectoryConfig(RobotMap.MAXIMUM_SPEED, RobotMap.MAXIMUM_ACCELERATION);

        this.targetAngle = targetAngle;

        trajectory = TrajectoryGenerator.generateTrajectory(waypoints, trajectoryConfig);
    }

    public double getTargetAngle() {
        return targetAngle;
    }

    public State getState(double timestamp) {
        return trajectory.sample(timestamp);
    }

    public double getTotalTime() {
        return trajectory.getTotalTimeSeconds();
    }
}
