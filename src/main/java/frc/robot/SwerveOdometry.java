package frc.robot;

import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;

public class SwerveOdometry {

    private Pose2d pose;

    private double lastUpdate = -1.0;

    public SwerveOdometry(Pose2d initialPose) {
        this.pose = initialPose;
    }

    /**
     * Updates the current location of the robot
     * @param command The most recent command given to the robot
     * @param currentAngle The current angle given by the gyro from -Pi to Pi
     * @param timestamp The current timestamp
     */
    public Pose2d update(SwerveCommand command, double currentAngle, double timestamp) {
        //Get the amount of time since the last update
        double period;
        if (lastUpdate >= 0) {
            period = timestamp - lastUpdate;
        } else {
            period = 0.0;
        }

        lastUpdate = timestamp;

        double distanceHorizontal = command.getVelocityHorizontal() * period;
        double distanceVertical = command.getVelocityVertical() * period;

        pose = new Pose2d(pose.getX() + distanceHorizontal, pose.getY() + distanceVertical, new Rotation2d(currentAngle));

        return pose;
    }

    /**
     * Gets the current pose of the robot as a Pose2d object
     */
    public Pose2d getPose() {
        return pose;
    }
}
