package frc.robot;

/**
 * A centralized file that keeps track of constants of the robot, such as motor ports and robot dimensions
 */
public class RobotMap {
    //Enforces a maximum safe speed of the motors. This may cause steering issues, so this should always be 1 unless debugging
    public static final double MAX_SAFE_SPEED_OVERRIDE = 0.2;

    //Measurements are in meters
    public static final double WHEELBASE = 0.65;
    public static final double TRACK_WIDTH = 0.53;

    //Maximum speed is in meters/second
    public static final double MAXIMUM_SPEED = 0.5;

    //Maximum acceleration is in meters/second/second
    public static final double MAXIMUM_ACCELERATION = 0.25;

    public static final int FRONT_LEFT_DRIVE_MOTOR = 0;
    public static final int FRONT_RIGHT_DRIVE_MOTOR = 2;
    public static final int REAR_LEFT_DRIVE_MOTOR = 4;
    public static final int REAR_RIGHT_DRIVE_MOTOR = 6;

    public static final double DRIVE_ENCODER_TICKS_PER_METER = 100000.0;

    public static final int FRONT_LEFT_ROTATION_MOTOR = 1;
    public static final int FRONT_RIGHT_ROTATION_MOTOR = 3;
    public static final int REAR_LEFT_ROTATION_MOTOR = 5;
    public static final int REAR_RIGHT_ROTATION_MOTOR = 7;

    public static final int FRONT_LEFT_ROTATION_ENCODER = 0;
    public static final int FRONT_RIGHT_ROTATION_ENCODER = 1;
    public static final int REAR_LEFT_ROTATION_ENCODER = 2;
    public static final int REAR_RIGHT_ROTATION_ENCODER = 3;
}
