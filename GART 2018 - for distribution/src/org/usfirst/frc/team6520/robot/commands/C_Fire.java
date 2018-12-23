package org.usfirst.frc.team6520.robot.commands;

import org.usfirst.frc.team6520.robot.RobotMap;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class C_Fire extends Command { //kéo piston bắn để thực hiện bắn
									  //kích hoạt bởi Button

    public C_Fire() {
    }

    protected void initialize() {
    	
    }

    protected void execute() {
    	RobotMap.solenoid_fire.set(DoubleSolenoid.Value.kReverse); //kéo piston	
    															   //chừng nào còn giữ thì kéo
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
    	RobotMap.solenoid_fire.set(DoubleSolenoid.Value.kForward); //xong thì đẩy trở lại
    }

    protected void interrupted() {
    	RobotMap.solenoid_fire.set(DoubleSolenoid.Value.kForward);
    }
}
