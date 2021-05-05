package frc.robot;

public class Teleop {
    // Variables for robot classes
    private SwerveDrive swerveDrive = null;
    private OI joysticks = null;

    public Teleop(SwerveDrive swerveDrive) {
        // Initialize Classes
        this.joysticks = new OI();
    }

    public void teleopInit() {

    }

    public void teleopPeriodic() {
        //Run periodic tasks on the swerve drive, setting the speed and rotation
        //TODO Figure out what units these values are in
        swerveDrive.periodic(new SwerveCommand(0.0, 0.0, 0.0));

        joysticks.checkInputs();
    }
}
