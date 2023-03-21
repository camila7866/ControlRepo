package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxLimitSwitch;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.SparkMaxLimitSwitch.Type;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Elevator extends SubsystemBase {
  public final CANSparkMax elevator = new CANSparkMax(5, MotorType.kBrushless);
  private RelativeEncoder enc_elevator = elevator.getEncoder(); 
  private SparkMaxPIDController pidController = elevator.getPIDController();
  public SparkMaxLimitSwitch limit_rev = elevator.getReverseLimitSwitch(Type.kNormallyClosed);
  public Elevator() {
    elevator.setIdleMode(IdleMode.kBrake);
    limit_rev.enableLimitSwitch(true);
    elevator.enableSoftLimit(CANSparkMax.SoftLimitDirection.kForward, true);
    elevator.setSoftLimit(CANSparkMax.SoftLimitDirection.kForward, 103);
  }

  @Override
  public void periodic() {
    SmartDashboard.putNumber("Elevator Position ", enc_elevator.getPosition());
    SmartDashboard.putNumber("Elevator Velocity ", enc_elevator.getVelocity());
    SmartDashboard.putBoolean("Limit Elevator", limit_rev.isPressed());
    if (limit_rev.isPressed()){
      ResetEncoder();
    }
  }

  public void ElevatorPower (double vel){
    elevator.set(vel);
  }
  
  public void ElevatorPosition (double position){
    pidController.setReference(position, CANSparkMax.ControlType.kPosition);
  }
  
  public void ResetEncoder(){
    enc_elevator.setPosition(0);
  }
  
  public void ConfigForPosition(){
    pidController.setP(0.02);
    pidController.setI(0.0001);
    pidController.setD(0.2);
    pidController.setIZone(0);
    pidController.setFF(0);
    pidController.setOutputRange(-1, 1);
  }
  
  public boolean IsStopped (double pos_goal){
    boolean value =  false;
    if (Math.abs(pos_goal - enc_elevator.getPosition()) <= 1){
      value = true;
    }
    return (value);
  }
}