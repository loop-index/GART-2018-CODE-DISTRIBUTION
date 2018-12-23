package org.usfirst.frc.team6520.robot.commands;

import org.usfirst.frc.team6520.robot.RobotMap;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class C_DeployIntake extends Command { //đóng/mở intake

    public C_DeployIntake() {
    	
    }

    protected void initialize() {
    	RobotMap.ss_Intake.open = 1 - RobotMap.ss_Intake.open; //toggle trạng thái của intake
    															
    	switch (RobotMap.ss_Intake.open) {
		case 0:
			RobotMap.solenoid_intake.set(Value.kForward); // = 0 thì đóng
			break;
		case 1:
			RobotMap.solenoid_intake.set(Value.kReverse); // = 1 thì mở
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
    }
}
