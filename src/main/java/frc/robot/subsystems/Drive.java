package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.math.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.math.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Drive extends SubsystemBase {
  public TalonFX dDer = new TalonFX(0);
  public TalonFX tDer = new TalonFX(2);
  public TalonFX dIzq = new TalonFX(1);
  public TalonFX tIzq = new TalonFX(3);
  public Gyro gyro = new ADXRS450_Gyro();
  //Objects for RamsetteCommand
  //public MotorControllerGroup right_motors = new MotorControllerGroup(dDer, tDer);
  //public MotorControllerGroup left_motors = new MotorControllerGroup(dIzq, tIzq);
  //public DifferentialDrive differentialDrive = new DifferentialDrive(left_motors, right_motors);
  public DifferentialDriveKinematics differentialDriveKinematics = new DifferentialDriveKinematics(Constants.kTrackWidthMeters);
  public DifferentialDriveOdometry differentialDriveOdometry;

  public Drive() {
    dIzq.setInverted(true);
    tIzq.setInverted(true);
    ResetEncoders();
    gyro.reset();
    differentialDriveOdometry = new DifferentialDriveOdometry(gyro.getRotation2d(), 0, 0);
  }
  
  @Override
  public void periodic() {
    double[] array = new double[4];
    array = PositionsInMeters();
    differentialDriveOdometry.update(gyro.getRotation2d(), array[0], array[1]);
    SmartDashboard.putNumber("FrontRightPos: ", dDer.getSelectedSensorPosition());
    SmartDashboard.putNumber("FrontLeftPos: ", dIzq.getSelectedSensorPosition());
    SmartDashboard.putNumber("RearRightPos: ", tDer.getSelectedSensorPosition());
    SmartDashboard.putNumber("RearLeftPos: ", tIzq.getSelectedSensorPosition());
    SmartDashboard.putNumber("Gyro Angle: ", gyro.getAngle());
    SmartDashboard.putNumber("Gyro Degrees: ", gyro.getRotation2d().getDegrees());
  }

  public Pose2d getPose() {
    return differentialDriveOdometry.getPoseMeters();
  }

  public void ResetEncoders(){
    dDer.setSelectedSensorPosition(0);
    dIzq.setSelectedSensorPosition(0);
    tDer.setSelectedSensorPosition(0);
    tIzq.setSelectedSensorPosition(0);
  }

  public double[] PositionsInMeters(){
    double[] array = new double[4];
    array[0] = (dIzq.getSelectedSensorPosition()/Constants.cpr) * Constants.kPerWheel;
    array[1] = (dDer.getSelectedSensorPosition()/Constants.cpr) * Constants.kPerWheel;
    array[2] = (tIzq.getSelectedSensorPosition()/Constants.cpr) * Constants.kPerWheel;
    array[3] = (tDer.getSelectedSensorPosition()/Constants.cpr) * Constants.kPerWheel;
    return(array);
  }

  public DifferentialDriveWheelSpeeds getWheelSpeeds(){
    double[] array = new double[2];
    array[0] = ((dIzq.getSelectedSensorVelocity() * 10)/Constants.cpr) * Constants.kPerWheel;
    array[1] = ((dDer.getSelectedSensorVelocity() * 10)/Constants.cpr) * Constants.kPerWheel;
    return (new DifferentialDriveWheelSpeeds(array[0], array[1]));
  }

  public void resetOdometry(Pose2d pose) {
    ResetEncoders();
    double[] array = new double[4];
    array = PositionsInMeters();
    differentialDriveOdometry.resetPosition(gyro.getRotation2d(), array[0], array[1], pose);
  }

  public void tankDriveVolts(double leftVolts, double rightVolts) {
    dDer.set(TalonFXControlMode.PercentOutput, rightVolts);
    dIzq.set(TalonFXControlMode.PercentOutput, rightVolts);
    tDer.set(TalonFXControlMode.PercentOutput, rightVolts);
    tIzq.set(TalonFXControlMode.PercentOutput, rightVolts);
    //left_motors.setVoltage(leftVolts);
    //right_motors.setVoltage(rightVolts);
    //differentialDrive.feed();
  }

  public void givePower (double v_dDer, double v_dIzq, double v_tDer, double v_tIzq) {
    dDer.set(ControlMode.PercentOutput, v_dDer);
    dIzq.set(ControlMode.PercentOutput, v_dIzq);
    tDer.set(ControlMode.PercentOutput, v_tDer);
    tIzq.set(ControlMode.PercentOutput, v_tIzq);
  }

  @Override
  public void simulationPeriodic() {
  }
}
