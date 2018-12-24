package org.usfirst.frc.team6520.robot.commands;

import org.usfirst.frc.team6520.robot.RobotMap;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class C_AutoByDistance extends Command { //đi thẳng theo khoảng cách đo được từ cảm biến siêu âm

	//command này đi tới khoảng cách yêu cầu chứ không phải đi thêm một đoạn bằng khoảng cách yêu cầu
	
	double distance; //khoảng cách cần đến
	double error = 0.2; //sai số chấp nhận được, theo mét
	
	//giá trị đo của cảm biến, được lọc
	double filteredDist = RobotMap.kf.updateEstimate(RobotMap.ultra_behind_right.getRangeMM());
	

	public C_AutoByDistance(double value) { //pass lại giá trị cho biến khoảng cách
		this.distance = value;
	}

	protected void initialize() {
	}

	protected void execute() {
		double calculatedDistance = distance - error; //khoảng cách cần đi trừ đi sai số để
													  //bù cho quán tính
		while (filteredDist <= calculatedDistance * 1000) { //so sánh giá trị đã lọc
			RobotMap.ss_Drivebase.driveGyro();
			RobotMap.update();
			Timer.delay(0.01);
			
			//cập nhật giá trị đo cảm biến và lọc
			filteredDist = RobotMap.kf.updateEstimate(RobotMap.ultra_behind_right.getRangeMM());
		}

		RobotMap.ss_Drivebase.stop(); // khi điều kiện thỏa mãn, dừng động cơ
	}

	protected boolean isFinished() {
		return true; //execute() chạy đúng 1 lần vì sau khi chạy 1 lần thì kiểm tra xem hàm này có true không
	}

	protected void end() {
		RobotMap.ss_Drivebase.stop();
	}

	protected void interrupted() {
		RobotMap.ss_Drivebase.stop();
	}
}
