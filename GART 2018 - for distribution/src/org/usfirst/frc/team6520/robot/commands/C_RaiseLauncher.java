package org.usfirst.frc.team6520.robot.commands;

import org.usfirst.frc.team6520.robot.RobotMap;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class C_RaiseLauncher extends Command { //nâng hạ launcher
											   //same as DeployIntake

    public C_RaiseLauncher() {
        
    }

    protected void initialize() {
    	RobotMap.ss_Launcher.raised = 1 - RobotMap.ss_Launcher.raised;
    	switch (RobotMap.ss_Launcher.raised) {
		case 1:
			RobotMap.solenoid_launcher.set(Value.kForward);
			break;
		case 0:
			RobotMap.solenoid_launcher.set(Value.kReverse);
			break;
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
    	RobotMap.ss_Launcher.raised = 0;
    	RobotMap.solenoid_launcher.set(Value.kReverse);
    }
}
