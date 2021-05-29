package frc.robot;

import java.util.Arrays;

import edu.wpi.first.wpilibj.geometry.Translation2d;

public class AutoPaths {
    
    //Path variable declarations

    private AutoCommand testPath;

    public AutoPaths() {
        testPath = new AutoCommand(
            Arrays.asList(
                new Translation2d(2.0, 2.0),
                new Translation2d(2.0, 3.0),
                new Translation2d(3.0, 3.0),
                new Translation2d(3.0, 4.0),
                new Translation2d(2.0, 5.0),
                new Translation2d(3.0, 6.0),
                new Translation2d(2.0, 7.0)
            ),
            0.0
        );
    }


    //Getter functions for paths

    public AutoCommand getTestPath() {
        return testPath;
    }
}
