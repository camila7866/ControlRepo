package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

import com.revrobotics.ColorMatch;
import com.revrobotics.ColorMatchResult;
import com.revrobotics.ColorSensorV3;

public class Drive extends SubsystemBase {
  public TalonFX dDer = new TalonFX(0);
  public TalonFX dIzq = new TalonFX(1);
  public TalonFX tDer = new TalonFX(2);
  public TalonFX tIzq = new TalonFX(3);
  public Gyro gyro = new ADXRS450_Gyro();
  SimpleMotorFeedforward feedforward = new SimpleMotorFeedforward(0, 0, 0);
  ColorSensorV3 color_sensor = new ColorSensorV3(I2C.Port.kOnboard);
  ColorMatch color_matcher = new ColorMatch();
  Color kBlueTarget = new Color(0.143, 0.427, 0.429);
  Color kRedTarget = new Color(0.561, 0.232, 0.114);

  public Drive() {
    dIzq.configVoltageCompSaturation(12);
    dIzq.setInverted(true);
    tIzq.setInverted(true);
    ResetEncoders();
    gyro.reset();
    color_matcher.addColorMatch(kBlueTarget);
    color_matcher.addColorMatch(kRedTarget);
  }
  
  @Override
  public void periodic() {
    SmartDashboard.putNumber("dDer position: ", dDer.getSelectedSensorPosition());
    SmartDashboard.putNumber("dIzq position: ", dIzq.getSelectedSensorPosition());
    SmartDashboard.putNumber("dDer velocity: ", dDer.getSelectedSensorVelocity());
    SmartDashboard.putNumber("dIzq velocity: ", dDer.getSelectedSensorVelocity());
    SmartDashboard.putNumber("Heading: ", gyro.getRotation2d().getDegrees());
    SmartDashboard.putBoolean("ColorSensorIsConnected", color_sensor.isConnected());
  }

  public void ResetEncoders(){
    dDer.setSelectedSensorPosition(0);
    dIzq.setSelectedSensorPosition(0);
  }

  public double PositionsInMeters(double position){
    position = (position/Constants.cpr) * Constants.kDiamWheel * Math.PI;
    return(position);
  }

  public void followMotorInFront (){
    tIzq.follow(dIzq);
    tDer.follow(dDer);
  }

  public void followMotorInCrossover (){
    tIzq.follow(dDer);
    tDer.follow(dIzq);
  }

  public void setToMasters (double v_dIzq, double v_dDer) {
    dIzq.set(ControlMode.PercentOutput, feedforward.calculate(v_dIzq));
    dDer.set(ControlMode.PercentOutput, feedforward.calculate(v_dDer));
  }

  public boolean IsBlueOrRed(){
    boolean result = false;
    Color detectedColor = color_sensor.getColor();
    ColorMatchResult match_result = color_matcher.matchClosestColor(detectedColor); 
    if (match_result.color == kBlueTarget || match_result.color == kRedTarget){
      result = true;
    }
    return (result);
  }

  @Override
  public void simulationPeriodic() {
  }
}
