package frc.robot;

/**
 * A centralized file that keeps track of constants of the robot, such as motor ports and robot dimensions
 */
public class RobotMap {
    //Enforces a maximum safe speed of the motors. This may cause steering issues, so this should always be 1 unless debugging
    public static final double MAX_SAFE_SPEED_OVERRIDE = 0.5;

    //Measurements are in meters
    public static final double WHEELBASE = 0.65;
    public static final double TRACK_WIDTH = 0.53;

    //Maximum linear speed is in meters/second
    public static final double MAXIMUM_SPEED = 0.25;
    //Used only for auto. Maximum acceleration is in meters/second/second
    public static final double MAXIMUM_ACCELERATION = 0.25;

    //Maximum rotational speed is in radians/second
    public static final double MAXIMUM_ROTATIONAL_SPEED = Math.PI;
    //Maximum rotational speed is in radians/second
    public static final double MAXIMUM_ROTATIONAL_ACCELERATION = Math.PI / 2.0;

    public static final int FRONT_LEFT_DRIVE_MOTOR = 0;
    public static final int FRONT_RIGHT_DRIVE_MOTOR = 2;
    public static final int REAR_LEFT_DRIVE_MOTOR = 4;
    public static final int REAR_RIGHT_DRIVE_MOTOR = 6;

    public static final double DRIVE_ENCODER_TICKS_PER_METER = 1564.0;

    public static final int FRONT_LEFT_ROTATION_MOTOR = 1;
    public static final int FRONT_RIGHT_ROTATION_MOTOR = 3;
    public static final int REAR_LEFT_ROTATION_MOTOR = 5;
    public static final int REAR_RIGHT_ROTATION_MOTOR = 7;

    public static final int FRONT_LEFT_ROTATION_ENCODER = 0;
    public static final int FRONT_RIGHT_ROTATION_ENCODER = 1;
    public static final int REAR_LEFT_ROTATION_ENCODER = 2;
    public static final int REAR_RIGHT_ROTATION_ENCODER = 3;

    //Set this to true if you want to visualize the robot's movement during auto
    public static final boolean AUTO_PATH_LOGGING_ENABLED = true;
}
