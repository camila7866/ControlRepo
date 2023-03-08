package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drive;

public class Calibrating extends CommandBase {
  private final Drive m_Drive;
  private double pitch;
  public Calibrating(Drive _Drive) {
    m_Drive = _Drive;
    addRequirements(m_Drive);
  }

  @Override
  public void initialize() {
    m_Drive.restartNavx();

  }

  @Override
  public void execute() {
    pitch = m_Drive.getPitch();
  }

  @Override
  public void end(boolean interrupted) {

  }

  @Override
  public boolean isFinished() {
    m_Drive.setZeroPitch(pitch);
    return !m_Drive.Calibrating();
  }
}
