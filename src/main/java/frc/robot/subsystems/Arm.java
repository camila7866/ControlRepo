package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxLimitSwitch;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMax.SoftLimitDirection;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.SparkMaxLimitSwitch.Type;

import edu.wpi.first.math.controller.ArmFeedforward;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Arm extends SubsystemBase {
  public final CANSparkMax arm = new CANSparkMax(6, MotorType.kBrushless);
  private RelativeEncoder enc_arm = arm.getEncoder(); 
  private SparkMaxPIDController pidController = arm.getPIDController();
  public SparkMaxLimitSwitch limit_for = arm.getForwardLimitSwitch(Type.kNormallyClosed);
  private ArmFeedforward arm_feedforward = new ArmFeedforward(0, 0.06, 1.8, 0);
  public Arm() {
    arm.setIdleMode(IdleMode.kBrake);
    limit_for.enableLimitSwitch(true);
    //arm.enableSoftLimit(CANSparkMax.SoftLimitDirection.kForward, false);
    //arm.setSoftLimit(SoftLimitDirection.kForward, 0);
    arm.enableSoftLimit(CANSparkMax.SoftLimitDirection.kReverse, true);
    arm.setSoftLimit(SoftLimitDirection.kReverse, -180);
    ResetEncoder();
  }

  @Override
  public void periodic() {
    SmartDashboard.putNumber("Arm Position: ", enc_arm.getPosition());
    SmartDashboard.putBoolean("Limit Arm", limit_for.isPressed());
    if (limit_for.isPressed()){
      ResetEncoder();
    }
  }

  public void ArmPower (double vel){
    arm.set(vel);
  }

  public void ArmPosFeed (){}
  
  public void ArmPosition (double position){
    pidController.setReference(position, CANSparkMax.ControlType.kSmartMotion);
  }

  public double ArmPosRadians (){
    return -Units.degreesToRadians((enc_arm.getPosition()/100)*360);
  }
  
  public void ResetEncoder(){
    enc_arm.setPosition(0);
  }
  
  public void ConfigForPosition(double max_accel, double max_vel){
    pidController.setP(0.0005);
    pidController.setI(0.000001);
    pidController.setD(0);
    pidController.setIZone(0);
    pidController.setFF(0.000156);
    pidController.setOutputRange(-1, 1);
    pidController.setSmartMotionMaxVelocity(max_vel, 0);
    pidController.setSmartMotionMinOutputVelocity(0, 0);
    pidController.setSmartMotionMaxAccel(max_accel, 0);
    pidController.setSmartMotionAllowedClosedLoopError(2, 0);
  }
  
  public boolean IsStopped (double pos_goal){
    boolean value =  false;
    if (Math.abs(pos_goal - enc_arm.getPosition()) <= 2){
      value = true;
    }
    return (value);
  }
}
