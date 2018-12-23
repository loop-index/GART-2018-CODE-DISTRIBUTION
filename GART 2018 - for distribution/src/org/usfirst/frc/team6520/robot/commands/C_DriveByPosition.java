package org.usfirst.frc.team6520.robot.commands;

import org.usfirst.frc.team6520.robot.Robot;
import org.usfirst.frc.team6520.robot.RobotMap;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class C_DriveByPosition extends Command { //gần giống AutoTillPass nhưng dừng lại khi đi ngang
												 //thay vì đi quá
												 //also cái này Đức Đoàn viết
    
    boolean state = false;
    double distance;
    double maxSide;
    char sideSensor;
    
	public C_DriveByPosition(double d, double s, double a, int m, char ss) {
		//a với m redundant
		this.distance = d * 1000;
		this.maxSide = s;
		this.sideSensor = ss;
    }

    protected void initialize() {
    }

    protected void execute() {
    	
    	double dL = RobotMap.ultra_behind_left.getRangeMM();
    	double dR = RobotMap.ultra_behind_right.getRangeMM();
    	double dSR = RobotMap.ultra_right.getRangeMM();
    	double dSL = RobotMap.ultra_left.getRangeMM();
    	
	    if (sideSensor == 'R') {	
			if ((dL > distance || dR > distance) && (dSR > 4 && dSR <= maxSide)) {
	    		RobotMap.ss_Drivebase.stop();
	    		state = true;
	    	} else {
	    		RobotMap.ss_Drivebase.driveGyro();
	    	} 
	    } else {
    		if ((dL > distance || dR > distance) && (dSL > 4 && dSL <= maxSide)) {
	    		RobotMap.ss_Drivebase.stop();
	    		state = true;
	    	} else {
	    		RobotMap.ss_Drivebase.driveGyro();
	    	} 
    	}
    	
    }

    protected boolean isFinished() {
        return state;
    }

    protected void end() {
    	RobotMap.ss_Drivebase.stop();
    }

    protected void interrupted() {
    }
}
