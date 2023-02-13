package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drive;

public class UntilColorDetected extends CommandBase {
  private final Drive m_Drive;
  public UntilColorDetected(Drive _Drive) {
    m_Drive = _Drive;
    addRequirements(m_Drive);
  }

  @Override
  public void initialize() {
    m_Drive.followMotorInFront();
  }

  @Override
  public void execute() {
    m_Drive.setToMasters(0.2, 0.2);
  }

  @Override
  public void end(boolean interrupted) {
    m_Drive.setToMasters(0, 0);
  }

  @Override
  public boolean isFinished() {
    return m_Drive.IsBlueOrRed();
  }
}
