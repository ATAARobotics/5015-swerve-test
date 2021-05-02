package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
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

    private VictorSPX frontLeftRotationMotor;
    private VictorSPX frontRightRotationMotor;
    private VictorSPX rearLeftRotationMotor;
    private VictorSPX rearRightRotationMotor;

    public SwerveDrive(RobotMap robotMap) {
        this.frontLeftDriveMotor = robotMap.getDriveMotor(0);
        this.frontRightDriveMotor = robotMap.getDriveMotor(1);
        this.rearLeftDriveMotor = robotMap.getDriveMotor(2);
        this.rearRightDriveMotor = robotMap.getDriveMotor(3);

        this.frontLeftRotationMotor = robotMap.getRotationMotor(0);
        this.frontRightRotationMotor = robotMap.getRotationMotor(1);
        this.rearLeftRotationMotor = robotMap.getRotationMotor(2);
        this.rearRightRotationMotor = robotMap.getRotationMotor(3);
    }
}
