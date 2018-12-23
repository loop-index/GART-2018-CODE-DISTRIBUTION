package org.usfirst.frc.team6520.robot.commandgroups;

import org.usfirst.frc.team6520.robot.Robot;
import org.usfirst.frc.team6520.robot.commands.C_AutoByDistance;
import org.usfirst.frc.team6520.robot.commands.C_AutoByTimer;
import org.usfirst.frc.team6520.robot.commands.C_AutoShoot;
import org.usfirst.frc.team6520.robot.commands.C_AutoTillPass_Left;
import org.usfirst.frc.team6520.robot.commands.C_AutoTillPass_Right;
import org.usfirst.frc.team6520.robot.commands.C_AutoTurn;
import org.usfirst.frc.team6520.robot.commands.C_DriveByPosition;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class CG_Left_Left extends CommandGroup {

	public CG_Left_Left () { //đi ngang cán cân, quay phải, bắn
							 //viết vào constructor để những lệnh này được gọi ngay khi CommandGroup được tạo

		/*
	 * |                                       |
	 * |                                       |
	 * |        ---------------------          |
	 * |        |     |       |     |          |
	 * |   ^ -->|     |       |     |          |
	 * |   |    ---------------------          |
	 * |   |                                   |
	 * |   |                                   |
	 * |  ---                                  |
	 * |  | |                                  |
	 * |  ---                                  |
	 * -----------------------------------------
	 */
		
	addSequential(new C_DriveByPosition(2.79, 1.16, 0, 1, 'R')); //thêm Command theo thứ tự
	addSequential(new C_AutoTurn(90));
	addSequential(new C_AutoShoot());
	
	/*
	 * Q: method addSequential() được định nghĩa ở đâu mà được gọi ở đây?
	 * 
	 * A: mỗi CommandGroup người dùng tạo đều inherit từ class CommandGroup, do đó mọi method và biến
	 * của parent class đều được inherit bởi child class và không cần định nghĩa lại (trừ khi muốn inherited
	 * method thực hiện một cái gì đó khác với parent). Method addSequential() là 1 method được inherit nên
	 * có thể gọi thẳng. 
	 */
	}
}
