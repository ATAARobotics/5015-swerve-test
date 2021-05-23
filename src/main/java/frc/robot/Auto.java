package frc.robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.controller.ProfiledPIDController;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj.trajectory.Trajectory.State;

public class Auto {

    private SwerveDrive swerveDrive;

    private PIDController xController = new PIDController(0.07, 0, 0.001);
    private PIDController yController = new PIDController(0.07, 0, 0.001);

    //Rotation is controlled independently of linear movement, so we use a separate PID system
    private ProfiledPIDController rotationController = new ProfiledPIDController(0.07, 0, 0.001, new TrapezoidProfile.Constraints(RobotMap.MAXIMUM_ROTATIONAL_SPEED, RobotMap.MAXIMUM_ROTATIONAL_ACCELERATION));

    private Timer timer = new Timer();

    private AutoProgram[] autoPrograms;

    private int autoSelected;

    private int commandRunning = 0;

    public Auto(SwerveDrive swerveDrive) {
        this.swerveDrive = swerveDrive;
    }

    public void autoInit(int autoSelected) {
        this.autoSelected = autoSelected;

        swerveDrive.resetPose();

        //Configure the rotation PID to take the shortest route to the setpoint
        rotationController.enableContinuousInput(-Math.PI, Math.PI);
    }

    public void autoPeriodic() {

        AutoCommand currentCommand = autoPrograms[autoSelected].getCommand(commandRunning);

        double velocityX = 0;
        double velocityY = 0;
        double velocityRotation = 0;

        switch (currentCommand.getCommandType()) {
            case 0:
                State desiredState = currentCommand.getState(timer.get());
            
                Pose2d currentPose = swerveDrive.getPose();
                Pose2d desiredPose = desiredState.poseMeters;
        
                double currentAngle = currentPose.getRotation().getRadians();
                double desiredAngle = currentCommand.getTargetAngle();
        
                double totalVelocity = desiredState.velocityMetersPerSecond;
                
                velocityX = totalVelocity * desiredPose.getRotation().getCos();
                velocityY = totalVelocity * desiredPose.getRotation().getSin();
        
                velocityX += xController.calculate(currentPose.getX(), desiredPose.getX());
                velocityY += yController.calculate(currentPose.getY(), desiredPose.getY());
        
                velocityRotation = rotationController.calculate(currentAngle, desiredAngle);

                if (timer.get() >= currentCommand.getTotalTime()) {
                    commandRunning++;
                }
                break;

            default:
                System.err.println("There is no auto command with type " + currentCommand.getCommandType() + "!");
                break;
        }

        swerveDrive.periodic(new SwerveCommand(velocityX, velocityY, velocityRotation));
    }

    public void createPrograms() {
        AutoPaths autoPaths = new AutoPaths();

        autoPrograms = new AutoProgram[] {
            new AutoProgram(
                autoPaths.getTestPath()
            )
        };
    }
}
