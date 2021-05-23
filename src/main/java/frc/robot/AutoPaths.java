package frc.robot;

import java.util.Arrays;

import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.geometry.Translation2d;

public class AutoPaths {
    
    private AutoCommand testPath;

    public AutoPaths() {
        testPath = new AutoCommand(
            new Pose2d(0.0, 0.0, new Rotation2d(0.0)),
            Arrays.asList(
                new Translation2d(0.0, 1.0)
            ),
            new Pose2d(1.0, 1.0, new Rotation2d(0.0))
        );
    }

    public AutoCommand getTestPath() {
        return testPath;
    }
}
