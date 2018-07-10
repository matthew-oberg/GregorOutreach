package frc.team4131.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.command.Scheduler;

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
     */
    public int controlMode;

    @Override
    public void teleopPeriodic() {
        Scheduler.getInstance().run();
        drive();
    }

    public void drive() {
        double straight = controller.getY(left);
        double rotate = controller.getX(right);

        switch (controlMode) {
            case 0:
                leftOne.set(ControlMode.PercentOutput, leftJoy.getRawAxis(1));
                leftTwo.set(ControlMode.PercentOutput, leftJoy.getRawAxis(1));

                rightOne.set(ControlMode.PercentOutput, -rightJoy.getRawAxis(1));
                rightTwo.set(ControlMode.PercentOutput, -rightJoy.getRawAxis(1));
            case 1:
                leftOne.set(ControlMode.PercentOutput, rotate + straight);
                leftTwo.set(ControlMode.PercentOutput, rotate + straight);

                rightOne.set(ControlMode.PercentOutput, rotate - straight);
                rightTwo.set(ControlMode.PercentOutput, rotate - straight);
            default:
                System.out.println("Error: Please enter a valid control mode!");
        }
    }
}