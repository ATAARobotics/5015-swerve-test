package frc.robot;

public class SwerveCommand {

    private double[] speeds;
    private double[] angles;

    public SwerveCommand(double speedVertical, double speedHorizontal, double speedRotation) {

        double wheelbase = RobotMap.WHEELBASE;
        double trackWidth = RobotMap.TRACK_WIDTH;

        //Calculate wheel speeds and angles
        double a,b,c,d;
        
        a = speedHorizontal - speedRotation * wheelbase / 2;
        b = speedHorizontal + speedRotation * wheelbase / 2;
        c = speedVertical - speedRotation * trackWidth / 2;
        d = speedVertical + speedRotation * trackWidth / 2;

        speeds = new double[]{
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

        //Get maximum wheel speed
        double maxSpeed = Math.max(Math.max(speeds[0], speeds[1]), Math.max(speeds[2], speeds[3]));

        //If any speed is larger than 1, all speeds need to be reduced to keep the ratio correct
        if (maxSpeed > 1.0) {
            for (int i = 0; i < speeds.length; i++) {
                speeds[i] /= maxSpeed;
            }
        }
    }

    public double getModuleSpeed(int moduleId) {
        return speeds[moduleId];
    }

    public double getModuleAngle(int moduleId) {
        return angles[moduleId];
    }

}
