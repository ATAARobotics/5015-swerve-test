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

    private AutoCommand[][] autoPrograms;

    private int autoSelected;

    private int commandRunning = 0;
    private boolean newCommand = true;

    private DataLogger pathLogger;

    public Auto(SwerveDrive swerveDrive) {
        this.swerveDrive = swerveDrive;

        if (RobotMap.AUTO_PATH_LOGGING_ENABLED) {
            pathLogger = new DataLogger("autoLog");
        }
    }

    public void autoInit(int autoSelected) {
        this.autoSelected = autoSelected;

        swerveDrive.resetPose();

        pathLogger.setupFile();

        //Configure the rotation PID to take the shortest route to the setpoint
        rotationController.enableContinuousInput(-Math.PI, Math.PI);
    }

    public void autoPeriodic() {

        AutoCommand currentCommand = autoPrograms[autoSelected][commandRunning];

        double velocityX = 0;
        double velocityY = 0;
        double velocityRotation = 0;

        if (currentCommand != null) {
            switch (currentCommand.getCommandType()) {
                case 0:
                    if (newCommand) {
                        newCommand = false;
                        timer.reset();
                        timer.start();
                    }

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

                    //TODO Find out why the robot turns funny to start
                    if (RobotMap.AUTO_PATH_LOGGING_ENABLED) {
                        pathLogger.writeLine(desiredPose.getX() + "," + desiredPose.getY() + "," + currentPose.getX() + "," + currentPose.getY() + "," + desiredPose.getRotation().getRadians() + "," + Timer.getFPGATimestamp());
                    }

                    if (timer.get() >= currentCommand.getTotalTime()) {
                        commandRunning++;
                        newCommand = true;
                    }
                    break;

                default:
                    System.err.println("There is no auto command with type " + currentCommand.getCommandType() + "!");
                    break;
            }
        }

        swerveDrive.periodic(new SwerveCommand(velocityX, velocityY, velocityRotation));
    }

    public void createPrograms() {
        AutoPaths autoPaths = new AutoPaths();

        autoPrograms = new AutoCommand[][] {
            {
                autoPaths.getTestPath()
            }
        };
    }
}
