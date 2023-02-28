package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Elevator extends SubsystemBase {
  public final CANSparkMax elevator = new CANSparkMax(5, MotorType.kBrushless);
  private RelativeEncoder enc_elevator = elevator.getEncoder(); 
  private SparkMaxPIDController pidController = elevator.getPIDController();
  public Elevator() {
    elevator.setIdleMode(IdleMode.kBrake);
    elevator.enableSoftLimit(CANSparkMax.SoftLimitDirection.kForward, true);
    elevator.enableSoftLimit(CANSparkMax.SoftLimitDirection.kReverse, true); 
    elevator.setSoftLimit(CANSparkMax.SoftLimitDirection.kReverse, 0);
    elevator.setSoftLimit(CANSparkMax.SoftLimitDirection.kForward, 125);
    enc_elevator.setPosition(0);
  }

  @Override
  public void periodic() {
    SmartDashboard.putNumber("Elevator Position: ", enc_elevator.getPosition());
    SmartDashboard.putNumber("Elevator Vel: ", enc_elevator.getVelocity());
  }

  public void ElevatorPower (double vel){
    elevator.set(vel);
  }
  
  public void ElevatorPosition (double position){
    pidController.setReference(position, CANSparkMax.ControlType.kSmartMotion);
  }
  
  public void ResetEncoder(){
    enc_elevator.setPosition(0);
  }
  
  public void ConfigForPosition(){
    pidController.setP(0.00005);
    pidController.setI(0.000001);
    pidController.setD(0);
    pidController.setIZone(0);
    pidController.setFF(0.000156);
    pidController.setOutputRange(-1, 1);
    pidController.setSmartMotionMaxVelocity(2000, 0);
    pidController.setSmartMotionMinOutputVelocity(0, 0);
    pidController.setSmartMotionMaxAccel(1500, 0);
    pidController.setSmartMotionAllowedClosedLoopError(2, 0);
  }
  
  public boolean IsStopped (double pos_goal){
    boolean value =  false;
    if (Math.abs(pos_goal - enc_elevator.getPosition()) <= 3){
      value = true;
    }
    return (value);
  }
}