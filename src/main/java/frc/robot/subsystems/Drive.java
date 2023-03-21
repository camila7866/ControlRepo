package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.TalonFXFeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.TalonFXConfiguration;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Drive extends SubsystemBase {
  public TalonFX dDer = new TalonFX(0);
  public TalonFX dIzq = new TalonFX(1);
  public TalonFX tDer = new TalonFX(2);
  public TalonFX tIzq = new TalonFX(3);
  public static AHRS navx = new AHRS();
  public static ADXRS450_Gyro gyro = new ADXRS450_Gyro();
  public double zero_pitch = 0;

  public Drive() {
    dDer.setNeutralMode(NeutralMode.Brake);
    dIzq.setNeutralMode(NeutralMode.Brake);
    tDer.setNeutralMode(NeutralMode.Brake);
    tIzq.setNeutralMode(NeutralMode.Brake);
    dDer.setInverted(true);
    tDer.setInverted(true);
    ResetEncoders();
    navx.calibrate();
    navx.reset();
  }
  
  @Override
  public void periodic() {
    SmartDashboard.putNumber("dDer position: ", dDer.getSelectedSensorPosition());
    SmartDashboard.putNumber("dIzq position: ", dIzq.getSelectedSensorPosition());
    SmartDashboard.putNumber("Dder Volt", dDer.getMotorOutputPercent());
    SmartDashboard.putNumber("DIzq Volt", dIzq.getMotorOutputPercent());
    SmartDashboard.putNumber("Yaw: ", getBotHeadingDegrees());
    SmartDashboard.putNumber("Pitch: ", navx.getPitch());
    SmartDashboard.putNumber("ZeroPitch", zero_pitch);
  }

  public void configMastersForPosition(double max_vel, double max_accel){
    tIzq.configSelectedFeedbackSensor(TalonFXFeedbackDevice.IntegratedSensor, 0, 0);
    tIzq.configNeutralDeadband(0.001);
    tIzq.setSensorPhase(false);
    tIzq.setStatusFramePeriod(StatusFrameEnhanced.Status_13_Base_PIDF0, 10, 0);
    tIzq.setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, 10, 0);
    tIzq.configNominalOutputForward(0);
		tIzq.configNominalOutputReverse(0);
		tIzq.configPeakOutputForward(1);
		tIzq.configPeakOutputReverse(-1);
    tIzq.selectProfileSlot(0, 0);
    tIzq.config_kF(0, 0.2);
		tIzq.config_kP(0, 0.2);
		tIzq.config_kI(0, 0);
	  tIzq.config_kD(0, 0);
    tIzq.configMotionCruiseVelocity(max_vel);
    tIzq.configMotionAcceleration(max_accel);
    //dIzq.configAllowableClosedloopError(0, 100);
    /*
    dDer.configFactoryDefault();
    dDer.configSelectedFeedbackSensor(TalonFXFeedbackDevice.IntegratedSensor, 0, 0);
    dDer.configNeutralDeadband(0.001);
    dDer.setSensorPhase(false);
    dDer.setStatusFramePeriod(StatusFrameEnhanced.Status_13_Base_PIDF0, 10, 0);
    dDer.setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, 10, 0);
    dDer.configNominalOutputForward(0);
		dDer.configNominalOutputReverse(0);
		dDer.configPeakOutputForward(1);
		dDer.configPeakOutputReverse(-1); 
    dDer.selectProfileSlot(0, 0);
    dDer.config_kF(0, 0.2);
		dDer.config_kP(0, 0.2);
		dDer.config_kI(0, 0);
	  dDer.config_kD(0, 0);
    dDer.configMotionCruiseVelocity(max_vel);
    dDer.configMotionAcceleration(max_accel);
    */
    //dDer.configAllowableClosedloopError(0, 100);
  }

  public void RunToPosition (double PosDer, double PosIzq){
		tIzq.set(TalonFXControlMode.MotionMagic, PosIzq);
    //dDer.set(TalonFXControlMode.MotionMagic, PosDer);
  }

  public void ResetEncoders(){
    dDer.setSelectedSensorPosition(0);
    dIzq.setSelectedSensorPosition(0);
  }

  public void followOnlyOneMaster (){
    dDer.follow(tIzq);
    tDer.follow(tIzq);
    dIzq.follow(tIzq);
  }

  public void followMotorInFront () {
    tIzq.follow(dIzq);
    tDer.follow(dDer);
  }

  public void followMotorInCrossover (){
    tIzq.follow(dDer);
    tDer.follow(dIzq);
  }

  public void setToMasters (double v_dIzq, double v_dDer) {
    dIzq.set(ControlMode.PercentOutput, v_dIzq);
    dDer.set(ControlMode.PercentOutput, v_dDer);
  }

  public double getBotHeadingFOC () {
    return (Units.degreesToRadians(-gyro.getRotation2d().getDegrees() - (Math.round(-gyro.getRotation2d().getDegrees() / 360) * 360)));
  }

  public double getBotHeadingDegrees(){
    double angle = -gyro.getRotation2d().getDegrees() - (Math.round(-gyro.getRotation2d().getDegrees() / 360) * 360);
    return (angle);
  }

  public double getPitch(){
    return (navx.getPitch());
  }

  public boolean MastersInZero (){
    boolean value = false;

    if (Math.abs(dDer.getMotorOutputVoltage()) == 0 || Math.abs(dIzq.getMotorOutputVoltage()) == 0){
      value = true;
    }
    return (value);
  }

  public void restartNavx (){
    navx.calibrate();
  }

  public boolean Calibrating (){
    return (navx.isCalibrating());
  }

  public void setZeroPitch (double _zero){
    zero_pitch = _zero;
  }

  public double getZeroPitch (){
    return (zero_pitch);
  }

  @Override
  public void simulationPeriodic() {
  }
}
