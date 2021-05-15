//TODO Make wheel speeds the same
//TODO Test field oriented
//TODO Test no spinning to 0

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

    private Gyro gyro;

    //Whether the swerve should be field-oriented
    boolean fieldOriented = false;

    //An array of all the modules on the swerve drive
    private SwerveModule[] swerveModules;

    public SwerveDrive(RobotMap robotMap, Gyro gyro) {
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
    }

    /**
     * This function should be run every telopPeriodic
     */
    public void periodic(SwerveCommand command) {
        SmartDashboard.putNumber("Gyro Value", gyro.getAngle());

        //Execute functions on each swerve module
        for (SwerveModule module : swerveModules) {
            //Do the math to get the speed and angle of the module, and set the speed and angle target accordingly
            module.setDriveSpeed(command.getModuleSpeed(module.getId()));
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
    }

    /**
     * Sets whether the robot should be field-oriented
     */
    public void setFieldOriented(boolean fieldOriented) {
        this.fieldOriented = fieldOriented;
    }
}
