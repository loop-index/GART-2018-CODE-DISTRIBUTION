package org.usfirst.frc.team6520.robot.subsystems;

import org.usfirst.frc.team6520.robot.Robot;
import org.usfirst.frc.team6520.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class SS_Launcher extends Subsystem {
	
	//boolean alternative: 0 = false, 1 = true
	public int raised = 0;
	public double switchSpeed = 0.45;
	public double scaleSpeed = 0.6;
	public double scaleFinal = 0;
	
	public void setWheels(double speed){
		RobotMap.shoot1.set(-speed);
		RobotMap.shoot2.set(speed);
		RobotMap.shoot3.set(-speed);
		RobotMap.shoot4.set(speed);
	}
	
	public void loadCube(){
			setWheels(-0.5);
	}
	
	public void shoot(){
		if (Robot.oi.panel.getRawAxis(2) != 0 && Robot.oi.panel.getRawAxis(3) == 0){
			setWheels(switchSpeed);
		}
		else if (Robot.oi.panel.getRawAxis(2) != 0 && Robot.oi.panel.getRawAxis(3) != 0){
			scaleFinal = scaleSpeed + (12.5 - RobotMap.pdp.getVoltage()) * 0.08;
			setWheels(scaleFinal);
		}
		else {
			setWheels(0);
		}
		
	}
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

