package frc.robot;

public class Teleop {
    // Variables for robot classes
    private SwerveDrive swerveDrive = null;
    private OI joysticks = null;

    public Teleop(SwerveDrive swerveDrive) {
        // Initialize Classes
        this.joysticks = new OI();
        this.swerveDrive = swerveDrive;
    }

    public void teleopInit() {

    }

    public void teleopPeriodic() {
        //Update inputs from the controller
        joysticks.checkInputs();

        //Run periodic tasks on the swerve drive, setting the velocity and rotation
        swerveDrive.periodic(new SwerveCommand(-joysticks.getXVelocity() * RobotMap.MAXIMUM_SPEED, joysticks.getYVelocity() * RobotMap.MAXIMUM_SPEED, -joysticks.getRotationVelocity() * RobotMap.MAXIMUM_ROTATIONAL_SPEED, swerveDrive.getFieldOriented(), swerveDrive.getHeading()));
    
        if (joysticks.getToggleFieldOriented()) {
            swerveDrive.setFieldOriented(!swerveDrive.getFieldOriented());
            swerveDrive.resetHeading();
        }
    }
}
