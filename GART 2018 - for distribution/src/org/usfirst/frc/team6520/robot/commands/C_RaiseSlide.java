package org.usfirst.frc.team6520.robot.commands;

import org.usfirst.frc.team6520.robot.RobotMap;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class C_RaiseSlide extends Command { //nâng hạ máng intake
	   										//same as DeployIntake và RaiseLauncher

    public C_RaiseSlide() {
        
    }

    protected void initialize() {
    	RobotMap.ss_Intake.raised = !RobotMap.ss_Intake.raised;
    	if (RobotMap.ss_Intake.raised == false){
    		RobotMap.solenoid_slide.set(true); //method hơi khác những solenoid khác vì đây là solenoid đơn
    										   //chỉ có 2 state thay vì 3 như solenoid kép
    	}
    	else {
    		RobotMap.solenoid_slide.set(false);
    	}
    }

    protected void execute() {
    }
    
    protected boolean isFinished() {
        return true;
    }

    protected void end() {
    }

    protected void interrupted() {
    	RobotMap.ss_Intake.raised = false;
    	RobotMap.solenoid_slide.set(false);
    }
}
