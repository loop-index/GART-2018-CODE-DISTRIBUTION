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

	public final double speed = 0.6; //hệ số tốc độ default
	public double leftSpeed = speed; //tốc độ trái
	public double rightSpeed = -speed; //tốc độ phải (inverted)
	double boostSpeed = 1;  //hệ số tốc độ khi kích hoạt chế độ boost


	public void leftSide() { //nhân giá trị joystick trái với hệ số (joystick của controller lái)
		RobotMap.left.set(Robot.oi.gamepad.getRawAxis(5) * leftSpeed);
	}

	public void rightSide() { //nhân giá trị joystick phải với hệ số
		RobotMap.right.set(Robot.oi.gamepad.getRawAxis(1) * rightSpeed);
	}

	public void boost() { //chế độ boost
		if (Robot.oi.gamepad.getRawAxis(2) == 1) { //khi nào nút phía sau được giữ 
												   //thì đổi hệ số thành boostSpeed
			leftSpeed = boostSpeed;
			rightSpeed = -boostSpeed;
		} else { //khi thả ra thì đổi lại hệ số thường
			leftSpeed = speed;
			rightSpeed = -speed;
		}
	}

	public void driveTwoJoysticks() { //method kết hợp 3 method trên
		leftSide();
		rightSide();
		boost();
	}
	
	double accErr = 0.2;  //sai số chấp nhận được
	double turnVal = 0.3;

	public void driveGyro() { //method đi thẳng bằng cảm biến góc (đề phỏng vấn gen 3)
		
		double currentAngle = RobotMap.gyro.getAngle(); //góc đặt làm mốc
		double turnVal = currentAngle; 
		
		
		//warning: cái này confusing shit vl và cũng chẳng nhớ có hoạt động đúng không so không chắc 
		//1 số thứ đâu
		
		if (currentAngle > accErr){ //nếu góc lớn hơn accErr (lệch sang phải) 
			RobotMap.left.set(-speed * (1 + turnVal)); //cho bánh trái quay nhanh hơn để chỉnh lại
			RobotMap.right.set(speed);
			
			while (RobotMap.gyro.getAngle() > 0); //lặp lại lệnh trước cho đến 
												  //khi góc nhỏ hơn 0 (chỉnh hoàn toàn)
			
			RobotMap.left.set(-speed);	//đi bình thường
			RobotMap.right.set(speed);
			
		}
		else if (currentAngle < -accErr){ //nếu góc nhỏ hơn -accErr (lệch sang trái) 
			
			RobotMap.left.set(-speed);
			RobotMap.right.set(speed * (1 + turnVal)); //cho bánh phải quay nhanh hơn để chỉnh lại
			
			while (RobotMap.gyro.getAngle() < 0); //lặp lại lệnh trước cho đến 
			  									  //khi góc lớn hơn 0 (chỉnh hoàn toàn)
			
			RobotMap.left.set(-speed);  //đi bình thường
			RobotMap.right.set(speed);
			
		}
		else {
			RobotMap.left.set(-speed);  //đi bình thường
			RobotMap.right.set(speed);
		}
	}

	public void turn (double value) { //quay thêm 1 góc cho trước
									 //không phải quay đến 1 góc
		
    	double startAngle = RobotMap.gyro.getAngle(); //góc xuất phát
    	double angle = startAngle; //góc đếm
    	
	    if (value >= 0) {	//nếu góc yêu cầu lớn hơn 0 thì quay sang phải
    		while (Math.abs(angle - startAngle) <= value) { //trong khi góc đếm - góc ban đầu < góc yêu cầu
    			RobotMap.left.set(speed);
    			RobotMap.right.set(speed);  //quay
	    		angle = RobotMap.gyro.getAngle();  //cập nhật góc để tiếp tục so sánh
	    		SmartDashboard.putNumber("angle", angle);  //đưa lên SD để dễ kiểm soát & tìm lỗi
    		}
    		RobotMap.left.set(-speed);  //quay ngược lại 1 chút để compensate quán tính
    									//which failed miserably nên năm nay học PID đi các bạn
    		RobotMap.right.set(-speed);
    		
    		
	    } else {  //nếu góc yêu cầu nhỏ hơn 0 thì quay sang trái
	    	while (Math.abs(angle - startAngle) <= -value) { 
	    		RobotMap.left.set(-speed);
	    		RobotMap.right.set(-speed);
	    		angle = RobotMap.gyro.getAngle();
	    		SmartDashboard.putNumber("angle", angle);
	    	}
	    	RobotMap.left.set(speed);
	    	RobotMap.right.set(speed);
	    } 	
    	Timer.delay(0.04);
    	RobotMap.ss_Drivebase.stop(); //xong thì dừng
	}

	public void stop() {
		RobotMap.left.set(0);  //dừng motor
		RobotMap.right.set(0);
	}

	protected void initDefaultCommand() {

	}
}
