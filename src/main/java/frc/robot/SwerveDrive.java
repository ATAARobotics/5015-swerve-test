package frc.robot;

import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Manages a swerve drive
 * 
 * @author Jacob Guglielmin
 */
public class SwerveDrive {

    //An array of all the modules on the swerve drive
    private SwerveModule[] swerveModules;

    public SwerveDrive(RobotMap robotMap) {
        //Initialize four swerve modules using the SwerveModule class
        SwerveModule frontLeftModule = new SwerveModule(new WPI_TalonSRX(RobotMap.FRONT_LEFT_DRIVE_MOTOR), new VictorSPX(RobotMap.FRONT_LEFT_ROTATION_MOTOR), new AnalogInput(RobotMap.FRONT_LEFT_ROTATION_ENCODER), -1.2);
        frontLeftModule.setName("Front Left");
        SwerveModule frontRightModule = new SwerveModule(new WPI_TalonSRX(RobotMap.FRONT_RIGHT_DRIVE_MOTOR), new VictorSPX(RobotMap.FRONT_RIGHT_ROTATION_MOTOR), new AnalogInput(RobotMap.FRONT_RIGHT_ROTATION_ENCODER), -4.2);
        frontRightModule.setName("Front Right");
        SwerveModule rearLeftModule = new SwerveModule(new WPI_TalonSRX(RobotMap.REAR_LEFT_DRIVE_MOTOR), new VictorSPX(RobotMap.REAR_LEFT_ROTATION_MOTOR), new AnalogInput(RobotMap.REAR_LEFT_ROTATION_ENCODER), -5.4);
        rearLeftModule.setName("Rear Left");
        SwerveModule rearRightModule = new SwerveModule(new WPI_TalonSRX(RobotMap.REAR_RIGHT_DRIVE_MOTOR), new VictorSPX(RobotMap.REAR_RIGHT_ROTATION_MOTOR), new AnalogInput(RobotMap.REAR_RIGHT_ROTATION_ENCODER), -1.0);
        rearRightModule.setName("Rear Right");

        //Put the swerve modules in an array so we can process them easier
        swerveModules = new SwerveModule[]{
            frontLeftModule,
            frontRightModule,
            rearLeftModule,
            rearRightModule
        };
    }

    /**
     * This function should be run every telopPeriodic
     */
    public void periodic() {
        //Execute functions on each swerve module
        for (SwerveModule module : swerveModules) {
            module.periodic();

            SmartDashboard.putNumber(module.getName() + " Distance", module.getDistance());
            SmartDashboard.putNumber(module.getName() + " Angle", module.getAngle());
            SmartDashboard.putNumber(module.getName() + " Angle Setpoint", module.getTargetAngle());
        }
    }
}
