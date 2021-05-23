package frc.robot;

import java.util.List;

import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj.trajectory.TrajectoryConfig;
import edu.wpi.first.wpilibj.trajectory.TrajectoryGenerator;
import edu.wpi.first.wpilibj.trajectory.Trajectory.State;

public class AutoPath {

    Trajectory trajectory;

    double targetAngle;

    public AutoPath(Pose2d startPoint, List<Translation2d> waypoints, Pose2d endPoint) {
        TrajectoryConfig trajectoryConfig = new TrajectoryConfig(RobotMap.MAXIMUM_SPEED, RobotMap.MAXIMUM_ACCELERATION);

        this.targetAngle = endPoint.getRotation().getRadians();

        trajectory = TrajectoryGenerator.generateTrajectory(startPoint, waypoints, endPoint, trajectoryConfig);
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
