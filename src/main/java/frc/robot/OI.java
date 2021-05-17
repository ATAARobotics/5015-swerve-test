package frc.robot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
class OI {

    private XboxController driveStick = new XboxController(0);
    private double xVelocity;
    private double yVelocity;
    private double rVelocity;

    public OI() {
        
    }

    //periodic function to update controller input
    public void checkInputs() {
        xVelocity = driveStick.getX(Hand.kLeft);
        yVelocity = driveStick.getY(Hand.kLeft);
        rVelocity = driveStick.getX(Hand.kRight);
        if (xVelocity < 0.3 && xVelocity > -0.3) { xVelocity = 0; }
        if (yVelocity < 0.3 && yVelocity > -0.3) { yVelocity = 0; }
        if (rVelocity < 0.3 && rVelocity > -0.3) { rVelocity = 0; }
    }

    //Getter functions for controls
    public double getXVelocity() {
        return xVelocity;
    }
    public double getYVelocity() {
        return yVelocity;
    }
    public double getRVelocity() {
        return rVelocity;
    }
}
