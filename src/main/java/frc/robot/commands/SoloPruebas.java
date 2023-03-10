package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drive;

public class SoloPruebas extends CommandBase {
  Drive m_Drive;
  double m_vel_der, m_vel_izq;
  public SoloPruebas(Drive _Drive, double vel_der, double vel_izq) {
    m_Drive = _Drive;
    m_vel_der = vel_der;
    m_vel_izq = vel_izq; 
    addRequirements(m_Drive);
  }

  @Override
  public void initialize() {
    m_Drive.followMotorInCrossover();
  }

  @Override
  public void execute() {
    m_Drive.setToMasters(m_vel_izq, m_vel_der);
  }

  @Override
  public void end(boolean interrupted) {
    m_Drive.setToMasters(0, 0);
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
