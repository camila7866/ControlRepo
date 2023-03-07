package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Intake extends SubsystemBase {
  public CANSparkMax intake = new CANSparkMax(7, MotorType.kBrushed);
  public Intake() {
    intake.setIdleMode(IdleMode.kBrake);
  }

  @Override
  public void periodic() {
  }
}
