package frc.robot;

import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpiutil.math.MathUtil;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;

/**
 * A single swerve module on a swerve drive.
 * 
 * @author Jacob Guglielmin
 */
public class SwerveModule {
    
    //Restrictions on the minimum and maximum speed of the motors (0 to 1)
    private double maxDriveSpeed = 0.4;
    private double maxRotationSpeed = 0.4;
    private double minRotationSpeed = 0.03;

    private WPI_TalonSRX driveMotor;
    private VictorSPX rotationMotor;
    private AnalogInput rotationEncoder;

    //The rotation encoders all have their zero position in a different place, so keep track of how far off zero is from straight ahead
    private double rotationOffset;

    //The right-hand modules have their wheels facing the other way, so we need to invert their direction
    private double inversionConstant = 1.0;

    //The ID number of the module
    private int id = -1;

    //The name of the module - not used for much other than debugging
    private String name = "Unknown";

    //The speed (-1 to 1) to run the motor
    private double driveSpeed = 0.0;
    private double reverseMultiplier = 1.0;

    //Create a PID for controlling the angle of the module
    private PIDController angleController = new PIDController(0.7, 0.0, 0.001);

    /**
     * Creates a swerve module with the given hardware
     * 
     * @param driveMotor The Talon SRX running the wheel
     * @param rotationMotor The Victor SPX that rotates the wheel
     * @param rotationEncoder The input from the encoder. The cable for this MUST be in an "Analog In" port on the roboRIO
     * @param rotationOffset The distance from zero that forward is on the encoder
     * @param invertDrive Whether to invert the direction of the wheel
     */
    public SwerveModule(WPI_TalonSRX driveMotor, VictorSPX rotationMotor, AnalogInput rotationEncoder, double rotationOffset, boolean invertDrive) {
        this.driveMotor = driveMotor;
        this.rotationMotor = rotationMotor;
        this.rotationEncoder = rotationEncoder;
        this.rotationOffset = rotationOffset;
        if (invertDrive) {
            this.inversionConstant = -1.0;
        }

        //Set up the encoder from the drive motor
        this.driveMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative);
        this.driveMotor.setSelectedSensorPosition(0);

        //Because the rotation is on a circle, not a line, we want to take the shortest route to the setpoint - this function tells the PID it is on a circle from 0 to 2*Pi
        angleController.enableContinuousInput(-Math.PI, Math.PI);
    }

    /**
     * This function should run every teleopPeriodic
     */
    public void periodic() {
        //Set the drive speed based on the driveSpeed variable (0 to 1)
        driveMotor.set(ControlMode.PercentOutput, driveSpeed);

        //Get the rotation speed
        double rotationSpeed = -angleController.calculate(getAngle());
        //Clamp the value (not scale because faster is okay, it's on a PID)
        rotationSpeed = MathUtil.clamp(rotationSpeed, -maxRotationSpeed, maxRotationSpeed);
        if (rotationSpeed > -minRotationSpeed && rotationSpeed < minRotationSpeed) {
            rotationSpeed = 0.0;
        }

        //If the robot isn't moving at all, don't rotate the module
        if (driveSpeed != 0.0) {
            //Set the rotation motor speed based on the next value from the angle PID, clamped to not exceed the maximum speed
            rotationMotor.set(ControlMode.PercentOutput, rotationSpeed);
        }
    }

    /**
     * Sets the speed to drive the module
     * 
     * @param speed The speed to drive the module. This value will be scaled to not exceed the maximum motor speed
     */
    public void setDriveSpeed(double speed) {
        this.driveSpeed = speed / maxDriveSpeed * inversionConstant * reverseMultiplier;
    }

    /**
     * Sets the target in radians for the angle PID
     * 
     * @param angle The angle to try to reach. This value should be between 0 and 2*Pi
     */
    public void setTargetAngle(double angle) {
        double currentAngle = getAngle();
        angle += 2.0 * Math.PI;
        angle %= 2.0 * Math.PI;
        currentAngle += 2.0 * Math.PI;
        currentAngle %= 2.0 * Math.PI;

        if (Math.abs(angle - currentAngle) > Math.PI / 2.0 && Math.min(angle, Math.abs(2.0 * Math.PI - angle)) + Math.min(currentAngle, Math.abs(2.0 * Math.PI - currentAngle)) > Math.PI / 2.0) {
            angle += Math.PI;
            angle %= 2.0 * Math.PI;
            reverseMultiplier = -1.0;
        } else {
            reverseMultiplier = 1.0;
        }

        angleController.setSetpoint(angle - Math.PI);
    }

    /**
     * Get the distance that the drive wheel has turned
     */
    public double getDistance() {
        //TODO Make this actually distance with ticks per inch
        return driveMotor.getSelectedSensorPosition();
    }

    /**
     * Gets the angle in radians of the module from -Pi to Pi
     */
    public double getAngle() {
        double angle = ((rotationEncoder.getVoltage() / RobotController.getVoltage5V()) * 2.0 * Math.PI) + rotationOffset;
        angle %= 2.0 * Math.PI;
        if (angle < 0.0) {
            angle += 2.0 * Math.PI;
        }
        angle -= Math.PI;

        return angle;
    }

    /**
     * Gets the target angle in radians from the angle PID
     */
    public double getTargetAngle() {
        return angleController.getSetpoint();
    }

    /**
     * Set the id of the module
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Get the id of the module
     */
    public int getId() {
        return this.id;
    }

    /**
     * Set the name of the module
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get the name of the module
     */
    public String getName() {
        return this.name;
    }
}
