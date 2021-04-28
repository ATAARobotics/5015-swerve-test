package frc.robot;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.vision.CameraMode;
import frc.robot.vision.LimeLight;
import edu.wpi.first.wpilibj.RobotController;

public class Robot extends TimedRobot {
    // Create objects to run auto and teleop code
    public Teleop teleop = null;
    RobotMap robotMap = null;
    SwerveDrive swerveDrive = null;
    Gyro gyro = null;

    public Robot() {
        robotMap = new RobotMap();
        teleop = new Teleop(robotMap);
        swerveDrive = robotMap.swerveDrive;
    }

    @Override
    public void robotInit() {
        teleop.teleopInit();
    }

    /**
    * This function is called every robot packet, no matter the mode. Use
    * this for items like diagnostics that you want ran during disabled,
    * autonomous, teleoperated and test.
    *
    * This runs after the mode specific periodic functions, but before
    * LiveWindow and SmartDashboard integrated updating.
    *
    */
    @Override
    public void robotPeriodic() {

    }

    @Override
    public void disabledInit() {
        super.disabledInit();
    }

    @Override
    public void disabledPeriodic() {

    }

    @Override
    public void autonomousInit() {
        
    }

    @Override
    public void autonomousPeriodic() {

    }

    @Override
    public void teleopInit() {
        teleop.teleopInit();
    }

    @Override
    public void teleopPeriodic() {
        teleop.TeleopPeriodic();
    }
}
