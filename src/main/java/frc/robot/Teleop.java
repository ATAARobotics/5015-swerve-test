package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Teleop {
    // Variables for robot classes
    private SwerveDrive swerveDrive = null;
    private OI joysticks = null;
    private Gyro gyro = null;

    public Teleop(RobotMap robotMap) {
        // Initialize Classes
        this.joysticks = new OI();
        this.swerveDrive = robotMap.swerveDrive;
        this.gyro = robotMap.getGyro();
    }

    public void teleopInit() {

    }

    public void TeleopPeriodic() {
        SmartDashboard.putNumber("Gyro Value", gyro.getAngle());

        joysticks.checkInputs();

        //This is where the robot is driven
    }
}
