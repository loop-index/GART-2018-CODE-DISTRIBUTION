package org.usfirst.frc.team6520.robot.commands;

import org.usfirst.frc.team6520.robot.Robot;
import org.usfirst.frc.team6520.robot.RobotMap;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class C_AutoTillPass_Left extends Command { //đi ngang qua vật cản, khi nào không thấy vật cản 
												   //thì dừng (đề phỏng vấn gen 3)
												   //bên trái version
	
	boolean encountered = false; //đã gặp vật cản chưa?
	boolean passed = false;		//đã hết vật cản chưa?
	
	double filtered_dist = RobotMap.kf.updateEstimate(RobotMap.ultra_left.getRangeMM());

    public C_AutoTillPass_Left() {
    }

    protected void initialize() {
    	
    }

    protected void execute() {
    	while (passed == false){ //nếu chưa đi qua
    		RobotMap.ss_Drivebase.driveGyro();
    		
    		//nếu khoảng cách tới vật cản bên trái nhỏ hơn giá trị cho trước
    		//và chưa gặp vật
    		if (filtered_dist < 1500 && filtered_dist >= 100 && encountered == false){
    			encountered = true; //đã gặp vật
    			
    		}
    		if (encountered == true){ //nếu đã gặp vật
    			//và khoảng cách trở thành vô hạn
    			if (filtered_dist > 3500 || filtered_dist < 100){ //cảm biến vớ vẩn nên 
    															  //lấy >3500 hoặc <100 là vô hạn
        			passed = true; //đã đi qua vật
    			}
    		}
    		filtered_dist = RobotMap.kf.updateEstimate(RobotMap.ultra_left.getRangeMM());
    		
    		SmartDashboard.putBoolean("encountered?", encountered); //đưa thông tin lên SD để dễ kiểm soát
    																//và kiểm tra lỗi
    		SmartDashboard.putBoolean("passed?", passed);
    		Timer.delay(0.01);
    	}
    	
    	RobotMap.ss_Drivebase.stop();
    	
    }

    protected boolean isFinished() {
        return true;
    }

    protected void end() {
    	RobotMap.ss_Drivebase.stop();
    	passed = false;
    	encountered = false;
    }

    protected void interrupted() {
    	RobotMap.ss_Drivebase.stop();
    	passed = false;
    	encountered = false;
    }
}
