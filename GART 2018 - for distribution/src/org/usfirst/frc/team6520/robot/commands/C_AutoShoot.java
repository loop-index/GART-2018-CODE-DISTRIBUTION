package org.usfirst.frc.team6520.robot.commands;

import org.usfirst.frc.team6520.robot.Robot;
import org.usfirst.frc.team6520.robot.RobotMap;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class C_AutoShoot extends Command {

	double startTime = Timer.getFPGATimestamp(); //thời gian bắt đầu
	double time;	//thời gian hiện tại
	boolean shot = false; //đã bắn chưa?
	
    public C_AutoShoot() {
    }

    protected void initialize() {
    	time = startTime;
    	RobotMap.solenoid_launcher.set(Value.kForward); //launcher nâng lên
    	Timer.delay(3);
    }

    protected void execute() {
    	
    	//execute() được chạy liên tục cho đến khi boolean shot chuyển sang true, khi đó isFinished() 
    	//thành true và dừng execute()
    	
    	//chạy bánh bắn liên tục để tạo gia tốc
	    RobotMap.ss_Launcher.setWheels(RobotMap.ss_Launcher.switchSpeed);
	    
	    //cập nhật giá trị thời gian
    	time = Timer.getFPGATimestamp();
	    if (time - startTime > 7) {	//khi nào lấy đà đủ 7s

	    	RobotMap.solenoid_fire.set(Value.kReverse); //kéo piston ngược lại để đẩy hộp vào bánh bắn
	    	Timer.delay(0.2);
	    	shot = true; //bắn rồi
	    }
    }

    protected boolean isFinished() {
        return shot;
    }

    protected void end() { //khi kết thúc Command thì dừng bánh bắn, piston quay về vị trí cũ, launcher hạ xuống
    	RobotMap.ss_Launcher.setWheels(0);
    	RobotMap.solenoid_launcher.set(Value.kReverse);
    	RobotMap.solenoid_fire.set(Value.kForward);
    	
    }

    protected void interrupted() {
    }
}

