package org.usfirst.frc.team6520.robot.commands;

import org.usfirst.frc.team6520.robot.Robot;
import org.usfirst.frc.team6520.robot.RobotMap;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class C_AutoTillPass_Right extends Command { //same but bên phải
	
	boolean encountered = false;
	boolean passed = false;
	
	double filtered_dist = RobotMap.kf.updateEstimate(RobotMap.ultra_right.getRangeMM());

    public C_AutoTillPass_Right() {
    }

    protected void initialize() {
    	
    }

    protected void execute() {
    	if (passed == false){
    		RobotMap.ss_Drivebase.driveGyro();
    		if (filtered_dist < 1500 && filtered_dist >= 100 && encountered == false){
    			encountered = true;
    			
    		}
    		if (encountered == true){
    			if (filtered_dist > 3500 || filtered_dist < 100){
        			passed = true;
    			}
    		}
    		filtered_dist = RobotMap.kf.updateEstimate(RobotMap.ultra_right.getRangeMM());
    		SmartDashboard.putBoolean("encountered?", encountered);
    		SmartDashboard.putBoolean("passed?", passed);
    		Timer.delay(0.01);
    	}
    		RobotMap.ss_Drivebase.stop();
    	
    }

    protected boolean isFinished() {
        return true;
    }

    protected void end() {
    	RobotMap.ss_Drivebase.stop();
    	passed = false;
    	encountered = false;
    }

    protected void interrupted() {
    	RobotMap.ss_Drivebase.stop();
    	passed = false;
    	encountered = false;
    }
}
