package frc.robot;

import java.util.Arrays;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.controller.ProfiledPIDController;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj.trajectory.Trajectory.State;

public class Auto {

    private SwerveDrive swerveDrive;

    private AutoPath autoPath;

    private PIDController horizontalController = new PIDController(0.07, 0, 0.001);
    private PIDController verticalController = new PIDController(0.07, 0, 0.001);

    //Rotation is controlled independently of linear movement, so we use a separate PID system
    private ProfiledPIDController rotationController = new ProfiledPIDController(0.07, 0, 0.001, new TrapezoidProfile.Constraints(RobotMap.MAXIMUM_ROTATIONAL_SPEED, RobotMap.MAXIMUM_ROTATIONAL_ACCELERATION));

    private Timer timer = new Timer();

    public Auto(SwerveDrive swerveDrive) {
        this.swerveDrive = swerveDrive;
    }

    public void autoInit() {
        swerveDrive.resetPose();

        //Configure the rotation PID to take the shortest route to the setpoint
        rotationController.enableContinuousInput(-Math.PI, Math.PI);

        autoPath = new AutoPath(
            Arrays.asList(
                new Pose2d(0.0, 0.0, new Rotation2d(0.0)),
                new Pose2d(0.0, 1.0, new Rotation2d(0.0))
            ),
            0.0
        );

        timer.reset();
        timer.start();
    }

    public void autoPeriodic() {
        State desiredState = autoPath.getState(timer.get());
        
        Pose2d currentPose = swerveDrive.getPose();
        Pose2d desiredPose = desiredState.poseMeters;

        double currentAngle = currentPose.getRotation().getRadians();
        double desiredAngle = autoPath.getTargetAngle();

        double totalVelocity = desiredState.velocityMetersPerSecond;
        double velocityX = totalVelocity * desiredPose.getRotation().getCos();
        double velocityY = totalVelocity * desiredPose.getRotation().getSin();

        double horizontalFeedback = horizontalController.calculate(currentPose.getX(), desiredPose.getX());
        double verticalFeedback = verticalController.calculate(currentPose.getY(), desiredPose.getY());

        double velocityRotation = rotationController.calculate(currentAngle, desiredAngle);

        swerveDrive.periodic(new SwerveCommand(velocityX + horizontalFeedback, velocityY + verticalFeedback, velocityRotation));
    }
}
