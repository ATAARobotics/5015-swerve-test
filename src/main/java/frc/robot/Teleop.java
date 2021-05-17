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
        swerveDrive.periodic(new SwerveCommand(joysticks.getXVelocity(), joysticks.getYVelocity(), joysticks.getRVelocity() / 10));
    }
}
