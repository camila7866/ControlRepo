// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.MecanumDriveKinematics;
import edu.wpi.first.math.kinematics.MecanumDriveOdometry;
import edu.wpi.first.math.kinematics.MecanumDriveWheelPositions;
import edu.wpi.first.math.kinematics.MecanumDriveWheelSpeeds;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.drive.MecanumDrive;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ExampleSubsystem extends SubsystemBase {
  WPI_TalonFX dDer = new WPI_TalonFX(0);
  WPI_TalonFX tDer = new WPI_TalonFX(0);
  WPI_TalonFX dIzq = new WPI_TalonFX(0);
  WPI_TalonFX tIzq = new WPI_TalonFX(0);
  Gyro gyro = new ADXRS450_Gyro();
  MecanumDrive drive = new MecanumDrive(dIzq, tIzq, dDer, tDer);
  MecanumDriveKinematics drive_kinematics = new 
  MecanumDriveKinematics(
    new Translation2d(0, 0), //front left
    new Translation2d(0, 0), //front right
    new Translation2d(0, 0), //rear left
    new Translation2d(0, 0) //rear right
  );
  MecanumDriveOdometry drive_odometry;

  public ExampleSubsystem() {
    dDer.setInverted(true);
    tDer.setInverted(true);
    ResetEncoders();
    double[] pos_wheels = new double[4];
    pos_wheels = PositionsInMeters(); | 
    drive_odometry = new MecanumDriveOdometry(
      drive_kinematics, 
      gyro.getRotation2d(), 
      new MecanumDriveWheelPositions(pos_wheels[0], pos_wheels[1], pos_wheels[2], pos_wheels[3]));
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

  public Pose2d getPose (){
    return (drive_odometry.getPoseMeters());
  }

  public MecanumDriveWheelSpeeds getWheelSpeeds(){
    double[] array = new double[4];
    array[0] = ((dIzq.getSelectedSensorVelocity() * 10)/Constants.cpr) * Constants.kPerWheel;
    array[1] = ((dDer.getSelectedSensorVelocity() * 10)/Constants.cpr) * Constants.kPerWheel;
    array[2] = ((tIzq.getSelectedSensorVelocity() * 10)/Constants.cpr) * Constants.kPerWheel;
    array[3] = ((tDer.getSelectedSensorVelocity() * 10)/Constants.cpr) * Constants.kPerWheel;
    return (new MecanumDriveWheelSpeeds(array[0], array[1], array[2], array[3]));
  }

  public void resetOdometry(Pose2d pose) {
    ResetEncoders();
    drive_odometry.resetPosition(gyro.getRotation2d(), new MecanumDriveWheelPositions(0, 0, 0, 0), pose);
  }

  @Override
  public void periodic() {
    double[] pos_wheels = new double[4];
    pos_wheels = PositionsInMeters();
    drive_odometry.update(gyro.getRotation2d(), new MecanumDriveWheelPositions(pos_wheels[0], pos_wheels[1], pos_wheels[2], pos_wheels[3]));
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }
}
