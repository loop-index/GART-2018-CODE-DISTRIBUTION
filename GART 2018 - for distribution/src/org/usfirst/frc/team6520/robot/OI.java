/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team6520.robot;

import org.usfirst.frc.team6520.robot.commands.C_DeployIntake;
import org.usfirst.frc.team6520.robot.commands.C_Fire;
import org.usfirst.frc.team6520.robot.commands.C_RaiseLauncher;
import org.usfirst.frc.team6520.robot.commands.C_RaiseSlide;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

public class OI {
	public Joystick gamepad = new Joystick(0); //tay cầm dùng để lái robot
	public Joystick panel = new Joystick(1);   //tay cầm dùng điều khiển chức năng của robot
	
	//các nút chức năng đều nằm trên controller 2 (panel)
	public Button LAUNCHER_B = new JoystickButton(panel, 2); 
	public Button FIRE_A = new JoystickButton(panel, 1);
	public Button SLIDE_X = new JoystickButton(panel, 3);
	public Button INTAKE_Y = new JoystickButton(panel, 4);
	public Button LOADCUBE_RIGHTBACK = new JoystickButton(panel, 6); //unused
	
	public OI(){ //method được gọi bên trong constructor của OI
		LAUNCHER_B.whenPressed(new C_RaiseLauncher());
		INTAKE_Y.whenPressed(new C_DeployIntake());
		FIRE_A.whileHeld(new C_Fire());
		SLIDE_X.whenPressed(new C_RaiseSlide()); 
	}
}
