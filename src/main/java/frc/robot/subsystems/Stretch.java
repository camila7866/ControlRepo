package frc.robot.subsystems;

import com.kauailabs.navx.AHRSProtocol.TuningVar;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxLimitSwitch;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.SparkMaxLimitSwitch.Type;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Stretch extends SubsystemBase {
  public CANSparkMax stretch = new CANSparkMax(4, MotorType.kBrushless);
  private RelativeEncoder enc_stretch = stretch.getEncoder();
  private SparkMaxPIDController pidController = stretch.getPIDController();
  public SparkMaxLimitSwitch limit_for = stretch.getForwardLimitSwitch(Type.kNormallyClosed);
  public Stretch() {
    stretch.setIdleMode(IdleMode.kBrake);
    limit_for.enableLimitSwitch(true);
    stretch.enableSoftLimit(CANSparkMax.SoftLimitDirection.kForward, false);
    //stretch.setSoftLimit(CANSparkMax.SoftLimitDirection.kForward, 0);
    stretch.enableSoftLimit(CANSparkMax.SoftLimitDirection.kReverse, true);
    stretch.setSoftLimit(CANSparkMax.SoftLimitDirection.kReverse, -500);
    ResetEncoder();
  }

  @Override
  public void periodic() {
    SmartDashboard.putNumber("Stretch Position: ", enc_stretch.getPosition());
    SmartDashboard.putBoolean("LimitStretch", limit_for.isPressed());
    if (limit_for.isPressed()){
      ResetEncoder();
    }
  }

  public void StretchPower (double vel){
    stretch.set(vel);
  }
  
  public void StretchPosition (double position){
    pidController.setReference(position, CANSparkMax.ControlType.kPosition);
  }
  
  public void ResetEncoder(){
    enc_stretch.setPosition(0);
  }
  
  public void ConfigForPosition(){
    pidController.setP(0.1);
    pidController.setI(0.0001);
    pidController.setD(1);
    pidController.setIZone(0);
    pidController.setFF(0);
    pidController.setOutputRange(-1, 1);
  }
  
  public boolean IsStopped (double pos_goal){
    boolean value =  false;
    if (Math.abs(pos_goal - enc_stretch.getPosition()) <= 3){
      value = true;
    }
    return (value);
  }
}
