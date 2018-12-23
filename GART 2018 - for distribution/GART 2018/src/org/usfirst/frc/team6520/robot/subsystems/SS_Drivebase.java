package org.usfirst.frc.team6520.robot.subsystems;

import org.usfirst.frc.team6520.robot.Robot;
import org.usfirst.frc.team6520.robot.RobotMap;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class SS_Drivebase extends Subsystem {

	public final double speed = 0.4;
	public double leftSpeed = speed;
	public double rightSpeed = -speed;
	double boostSpeed = 1;

	double accErr = 0.2;
	double turnVal = 0.2;
	
	public void joystick(){ //for use with the Logitech joystick only
		if(Robot.oi.gamepad.getRawAxis(2) < 0.5 && Robot.oi.gamepad.getRawAxis(2) > -0.5){
			RobotMap.left.set(Robot.oi.gamepad.getRawAxis(1) * leftSpeed);
			RobotMap.right.set(Robot.oi.gamepad.getRawAxis(1) * rightSpeed);
		}
		else if (Robot.oi.gamepad.getRawAxis(2) > 0.5){
			RobotMap.left.set(speed);
			RobotMap.right.set(speed);
		}
		else if (Robot.oi.gamepad.getRawAxis(2) < -0.5){
			RobotMap.left.set(-speed);
			RobotMap.right.set(-speed);
		}
	}

	public void leftSide() {
		RobotMap.left.set(Robot.oi.gamepad.getRawAxis(5) * leftSpeed);
	}

	public void rightSide() {
		RobotMap.right.set(Robot.oi.gamepad.getRawAxis(1) * rightSpeed);
	}

	public void boost() {
		if (Robot.oi.gamepad.getRawAxis(2) == 1) {
			leftSpeed = boostSpeed;
			rightSpeed = -boostSpeed;
		} else {
			leftSpeed = speed;
			rightSpeed = -speed;
		}
	}

	public void driveTwoJoysticks() {
//		joystick();
		leftSide();
		rightSide();
		boost();
	}

	public void driveGyro() {
		double currentAngle = RobotMap.gyro.getAngle();
		double turnVal = currentAngle;
		if (currentAngle > 0.3)
			turnVal = 0.3;
		
		if (currentAngle > accErr){
			RobotMap.left.set(-speed * (1 + turnVal));
			RobotMap.right.set(speed);
			
			while (RobotMap.gyro.getAngle() > 0);
			
			RobotMap.left.set(-speed);
			RobotMap.right.set(speed);
			
		}
		else if (currentAngle < -accErr){
			
			RobotMap.left.set(-speed);
			RobotMap.right.set(speed * (1 + turnVal));
			
			while (RobotMap.gyro.getAngle() < 0);
			
			RobotMap.left.set(-speed);
			RobotMap.right.set(speed);
			
		}
		else {
			RobotMap.left.set(-speed);
			RobotMap.right.set(speed);
		}
	}
	
	public void turn(double value) {
    	double startAngle = RobotMap.gyro.getAngle();
    	double angle = startAngle;
    	double speed = -0.4;
	    if (value >= 0) {	
    		while (Math.abs(angle - startAngle) <= value) {
    			RobotMap.left.set(-speed);
    			RobotMap.right.set(-speed);
	    		angle = RobotMap.gyro.getAngle();
	    		SmartDashboard.putNumber("angle", angle);
    		}
    		RobotMap.left.set(speed);
    		RobotMap.right.set(speed);
	    } else {
	    	while (Math.abs(angle - startAngle) <= -value) {
	    		RobotMap.left.set(speed);
	    		RobotMap.right.set(speed);
	    		angle = RobotMap.gyro.getAngle();
	    		SmartDashboard.putNumber("angle", angle);
	    	}
	    	RobotMap.left.set(-speed);
	    	RobotMap.right.set(-speed);
	    } 	
    	Timer.delay(0.04);
    	RobotMap.ss_Drivebase.stop();
	}

	public void stop() {
		RobotMap.left.set(0);
		RobotMap.right.set(0);
	}

	protected void initDefaultCommand() {

	}
}
