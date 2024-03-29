package frc.robot;

import java.util.List;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.controller.ProfiledPIDController;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj.trajectory.Trajectory.State;

public class Auto {

    private SwerveDrive swerveDrive;

    //Create PIDs to control the position of the robot in the x and y direction
    private PIDController xController = new PIDController(0.07, 0, 0.001);
    private PIDController yController = new PIDController(0.07, 0, 0.001);

    //Rotation is controlled independently of linear movement, so we use a separate PID system
    private ProfiledPIDController rotationController = new ProfiledPIDController(0.07, 0, 0.001, new TrapezoidProfile.Constraints(RobotMap.MAXIMUM_ROTATIONAL_SPEED, RobotMap.MAXIMUM_ROTATIONAL_ACCELERATION));

    private Timer timer = new Timer();

    //Stores all the auto programs
    private AutoCommand[][] autoPrograms;

    //The auto program to run
    private int autoSelected;

    //These keep track of the current command in the program, and whether the command that is running just started
    private int commandRunning = 0;
    private boolean newCommand = true;

    //This logs the path of the robot during autonomous (if enabled in RobotMap)
    private DataLogger pathLogger;

    public Auto(SwerveDrive swerveDrive) {
        this.swerveDrive = swerveDrive;

        //Create a data logging object to log the path
        if (RobotMap.AUTO_PATH_LOGGING_ENABLED) {
            pathLogger = new DataLogger("autoLog");
        }
    }

    public void autoInit(int autoSelected) {
        this.autoSelected = autoSelected;

        //Sets the swerve drive to robot-oriented
        swerveDrive.setFieldOriented(false);

        //Resets the position of the robot (just in case we want to run auto more than once without restarting)
        swerveDrive.resetPose();

        //Reset the data logger (just in case we want to run auto more than once without restarting)
        if (RobotMap.AUTO_PATH_LOGGING_ENABLED) {
            pathLogger.setupFile();

            for (AutoCommand autoCommand : autoPrograms[autoSelected]) {
                if (autoCommand.getCommandType() == 0) {
                    List<Translation2d> waypoints = autoCommand.getWaypoints();
                    for (Translation2d waypoint : waypoints) {
                        pathLogger.writeLine(waypoint.getX() + "," + waypoint.getY());
                    }
                }
            }
            pathLogger.writeLine("PATH LOG STARTS HERE");
        }

        //Configure the rotation PID to take the shortest route to the setpoint
        rotationController.enableContinuousInput(-Math.PI, Math.PI);
    }

    public void autoPeriodic() {

        //Get the command in the auto program to run, unless auto is finished
        AutoCommand currentCommand = null;
        if (commandRunning < autoPrograms[autoSelected].length) {
            currentCommand = autoPrograms[autoSelected][commandRunning];
        } else {
            //Auto is complete, so close the data logger if we used it
            if (newCommand && RobotMap.AUTO_PATH_LOGGING_ENABLED) {
                pathLogger.close();
            }
        }

        double xVelocity = 0;
        double yVelocity = 0;
        double rotationVelocity = 0;

        if (currentCommand != null) {
            switch (currentCommand.getCommandType()) {
                case 0:
                    if (newCommand) {
                        newCommand = false;
                        timer.reset();
                        timer.start();
                    }

                    //Get the state at this time from the trajectory
                    State desiredState = currentCommand.getState(timer.get());
                
                    //Get the current position of the robot
                    Pose2d currentPose = swerveDrive.getPose();

                    //Get the position we want to be at
                    Pose2d desiredPose = desiredState.poseMeters;
            
                    //Get the current angle of the robot
                    double currentAngle = currentPose.getRotation().getRadians();

                    //Get the angle we want to be at
                    double desiredAngle = currentCommand.getTargetAngle();
            
                    //Get the total speed the robot should be travelling (not accounting for deviations)
                    double totalVelocity = desiredState.velocityMetersPerSecond;
                    
                    //Get the velocity in the X and Y direction based on the heading and total speed
                    xVelocity = totalVelocity * desiredPose.getRotation().getCos();
                    yVelocity = totalVelocity * desiredPose.getRotation().getSin();
            
                    //Change the speeds to account for deviations
                    xVelocity += xController.calculate(currentPose.getX(), desiredPose.getX());
                    yVelocity += yController.calculate(currentPose.getY(), desiredPose.getY());
            
                    //Get the current rotational velocity from the rotation PID based on the desired angle
                    rotationVelocity = rotationController.calculate(currentAngle, desiredAngle);

                    //Log the current and expected position (don't change this without changing the path viewer utility to read it properly)
                    if (RobotMap.AUTO_PATH_LOGGING_ENABLED) {
                        pathLogger.writeLine(desiredPose.getX() + "," + desiredPose.getY() + "," + currentPose.getX() + "," + currentPose.getY() + "," + Timer.getFPGATimestamp());
                    }

                    //Check if the robot finished the path
                    if (desiredState == currentCommand.getLastState()) {
                        commandRunning++;
                        newCommand = true;
                    }
                    break;

                default:
                    System.err.println("There is no auto command with type " + currentCommand.getCommandType() + "!");
                    break;
            }
        }

        //Drive the robot based on computed velocities
        swerveDrive.periodic(new SwerveCommand(xVelocity, yVelocity, rotationVelocity, true, swerveDrive.getHeading()));
    }

    /**
     * Initializes the auto programs
     */
    public void createPrograms() {
        //Get the paths from place to place
        AutoPaths autoPaths = new AutoPaths();

        //Create the auto programs. This should be an array of AutoCommand arrays. Each nested array is an auto program that will execute its contents in order.
        autoPrograms = new AutoCommand[][] {
            {
                autoPaths.getTestPath()
            }
        };
    }
}
