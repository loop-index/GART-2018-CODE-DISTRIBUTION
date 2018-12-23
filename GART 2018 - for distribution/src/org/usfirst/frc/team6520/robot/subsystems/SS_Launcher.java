package org.usfirst.frc.team6520.robot.subsystems;

import org.usfirst.frc.team6520.robot.Robot;
import org.usfirst.frc.team6520.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class SS_Launcher extends Subsystem {
	
	public int raised = 0; //giá trị nâng/hạ launcher - toggle được
	public double switchSpeed = 0.55; //tốc độ bắn thấp
	public double scaleSpeed = 1.0; //tốc độ bắn cao
	
	public void setWheels(double speed){ //đặt tất cả bánh theo một tốc độ (để đỡ phải copy paste nhiều)
		RobotMap.shoot1.set(-speed);
		RobotMap.shoot2.set(speed);
		RobotMap.shoot3.set(-speed);
		RobotMap.shoot4.set(speed);
	}
	
	public void loadCube(){ //hút hộp
			setWheels(-0.8);
	}
	
	public void shoot(){ //bắn
		if (Robot.oi.panel.getRawAxis(2) != 0 && Robot.oi.panel.getRawAxis(3) == 0){
			//khi nút phía sau bên trái được giữ thì quay bánh bắn tốc độ thấp
			setWheels(switchSpeed);
		}
		else if (Robot.oi.panel.getRawAxis(2) != 0 && Robot.oi.panel.getRawAxis(3) != 0){
			//khi cả hai nút phía sau được giữ thì quay bánh bắn tốc độ cao
			setWheels(scaleSpeed);
		}
		else {
			//không thì dừng
			setWheels(0);
		}
		
	}
	
    public void initDefaultCommand() {
    	
    }
}

