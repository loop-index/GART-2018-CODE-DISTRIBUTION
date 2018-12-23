package org.usfirst.frc.team6520.robot.commands;

import org.usfirst.frc.team6520.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class C_AutoTurn extends Command {

	double angle;
    public C_AutoTurn(double value) {
    	this.angle = value;
    }

    protected void initialize() {
    	RobotMap.ss_Drivebase.turn(angle); //gọi thẳng method từ subsystem
    }

    protected void execute() {
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
    }

    protected void interrupted() {
    }
}
