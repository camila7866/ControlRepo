package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.TalonFXFeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Drive extends SubsystemBase {
  public TalonFX dDer = new TalonFX(0);
  public TalonFX dIzq = new TalonFX(1);
  public TalonFX tDer = new TalonFX(2);
  public TalonFX tIzq = new TalonFX(3);
  public static AHRS navx = new AHRS();
  public static ADXRS450_Gyro gyro = new ADXRS450_Gyro();

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
    SmartDashboard.putNumber("dDer velocity: ", dDer.getSelectedSensorVelocity());
    SmartDashboard.putNumber("dIzq velocity: ", dIzq.getSelectedSensorVelocity());
    SmartDashboard.putNumber("Yaw: ", getBotHeadingDegrees());
    SmartDashboard.putNumber("Pitch: ", navx.getPitch());
    //Para adelante disminuye, si disminuye la velocidad tambien
  }

  public void configMastersForPosition(){
    dIzq.configFactoryDefault();
    dIzq.configSelectedFeedbackSensor(TalonFXFeedbackDevice.IntegratedSensor, 0, 0);
    dIzq.configNeutralDeadband(0.001);
    dIzq.setSensorPhase(false);
    dIzq.setStatusFramePeriod(StatusFrameEnhanced.Status_13_Base_PIDF0, 10, 0);
    dIzq.setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, 10, 0);
    dIzq.configNominalOutputForward(0);
		dIzq.configNominalOutputReverse(0);
		dIzq.configPeakOutputForward(1);
		dIzq.configPeakOutputReverse(-1);
    dIzq.selectProfileSlot(0, 0);
    dIzq.config_kF(0, 0.2);
		dIzq.config_kP(0, 0.2);
		dIzq.config_kI(0, 0);
	  dIzq.config_kD(0, 0);
    dIzq.configMotionCruiseVelocity(8000);
    dIzq.configMotionAcceleration(6000);

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
    dDer.configMotionCruiseVelocity(10000);
    dDer.configMotionAcceleration(6000);
  }

  public void RunToPosition (double rev_der, double rev_izq ){
    double PosDer = Constants.cpr * rev_der;
    double PosIzq = Constants.cpr * rev_izq;
		dIzq.set(TalonFXControlMode.MotionMagic, PosIzq);
    dDer.set(TalonFXControlMode.MotionMagic, PosDer);
  }

  public void ResetEncoders(){
    dDer.setSelectedSensorPosition(0);
    dIzq.setSelectedSensorPosition(0);
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
    if (Math.abs(dIzq.getMotorOutputPercent()) == 0 && Math.abs(dDer.getMotorOutputPercent()) == 0){
      value = true;
    }
    return (value);
  }

  @Override
  public void simulationPeriodic() {
  }
}
