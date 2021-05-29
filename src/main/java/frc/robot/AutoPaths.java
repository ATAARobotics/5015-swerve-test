package frc.robot;

import java.util.ArrayList;
import java.util.Arrays;

import edu.wpi.first.wpilibj.geometry.Translation2d;

public class AutoPaths {
    
    //Path variable declarations

    private AutoCommand testPath;

    public AutoPaths() {
        testPath = new AutoCommand(
            new ArrayList<Translation2d>(Arrays.asList(
                new Translation2d(5.0, 5.0),
                new Translation2d(5.0, 6.0),
                new Translation2d(6.0, 6.0)
            )),
            0.0
        );
    }


    //Getter functions for paths

    public AutoCommand getTestPath() {
        return testPath;
    }
}
