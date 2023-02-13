// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.SparkMaxLimitSwitch;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.SparkMaxLimitSwitch.Type;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Elevator extends SubsystemBase {
  public CANSparkMax elevator = new CANSparkMax(5, MotorType.kBrushless);
  public Elevator() {
    SparkMaxLimitSwitch limit =elevator.getForwardLimitSwitch(Type.kNormallyOpen);
    limit.enableLimitSwitch(true); 
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
