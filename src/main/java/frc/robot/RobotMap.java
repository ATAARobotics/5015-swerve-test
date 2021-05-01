package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.AnalogInput;

/**
 * A centralized file that keeps track of all robot actuators and physical components
 */

public class RobotMap {

    //Motors
    private WPI_TalonSRX frontLeftDriveMotor = new WPI_TalonSRX(0);
    private WPI_TalonSRX frontRightDriveMotor = new WPI_TalonSRX(2);
    private WPI_TalonSRX rearLeftDriveMotor = new WPI_TalonSRX(4);
    private WPI_TalonSRX rearRightDriveMotor = new WPI_TalonSRX(6);

    //Encoders
    private AnalogInput frontLeftRotationEncoder = new AnalogInput(0);
    private AnalogInput frontRightRotationEncoder = new AnalogInput(1);
    private AnalogInput rearLeftRotationEncoder = new AnalogInput(2);
    private AnalogInput rearRightRotationEncoder = new AnalogInput(3);

    //Create subsystems
    private Gyro navX = new Gyro();
    private SwerveDrive swerveDrive = new SwerveDrive(this);
    private Encoders encoders = new Encoders(this);

    public RobotMap() {
        navX.initializeNavX();
    }

    //Getter functions for hardware
    public WPI_TalonSRX getDriveMotor(int moduleId) {
        switch (moduleId) {
            case 0:
                return frontLeftDriveMotor;

            case 1:
                return frontRightDriveMotor;

            case 2:
                return rearLeftDriveMotor;

            case 3:
                return rearRightDriveMotor;

            default:
                System.err.println(moduleId + " is not a valid swerve module ID!");
                return frontLeftDriveMotor;
        }
    }
    public AnalogInput getRotationEncoder(int moduleId) {
        switch (moduleId) {
            case 0:
                return frontLeftRotationEncoder;

            case 1:
                return frontRightRotationEncoder;

            case 2:
                return rearLeftRotationEncoder;

            case 3:
                return rearRightRotationEncoder;

            default:
                System.err.println(moduleId + " is not a valid swerve module ID!");
                return frontLeftRotationEncoder;
        }
    }

    //Getter functions for subsystems
    public Gyro getGyro() {
        return navX;
    }
    public Encoders getEncoders() {
        return encoders;
    }
    public SwerveDrive getSwerveDrive() {
        return swerveDrive;
    }
}
