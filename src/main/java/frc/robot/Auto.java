package frc.robot;

import java.util.Arrays;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj.trajectory.TrajectoryConfig;
import edu.wpi.first.wpilibj.trajectory.TrajectoryGenerator;
import edu.wpi.first.wpilibj.trajectory.Trajectory.State;

public class Auto {

    private SwerveDrive swerveDrive;

    private TrajectoryConfig trajectoryConfig;

    private Trajectory trajectory;

    private PIDController horizontalController = new PIDController(0.07, 0, 0.001);
    private PIDController verticalController = new PIDController(0.07, 0, 0.001);
    private PIDController rotationController = new PIDController(0.07, 0, 0.001);

    private Timer timer = new Timer();

    public Auto(SwerveDrive swerveDrive) {
        this.swerveDrive = swerveDrive;
    }

    public void autoInit() {
        //Configure the rotation PID to take the shortest route to the setpoint
        rotationController.enableContinuousInput(-Math.PI, Math.PI);

        //Sets the maximum speed and acceleration that we want to use
        trajectoryConfig = new TrajectoryConfig(RobotMap.MAXIMUM_SPEED, RobotMap.MAXIMUM_ACCELERATION);

        trajectory = TrajectoryGenerator.generateTrajectory(
            Arrays.asList(
                new Pose2d(0.0, 0.0, new Rotation2d(0.0)),
                new Pose2d(0.0, 1.0, new Rotation2d(0.0))
            ),
            trajectoryConfig
        );

        timer.reset();
        timer.start();
    }

    public void autoPeriodic() {
        Pose2d currentPose = swerveDrive.getPose();
        State desiredState = trajectory.sample(timer.get());
        Pose2d desiredPose = desiredState.poseMeters;

        double totalVelocity = desiredState.velocityMetersPerSecond;
        double velocityX = totalVelocity * desiredPose.getRotation().getCos();
        double velocityY = totalVelocity * desiredPose.getRotation().getSin();

        double horizontalFeedback = horizontalController.calculate(currentPose.getX(), desiredPose.getX());
        double verticalFeedback = verticalController.calculate(currentPose.getY(), desiredPose.getY());
        double velocityRotation = rotationController.calculate(currentPose.getRotation().getRadians(), desiredPose.getRotation().getRadians());

        swerveDrive.periodic(new SwerveCommand(velocityX + horizontalFeedback, velocityY + verticalFeedback, velocityRotation));

        SmartDashboard.putNumber("Horizontal", currentPose.getX());
        SmartDashboard.putNumber("Vertical", velocityY);
        SmartDashboard.putNumber("Rotation", currentPose.getRotation().getRadians());
    }
}
