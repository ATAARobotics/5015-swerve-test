package frc.robot;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.vision.CameraMode;
import frc.robot.vision.LimeLight;

public class Teleop {
    // Variables for robot classes
    private SwerveDrive swerveDrive = null;
    private Encoders encoders = null;
    private OI joysticks = null;
    private Gyro gyro = null;

    public Teleop(RobotMap robotMap) {
        // Initialize Classes
        this.joysticks = new OI();
        this.swerveDrive = robotMap.swerveDrive;
        this.encoders = robotMap.getDriveEncoders();
        this.gyro = robotMap.getGyro();
    }

    public void teleopInit() {
        encoders.reset();
    }

    public void TeleopPeriodic() {
        SmartDashboard.putNumber("Gyro Value", gyro.getAngle());

        joysticks.checkInputs();

        //This is where the robot is driven
    }
}
