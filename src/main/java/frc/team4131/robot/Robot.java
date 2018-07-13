package frc.team4131.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.ControlMode;

public class Robot extends IterativeRobot {

    //Drive motors
    TalonSRX leftOne = new TalonSRX(0);
    TalonSRX leftTwo = new TalonSRX(1);
    TalonSRX rightOne = new TalonSRX(2);
    TalonSRX rightTwo = new TalonSRX(3);

    //Controllers
    Joystick leftJoy = new Joystick(0);
    Joystick rightJoy = new Joystick(1);
    XboxController controller = new XboxController(2);
    GenericHID.Hand left = GenericHID.Hand.kLeft;
    GenericHID.Hand right = GenericHID.Hand.kRight;

    /*
     *Control Modes:
     *0: Joystick tank drive
     *1: Xbox arcade drive
     *2: Single joystick arcade drive
     *3: Xbox tank drive
     */
    public int controlMode;
    
    String message = "Joysticks must be in ports 1 and 2, the Xbox controller must be in port 3. You can change port assaignments via the USB tab of the driverstation.";

    @Override
    public void robotInit() {
        
        SmartDashboard.putNumber("Control Mode", -1.0);
        System.out.println(message);
    }
    
    @Override
    public void teleopPeriodic() {

        SmartDashboard.putString("IMPORTANT MESSAGE!", message);
        controlMode = (int)SmartDashboard.getNumber("Control Mode", -1.0);
        
        drive();

        SmartDashboard.putString("Control Mode", controlModeName());
    }

    public void drive() {

        if (controlMode == 0) {
            leftOne.set(ControlMode.PercentOutput, -leftJoy.getRawAxis(1));
            leftTwo.set(ControlMode.PercentOutput, -leftJoy.getRawAxis(1));

            rightOne.set(ControlMode.PercentOutput, rightJoy.getRawAxis(1));
            rightTwo.set(ControlMode.PercentOutput, rightJoy.getRawAxis(1));
        } else if (controlMode == 1) { 
            double straight = -controller.getY(left);
            double rotate = controller.getX(right);            

            leftOne.set(ControlMode.PercentOutput, rotate + straight);
            leftTwo.set(ControlMode.PercentOutput, rotate + straight);

            rightOne.set(ControlMode.PercentOutput, rotate - straight);
            rightTwo.set(ControlMode.PercentOutput, rotate - straight);
        } else if (controlMode == 2) {
            double straight = -leftJoy.getRawAxis(1);
            double rotate = leftJoy.getRawAxis(0);

            leftOne.set(ControlMode.PercentOutput, rotate + straight);
            leftTwo.set(ControlMode.PercentOutput, rotate + straight);

            rightOne.set(ControlMode.PercentOutput, rotate - straight);
            rightTwo.set(ControlMode.PercentOutput, rotate - straight);
        } else if (controlMode == 3) {
            leftOne.set(ControlMode.PercentOutput, -controller.getY(left));
            leftTwo.set(ControlMode.PercentOutput, -controller.getY(left));

            rightOne.set(ControlMode.PercentOutput, controller.getY(right));
            rightTwo.set(ControlMode.PercentOutput, controller.getY(right));
        } else {
            System.out.println("Error: Please set a valid control mode!");
        }
    }

    public String controlModeName() {

        if (controlMode == 0) {
            return "Joystick Tank Drive";
        } else if (controlMode == 1) {
            return "Xbox Arcade Drive";
        } else if (controlMode == 2) {
            return "Single Joystick Arcade Drive";
        } else if (controlMode == 3) {
            return "Xbox Tank Drive";
        } else {
            return "Error: Invalid control mode!";
        }
    }
}
