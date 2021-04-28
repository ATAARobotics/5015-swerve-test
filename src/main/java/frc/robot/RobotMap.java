package frc.robot;

/**
 * A centralized file that keeps track of all robot actuators and physical components
 */

public class RobotMap {

    //Motors
    /* private WPI_VictorSPX frontLeftMotor = new WPI_VictorSPX(2);
    private WPI_TalonSRX rearLeftMotor = new WPI_TalonSRX(1);
    private WPI_VictorSPX frontRightMotor = new WPI_VictorSPX(4);
    private WPI_TalonSRX rearRightMotor = new WPI_TalonSRX(3);
    private SpeedControllerGroup rightMotors = new SpeedControllerGroup(rearRightMotor, frontRightMotor); // Group
    private SpeedControllerGroup leftMotors = new SpeedControllerGroup(rearLeftMotor, frontLeftMotor); // Group */

    //Encoders

    // Gyro
    private Gyro NavX = new Gyro();

    // Controllers for specific actions on the robot, these classes should be
    // accessed directly because they have nice interfaces
    public SwerveDrive swerveDrive;

    public RobotMap() {

        // Init submodules
        swerveDrive = new SwerveDrive();

        NavX.initializeNavX();
    }

    /**
     * Returns the navx attached to the robot.
     */
    public Gyro getGyro() {
        return NavX;
    }
}
