package frc.robot.commands;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drive;

public class VelDriveWithBalance extends CommandBase {
  private final Drive m_Drive;
  private boolean flag;
  private double m_vel;
  public VelDriveWithBalance(Drive _Drive, double vel) {
    m_Drive = _Drive;
    m_vel = vel;
    addRequirements(m_Drive);
  }

  @Override
  public void initialize() {}

  @Override
  public void execute() {
    if (Math.abs(m_Drive.getZeroPitch() - m_Drive.getPitch()) <= 0.1){
      flag = true;
    }
    else {
      m_Drive.dDer.set(ControlMode.PercentOutput, m_vel);
      m_Drive.dIzq.set(ControlMode.PercentOutput, m_vel);
      m_Drive.tDer.set(ControlMode.PercentOutput, m_vel);
      m_Drive.tIzq.set(ControlMode.PercentOutput, m_vel);
    }
  }

  @Override
  public void end(boolean interrupted) {
    m_Drive.dDer.set(ControlMode.PercentOutput, 0);
    m_Drive.dIzq.set(ControlMode.PercentOutput, 0);
    m_Drive.tDer.set(ControlMode.PercentOutput, 0);
    m_Drive.tIzq.set(ControlMode.PercentOutput, 0);
  }

  @Override
  public boolean isFinished() {
    return flag;
  }
}
