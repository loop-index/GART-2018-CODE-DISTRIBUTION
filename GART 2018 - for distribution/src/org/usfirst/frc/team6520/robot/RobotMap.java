/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team6520.robot;

import org.usfirst.frc.team6520.robot.KalmanFilter;
import org.usfirst.frc.team6520.robot.subsystems.SS_Drivebase;
import org.usfirst.frc.team6520.robot.subsystems.SS_Intake;
import org.usfirst.frc.team6520.robot.subsystems.SS_Launcher;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.SPI.Port;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class RobotMap {
	
	//instantiate các subsystem (bắt buộc)
	public static SS_Drivebase ss_Drivebase = new SS_Drivebase();
	public static SS_Launcher ss_Launcher = new SS_Launcher();
	public static SS_Intake ss_Intake = new SS_Intake();
	
	//khai báo cảm biến 
	//cảm biến góc - gắn vào cổng CS0 ở góc phải roboRIO
	public static ADXRS450_Gyro gyro = new ADXRS450_Gyro(Port.kOnboardCS0);
	
	//cảm biến siêu âm - cổng DIO
	//hindsight: cảm biến siêu âm unreliable vl đừng dùng under any circumstances
	public static Ultrasonic ultra_behind_left = new Ultrasonic(0, 1);
	public static Ultrasonic ultra_left = new Ultrasonic(2, 3);
	public static Ultrasonic ultra_right = new Ultrasonic(6, 7);
	public static Ultrasonic ultra_behind_right = new Ultrasonic(4, 5);
	
	//van khí nén điện tử - cổng trên Pneumatic Control Module
	public static DoubleSolenoid solenoid_fire = new DoubleSolenoid(0, 1);
	public static DoubleSolenoid solenoid_launcher = new DoubleSolenoid(2, 3);
	public static DoubleSolenoid solenoid_intake = new DoubleSolenoid(4, 5);
	public static Solenoid solenoid_slide = new Solenoid(6);
	
	//máy nén khí - dây CAN
	public static Compressor comp = new Compressor(0);
	
	//instantiate bộ lọc kalman
	public static KalmanFilter kf = new KalmanFilter(0.1, 0.1, 0.01);
	
	//khai báo motor controller - cổng PWM
	//hindsight: should have used differentialdrive
	//hindsight: những motor mà chỉ được dùng bởi 1 subsystem thì nên khai báo thẳng trong subsystem đấy
	//cho đỡ dài dòng
	public static Spark left = new Spark(1);
	public static Spark right = new Spark(0);
	
	public static VictorSP shoot1 = new VictorSP(2);
	public static VictorSP shoot2 = new VictorSP(3);
	public static VictorSP shoot3 = new VictorSP(4); 
	public static VictorSP shoot4 = new VictorSP(5);
	
	public static VictorSP intake_left = new VictorSP(6);
	public static VictorSP intake_right = new VictorSP(7);
	
	public static double curAngle = 0; //giá trị góc tượng trưng của robot. không được cập nhật liên tục.
	
	
	public static void update(){ //cập nhật các giá trị lên SmartDashboard
		
		SmartDashboard.putNumber("gyro", gyro.getAngle());
		SmartDashboard.putNumber("motor const", ss_Drivebase.speed);

		SmartDashboard.putNumber("filtered distance left", kf.updateEstimate(ultra_left.getRangeMM()));
		SmartDashboard.putNumber("filtered distance right", kf.updateEstimate(ultra_right.getRangeMM()));
		SmartDashboard.putNumber("filtered distance left back", kf.updateEstimate(ultra_behind_left.getRangeMM()));
		SmartDashboard.putNumber("filtered distance right back", kf.updateEstimate(ultra_behind_right.getRangeMM()));
		SmartDashboard.putNumber("joystick error", Robot.oi.panel.getRawAxis(1));
	
	}
	public static void teleOpUpdate(){ //cập nhật các giá trị lên SmartDashboard
		
		SmartDashboard.putNumber("speed", RobotMap.ss_Drivebase.leftSpeed);
		SmartDashboard.putNumber("launcher raised?", RobotMap.ss_Launcher.raised);
		SmartDashboard.putNumber("intake opened?", RobotMap.ss_Intake.open);
		SmartDashboard.putNumber("left side", left.get());
		SmartDashboard.putNumber("right side", right.get());
		SmartDashboard.putNumber("shooter speed", shoot1.get());
		SmartDashboard.putBoolean("slide raised?", ss_Intake.raised);
	}
}
