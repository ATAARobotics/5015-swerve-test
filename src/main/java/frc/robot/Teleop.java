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
        this.swerveDrive = robotMap.getSwerveDrive();
        this.gyro = robotMap.getGyro();
    }

    public void teleopInit() {

    }

    public void teleopPeriodic() {
        //Print debugging values to SmartDashboard
        SmartDashboard.putNumber("Gyro Value", gyro.getAngle());

        //Run periodic tasks on the swerve drive, setting the speed and rotation

        //TODO Figure out what units these values are in
        swerveDrive.periodic(new SwerveCommand(0.0, 0.0, 0.0));

        joysticks.checkInputs();
    }
}
