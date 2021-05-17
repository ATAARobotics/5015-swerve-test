

package frc.robot;

import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SwerveDrive {

    private Gyro gyro;

    //Whether the swerve should be field-oriented
    boolean fieldOriented = false;

    //An array of all the modules on the swerve drive
    private SwerveModule[] swerveModules;

    //The odometry for the swerve drive
    private SwerveOdometry odometry;

    //The current pose of the robot
    private Pose2d pose;

    /**
     * Set up the swerve drive
     * 
     * @param gyro The gyro object running on the robot
     * @param initialPose The initial pose that the robot is in
     */
    public SwerveDrive(Gyro gyro, Pose2d initialPose) {
        this.gyro = gyro;

        //Initialize four swerve modules using the SwerveModule class
        SwerveModule frontLeftModule = new SwerveModule(new WPI_TalonSRX(RobotMap.FRONT_LEFT_DRIVE_MOTOR), new VictorSPX(RobotMap.FRONT_LEFT_ROTATION_MOTOR), new AnalogInput(RobotMap.FRONT_LEFT_ROTATION_ENCODER), 1.9, false);
        frontLeftModule.setId(0);
        frontLeftModule.setName("Front Left");
        SwerveModule frontRightModule = new SwerveModule(new WPI_TalonSRX(RobotMap.FRONT_RIGHT_DRIVE_MOTOR), new VictorSPX(RobotMap.FRONT_RIGHT_ROTATION_MOTOR), new AnalogInput(RobotMap.FRONT_RIGHT_ROTATION_ENCODER), -1.1, true);
        frontRightModule.setId(1);
        frontRightModule.setName("Front Right");
        SwerveModule rearLeftModule = new SwerveModule(new WPI_TalonSRX(RobotMap.REAR_LEFT_DRIVE_MOTOR), new VictorSPX(RobotMap.REAR_LEFT_ROTATION_MOTOR), new AnalogInput(RobotMap.REAR_LEFT_ROTATION_ENCODER), -2.3, false);
        rearLeftModule.setId(2);
        rearLeftModule.setName("Rear Left");
        SwerveModule rearRightModule = new SwerveModule(new WPI_TalonSRX(RobotMap.REAR_RIGHT_DRIVE_MOTOR), new VictorSPX(RobotMap.REAR_RIGHT_ROTATION_MOTOR), new AnalogInput(RobotMap.REAR_RIGHT_ROTATION_ENCODER), 2.1, true);
        rearRightModule.setId(3);
        rearRightModule.setName("Rear Right");

        //Put the swerve modules in an array so we can process them easier
        swerveModules = new SwerveModule[]{
            frontLeftModule,
            frontRightModule,
            rearLeftModule,
            rearRightModule
        };

        //Set up odometry
        odometry = new SwerveOdometry(initialPose);

        //Initialize the pose
        pose = initialPose;
    }

    /**
     * This function should be run during every teleop and auto periodic
     */
    public void periodic(SwerveCommand command) {
        SmartDashboard.putNumber("Gyro Value", gyro.getAngle());

        //Execute functions on each swerve module
        for (SwerveModule module : swerveModules) {
            //Set the drive velocity in meters/second for the module
            module.setDriveVelocity(command.getModuleVelocity(module.getId()));

            //Set the angle of the module
            if (fieldOriented) {
                //Subtract the current heading of the robot from the desired angle to determine the actual angle required
                double angle = command.getModuleAngle(module.getId()) - gyro.getAngle();

                 //Offset by Pi to find values in the wrong half of the circle
                angle += Math.PI;

                //Wrap angle at 2*Pi
                angle %= 2.0 * Math.PI;

                //Ensure the value is not negative
                if (angle < 0) {
                    angle += 2.0 * Math.PI;
                }

                //Undo the offset
                angle -= Math.PI;

                module.setTargetAngle(angle);
            } else {
                module.setTargetAngle(command.getModuleAngle(module.getId()));
            }

            //Run periodic tasks on the module (running motors)
            module.periodic();
        }

        //Update the current pose with the latest command, angle, and a timestamp
        pose = odometry.update(command, gyro.getAngle(), Timer.getFPGATimestamp());
    }

    /**
     * Sets whether the robot should be field-oriented
     */
    public void setFieldOriented(boolean fieldOriented) {
        this.fieldOriented = fieldOriented;
    }

    /**
     * Gets the current pose of the robot
     */
    public Pose2d getPose() {
        return pose;
    }
}
