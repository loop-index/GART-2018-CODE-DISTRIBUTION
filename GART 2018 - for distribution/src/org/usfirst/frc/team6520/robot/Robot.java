/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team6520.robot;

import org.usfirst.frc.team6520.robot.commandgroups.CG_Left_Left;
import org.usfirst.frc.team6520.robot.commandgroups.CG_Left_Right;
import org.usfirst.frc.team6520.robot.commandgroups.CG_Mid_Left;
import org.usfirst.frc.team6520.robot.commandgroups.CG_Mid_Right;
import org.usfirst.frc.team6520.robot.commandgroups.CG_Right_Left;
import org.usfirst.frc.team6520.robot.commandgroups.CG_Right_Right;
import org.usfirst.frc.team6520.robot.commands.C_AutoByDistance;
import org.usfirst.frc.team6520.robot.commands.C_AutoByTimer;
import org.usfirst.frc.team6520.robot.commands.C_AutoShoot;
import org.usfirst.frc.team6520.robot.commands.C_AutoTillPass_Left;
import org.usfirst.frc.team6520.robot.commands.C_AutoTurn;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends IterativeRobot {
	
	public static OI oi = new OI(); 		//instantiate instance của OI
	public static String STARTING_POSITION; //Xâu ký tự tượng trưng cho vị trí xuất phát
											//
	public static String FIELD_CONFIG;		//Xâu ký tự tượng trưng cho thứ tự màu các cán cân trên sân đấu
											//xem đề 2018 để biết thêm chi tiết
	
	Command autonomousCommand;				//Command placeholder để sau này gán command khác vào
	SendableChooser<String> chooser = new SendableChooser<>();	//Menu lựa chọn để đưa vào SmartDashboard
																//<String> nghĩa là giá trị trả lại khi lựa
																//chọn sẽ là String
	
	public void initCamera() { //khởi động usbcamera trên robot
    	UsbCamera camera = CameraServer.getInstance().startAutomaticCapture();
    	camera.setResolution(1280, 720);
    }

	@Override
	public void robotInit() {
		initCamera();							  //gọi method khởi động camera
		
		/* Q: Tại sao method này phải gọi mà những method như robotInit(), autonomousInit()... không 
		 * cần phải gọi ở đâu cả?
		 * A: Những method có gắn tag @Override là những method được inherit từ parent class của class 
		 * robot, và đã được gọi sẵn somewhere else (vì nó là method có sẵn).
		 * 
		 * Những method người dùng tự viết thì cũng phải tự gọi.
		 */
		
		RobotMap.comp.setClosedLoopControl(true); //lệnh khởi động của máy nén khí
		
		//khởi động các cảm biến siêu âm
		RobotMap.ultra_behind_left.setAutomaticMode(true);	
		RobotMap.ultra_behind_right.setAutomaticMode(true);
		RobotMap.ultra_right.setAutomaticMode(true);
		RobotMap.ultra_left.setAutomaticMode(true);
		
		//thêm các lựa chọn vào menu
		chooser.addDefault("Default Auto", "M");
		chooser.addObject("Middle", "M");
		chooser.addObject("Left", "L");
		chooser.addObject("Right", "R");
		
		
	}

	@Override
	public void disabledInit() { //cái này để chạy lệnh khi robot vừa bị disabled which is
								 //not needed
	}

	@Override
	public void disabledPeriodic() { //cái này để chạy lệnh trong khi robot đang disable which is
									 //not needed
		Scheduler.getInstance().run();
	}

	@Override
	public void autonomousInit() { //bắt đầu chế độ tự động
		RobotMap.gyro.reset(); //reset cảm biến góc
		
		//nhận thứ tự màu từ Driver Station
		FIELD_CONFIG = DriverStation.getInstance().getGameSpecificMessage(); 

		//thu thập lựa chọn của người điều khiển từ SmartDashboard
		String autoSelected = SmartDashboard.getString("Auto Selector", "Default");
		
		//xét các lựa chọn:
		switch (autoSelected) {
		default:
			autonomousCommand = new C_AutoByDistance(2.75); //default command, trong trường hợp người điều khiển
															//không chọn gì từ menu
			break;
		case "M": //nếu bắt đầu ở giữa 
			//FIELD_CONFIG có 3 ký tự: switch của mình - scale trung tâm - switch đối phương.
			//Mục tiêu của mình chỉ là ghi điểm vào switch của mình nên xét ký tự đầu tiên.
			
			if (FIELD_CONFIG.charAt(0) == 'L'){ //nếu đích đến bên trái
				autonomousCommand = new CG_Mid_Left(); //gán Command Group tương ứng vào placeholder
				break;
			}
			else { //nếu đích đến bên phải
				autonomousCommand = new CG_Mid_Right(); //gán Command Group tương ứng vào placeholder
				break;
			}
			
		case "L": //nếu bắt đầu bên trái
			if (FIELD_CONFIG.charAt(0) == 'L'){
				autonomousCommand = new CG_Left_Left(); //gán Command Group tương ứng vào placeholder
				break;
			}
			else {
				autonomousCommand = new CG_Left_Right(); //gán Command Group tương ứng vào placeholder
				break;
			}
		case "R": //nếu bắt đầu bên phải
			if (FIELD_CONFIG.charAt(0) == 'L'){
				autonomousCommand = new CG_Right_Left(); //gán Command Group tương ứng vào placeholder
				break;
			}
			else {
				autonomousCommand = new CG_Right_Right(); //gán Command Group tương ứng vào placeholder
				break;
			}
		}
		
		//đoạn code này kích hoạt cái command đã gán cho placeholder. Nếu không có command nào được gán
		//hoặc không có 2 cái dòng dưới này thì command không chạy.
		if (autonomousCommand != null)
			autonomousCommand.start();
	}

	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
		RobotMap.update();
	}

	@Override
	public void teleopInit() { //bắt đầu vào chế độ điều khiển
		
		RobotMap.ss_Intake.raised = false; //reset state của launcher thành false (hạ)
	}

	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run(); //lệnh này dùng để liên tục nhận và chạy command, nhưng
									   //mình không dùng command mà dùng method trực tiếp nên
									   //không cần đến
		
		//cập nhật các giá trị trên SmartDashboard
		RobotMap.update();	
		RobotMap.teleOpUpdate();
		
		//các lệnh chạy cần continuous
		RobotMap.ss_Drivebase.driveTwoJoysticks();
		RobotMap.ss_Launcher.shoot();
		RobotMap.ss_Intake.intake();

	}

	@Override
	public void testPeriodic() {
	}
}
