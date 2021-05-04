package frc.robot;

/**
 * A centralized file that keeps track of all robot actuators and physical components
 */

public class RobotMap {

    public static final int FRONT_LEFT_DRIVE_MOTOR = 0;
    public static final int FRONT_RIGHT_DRIVE_MOTOR = 2;
    public static final int REAR_LEFT_DRIVE_MOTOR = 4;
    public static final int REAR_RIGHT_DRIVE_MOTOR = 6;

    public static final int FRONT_LEFT_ROTATION_MOTOR = 1;
    public static final int FRONT_RIGHT_ROTATION_MOTOR = 3;
    public static final int REAR_LEFT_ROTATION_MOTOR = 5;
    public static final int REAR_RIGHT_ROTATION_MOTOR = 7;

    public static final int FRONT_LEFT_ROTATION_ENCODER = 0;
    public static final int FRONT_RIGHT_ROTATION_ENCODER = 1;
    public static final int REAR_LEFT_ROTATION_ENCODER = 2;
    public static final int REAR_RIGHT_ROTATION_ENCODER = 3;

    //Create subsystems
    private Gyro navX = new Gyro();
    private SwerveDrive swerveDrive = new SwerveDrive(this);

    public RobotMap() {
        navX.initializeNavX();
    }

    //Getter functions for subsystems
    public Gyro getGyro() {
        return navX;
    }
    public SwerveDrive getSwerveDrive() {
        return swerveDrive;
    }
}
