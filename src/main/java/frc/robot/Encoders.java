package frc.robot;

import edu.wpi.first.wpilibj.AnalogInput;

/**
 * Encoder code
 */
public class Encoders {

    private AnalogInput frontLeftRotation;
    private AnalogInput frontRightRotation;
    private AnalogInput rearLeftRotation;
    private AnalogInput rearRightRotation;

    public Encoders(RobotMap robotMap) {
        this.frontLeftRotation = robotMap.getRotationEncoder(0);
        this.frontRightRotation = robotMap.getRotationEncoder(1);
        this.rearLeftRotation = robotMap.getRotationEncoder(2);
        this.rearRightRotation = robotMap.getRotationEncoder(3);
    }

    public double getModuleRotation(int moduleId) {
        switch (moduleId) {
            case 0:
                return frontLeftRotation.getVoltage();

            case 1:
                return frontRightRotation.getVoltage();

            case 2:
                return rearLeftRotation.getVoltage();

            case 3:
                return rearRightRotation.getVoltage();

            default:
                System.err.println(moduleId + " is not a valid swerve module ID!");
                return 0.0;
        }
    }
}
