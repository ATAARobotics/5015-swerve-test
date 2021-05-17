package frc.robot;

public class SwerveCommand {

    private double velocityHorizontal;
    private double velocityVertical;
    private double velocityRotation;

    private double[] velocities;
    private double[] angles;

    public SwerveCommand(double velocityHorizontal, double velocityVertical, double velocityRotation) {

        this.velocityHorizontal = velocityHorizontal;
        this.velocityVertical = velocityVertical;
        this.velocityRotation = velocityRotation;

        double wheelbase = RobotMap.WHEELBASE;
        double trackWidth = RobotMap.TRACK_WIDTH;

        //Calculate wheel velocities and angles
        double a,b,c,d;
        
        a = velocityHorizontal - velocityRotation * wheelbase / 2;
        b = velocityHorizontal + velocityRotation * wheelbase / 2;
        c = velocityVertical - velocityRotation * trackWidth / 2;
        d = velocityVertical + velocityRotation * trackWidth / 2;

        velocities = new double[]{
            Math.sqrt(Math.pow(b, 2) + Math.pow(d, 2)),
            Math.sqrt(Math.pow(b, 2) + Math.pow(c, 2)),
            Math.sqrt(Math.pow(a, 2) + Math.pow(d, 2)),
            Math.sqrt(Math.pow(a, 2) + Math.pow(c, 2))
        };
        angles = new double[]{
            Math.atan2(b, d),
            Math.atan2(b, c),
            Math.atan2(a, d),
            Math.atan2(a, c)
        };

        //Get the maximum wheel speed
        double maxSpeed = Math.max(Math.max(Math.abs(velocities[0]), Math.abs(velocities[1])), Math.max(Math.abs(velocities[2]), Math.abs(velocities[3])));

        //If any speed is larger than the maximum speed, all speeds need to be reduced to keep the ratio between the speeds correct
        if (maxSpeed > RobotMap.MAXIMUM_SPEED) {
            for (int i = 0; i < velocities.length; i++) {
                velocities[i] /= maxSpeed;
            }
        }
    }

    /**
     * Gets the velocity of a specific module in meters/second
     * @param moduleId The ID of the module to get
     */
    public double getModuleVelocity(int moduleId) {
        return velocities[moduleId];
    }

    /**
     * Gets the angle that the module should be set to
     * @param moduleId The ID of the module to get
     */
    public double getModuleAngle(int moduleId) {
        return angles[moduleId];
    }

    public double getVelocityHorizontal() {
        return velocityHorizontal;
    }

    public double getVelocityVertical() {
        return velocityVertical;
    }

    public double getVelocityRotation() {
        return velocityRotation;
    }
}
