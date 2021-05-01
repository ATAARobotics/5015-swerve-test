package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Teleop {
    // Variables for robot classes
    private SwerveDrive swerveDrive = null;
    private OI joysticks = null;
    private Gyro gyro = null;
    private Encoders encoders = null;

    public Teleop(RobotMap robotMap) {
        // Initialize Classes
        this.joysticks = new OI();
        this.swerveDrive = robotMap.getSwerveDrive();
        this.gyro = robotMap.getGyro();
        this.encoders = robotMap.getEncoders();
    }

    public void teleopInit() {

    }

    public void teleopPeriodic() {
        SmartDashboard.putNumber("Gyro Value", gyro.getAngle());
        SmartDashboard.putNumber("Front Left Rotation", encoders.getModuleRotation(0));
        SmartDashboard.putNumber("Front Right Rotation", encoders.getModuleRotation(1));
        SmartDashboard.putNumber("Rear Left Rotation", encoders.getModuleRotation(2));
        SmartDashboard.putNumber("Rear Right Rotation", encoders.getModuleRotation(3));

        joysticks.checkInputs();
    }
}
