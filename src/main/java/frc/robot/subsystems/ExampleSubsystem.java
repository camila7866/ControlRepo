// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.TalonFXFeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ExampleSubsystem extends SubsystemBase {
  public static TalonFX talon = new TalonFX(0);
  /** Creates a new ExampleSubsystem. */
  public ExampleSubsystem() {
    talon.configFactoryDefault();
    talon.configSelectedFeedbackSensor(TalonFXFeedbackDevice.IntegratedSensor, 0, 0);
    talon.configNeutralDeadband(0.001);
    talon.setSensorPhase(false);
    talon.setStatusFramePeriod(StatusFrameEnhanced.Status_13_Base_PIDF0, 10, 0);
		talon.setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, 10, 0);
    talon.configNominalOutputForward(0);
		talon.configNominalOutputReverse(0);
		talon.configPeakOutputForward(1);
		talon.configPeakOutputReverse(-1); 
    talon.selectProfileSlot(0, 0);
    talon.config_kF(0, 0.2);
		talon.config_kP(0, 0.2);
		talon.config_kI(0, 0);
	  talon.config_kD(0, 0);
    talon.configMotionCruiseVelocity(15000);
    talon.configMotionAcceleration(6000);
    talon.setSelectedSensorPosition(0);
  }

  /**
   * Example command factory method.
   *
   * @return a command
   */
  public CommandBase exampleMethodCommand() {
    // Inline construction of command goes here.
    // Subsystem::RunOnce implicitly requires `this` subsystem.
    return runOnce(
        () -> {
          /* one-time action goes here */
        });
  }

  /**
   * An example method querying a boolean state of the subsystem (for example, a digital sensor).
   *
   * @return value of some boolean subsystem state, such as a digital sensor.
   */
  public boolean exampleCondition() {
    // Query some boolean state, such as a digital sensor.
    return false;
  }

  public void RunPosition (){
    double targetPos = 0.5 * 2048 * 5;
		talon.set(TalonFXControlMode.MotionMagic, targetPos);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }
}
