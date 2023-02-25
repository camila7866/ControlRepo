package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Stretch extends SubsystemBase {
  public CANSparkMax stretch = new CANSparkMax(4, MotorType.kBrushless);
  private RelativeEncoder enc_stretch = stretch.getEncoder();
  private SparkMaxPIDController pidController = stretch.getPIDController();
  public Stretch() {
    stretch.setIdleMode(IdleMode.kBrake);
    ResetEncoder();
  }

  @Override
  public void periodic() {
    SmartDashboard.putNumber("Stretch Position: ", enc_stretch.getPosition());
    SmartDashboard.putNumber("Stretch Applied Output: ", stretch.getAppliedOutput());
  }

  public void StretchPower (double vel){
    stretch.set(vel);
  }
  
  public void StretchPosition (double position){
    pidController.setReference(position, CANSparkMax.ControlType.kSmartMotion);
  }
  
  public void ResetEncoder(){
    enc_stretch.setPosition(0);
  }
  
  public void ConfigForPosition(){
    pidController.setP(0.00005);
    pidController.setI(0.000001);
    pidController.setD(0);
    pidController.setIZone(0);
    pidController.setFF(0.000156);
    pidController.setOutputRange(-1, 1);
    pidController.setSmartMotionMaxVelocity(40000, 0);
    pidController.setSmartMotionMinOutputVelocity(0, 0);
    pidController.setSmartMotionMaxAccel(20000, 0);
    pidController.setSmartMotionAllowedClosedLoopError(2, 0);
  }
  
  public boolean IsStopped (){
    boolean value =  false;
    if (Math.abs(stretch.getAppliedOutput()) == 0){
      value = true;
    }
    return (value);
  }
}
