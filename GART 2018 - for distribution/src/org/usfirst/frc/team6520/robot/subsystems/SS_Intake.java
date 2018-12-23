package org.usfirst.frc.team6520.robot.subsystems;

import org.usfirst.frc.team6520.robot.Robot;
import org.usfirst.frc.team6520.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class SS_Intake extends Subsystem {

	double speed = 1; //tốc độ quay
	public int open = 0; //giá trị đóng/mở - có thể toggle được
	public boolean raised = false; //giá trị nâng/hạ máng intake - toggle được
	double error = 0.2; //giá trị sai số vì joystick rất unreliable

	public void intake() {
		if (Robot.oi.panel.getRawAxis(1) > error){
			
			//thay vì đặt tốc độ theo giá trị joystick, thì đặt tốc độ theo giá trị cho
			//trước khi joystick được đẩy. về lý thuyết thì chỉ cần > 0, nhưng joystick có sai số
			//nên cho > giá trị nhất định.
			
			RobotMap.intake_left.set(speed);
			RobotMap.intake_right.set(-speed);
			RobotMap.ss_Launcher.loadCube(); //cho launcher hút hộp vào đồng thời với intake
		}
		else if (Robot.oi.panel.getRawAxis(1) < -error){
			
			//đẩy ngược lại nếu kéo joystick xuống
			RobotMap.intake_left.set(-speed);
			RobotMap.intake_right.set(speed);
		}
		else {
			//nếu nằm trong khoảng sai số thì dừng
			RobotMap.intake_left.set(0);
			RobotMap.intake_right.set(0);
		}
	}
	
	public void initDefaultCommand() {
	}
}
