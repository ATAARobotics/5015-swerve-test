package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

/**
 * A file in charge of managing a swerve drive
 * 
 * @author Jacob Guglielmin
 */
public class SwerveDrive {

    private WPI_TalonSRX frontLeftDriveMotor;
    private WPI_TalonSRX frontRightDriveMotor;
    private WPI_TalonSRX rearLeftDriveMotor;
    private WPI_TalonSRX rearRightDriveMotor;

    public SwerveDrive(RobotMap robotMap) {
        this.frontLeftDriveMotor = robotMap.getDriveMotor(0);
        this.frontRightDriveMotor = robotMap.getDriveMotor(0);
        this.rearLeftDriveMotor = robotMap.getDriveMotor(0);
        this.rearRightDriveMotor = robotMap.getDriveMotor(0);
    }
}
