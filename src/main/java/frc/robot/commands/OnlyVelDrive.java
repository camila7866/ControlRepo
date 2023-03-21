package frc.robot.commands;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drive;

public class OnlyVelDrive extends CommandBase {
  private final Drive m_Drive;
  private double m_vel;
  public OnlyVelDrive(Drive _Drive, double vel) {
    m_Drive = _Drive;
    m_vel = vel;
    addRequirements(m_Drive);
  }

  @Override
  public void initialize() {}

  @Override
  public void execute() {
    m_Drive.dDer.set(ControlMode.PercentOutput, m_vel);
    m_Drive.dIzq.set(ControlMode.PercentOutput, m_vel);
    m_Drive.tDer.set(ControlMode.PercentOutput, m_vel);
    m_Drive.tIzq.set(ControlMode.PercentOutput, m_vel);
  }

  @Override
  public void end(boolean interrupted) {}

  @Override
  public boolean isFinished() {
    return false;
  }
}
