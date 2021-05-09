package frc.robot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
class OI {

    private XboxController driveStick = new XboxController(0);
    private double xSpeed;
    private double ySpeed;
    private double rSpeed;

    public OI() {
        
    }

    //periodic function to update controller input
    public void checkInputs() {
        xSpeed = driveStick.getX(Hand.kLeft);
        ySpeed = driveStick.getY(Hand.kLeft);
        rSpeed = driveStick.getX(Hand.kRight);
    }

    //Getter functions for controls
    public double getXSpeed() {
        return xSpeed;
    }
    public double getYSpeed() {
        return ySpeed;
    }
    public double getRSpeed() {
        return rSpeed;
    }
}
