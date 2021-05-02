package frc.robot;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.AnalogInput;

/**
 * Encoder code
 */
public class Encoders {

    private AnalogInput frontLeftRotationEncoder;
    private AnalogInput frontRightRotationEncoder;
    private AnalogInput rearLeftRotationEncoder;
    private AnalogInput rearRightRotationEncoder;

    private TalonSRX frontLeftDriveMotor;
    private TalonSRX frontRightDriveMotor;
    private TalonSRX rearLeftDriveMotor;
    private TalonSRX rearRightDriveMotor;

    public Encoders(RobotMap robotMap) {
        //Get rotation encoders
        this.frontLeftRotationEncoder = robotMap.getRotationEncoder(0);
        this.frontRightRotationEncoder = robotMap.getRotationEncoder(1);
        this.rearLeftRotationEncoder = robotMap.getRotationEncoder(2);
        this.rearRightRotationEncoder = robotMap.getRotationEncoder(3);

        //Get drive motors (they have integrated encoders)
        this.frontLeftDriveMotor = robotMap.getDriveMotor(0);
        this.frontRightDriveMotor = robotMap.getDriveMotor(1);
        this.rearLeftDriveMotor = robotMap.getDriveMotor(2);
        this.rearRightDriveMotor = robotMap.getDriveMotor(3);

        //Configure drive encoder type
        this.frontLeftDriveMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative);
        this.frontRightDriveMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative);
        this.rearLeftDriveMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative);
        this.rearRightDriveMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative);

        this.frontLeftDriveMotor.setSelectedSensorPosition(0);
        this.frontRightDriveMotor.setSelectedSensorPosition(0);
        this.rearLeftDriveMotor.setSelectedSensorPosition(0);
        this.rearRightDriveMotor.setSelectedSensorPosition(0);
    }

    public double getModuleDistance(int moduleId) {
        //TODO Make this actually distance with ticks per inch
        switch (moduleId) {
            case 0:
                return frontLeftDriveMotor.getSelectedSensorPosition();

            case 1:
                return frontRightDriveMotor.getSelectedSensorPosition();

            case 2:
                return rearLeftDriveMotor.getSelectedSensorPosition();

            case 3:
                return rearRightDriveMotor.getSelectedSensorPosition();

            default:
                System.err.println(moduleId + " is not a valid swerve module ID!");
                return 0.0;
        }
    }
    public double getModuleRotation(int moduleId) {
        switch (moduleId) {
            case 0:
                return frontLeftRotationEncoder.getVoltage();

            case 1:
                return frontRightRotationEncoder.getVoltage();

            case 2:
                return rearLeftRotationEncoder.getVoltage();

            case 3:
                return rearRightRotationEncoder.getVoltage();

            default:
                System.err.println(moduleId + " is not a valid swerve module ID!");
                return 0.0;
        }
    }
}
