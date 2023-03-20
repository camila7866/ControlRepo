package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Drive;

public class OnlyVelDrive extends CommandBase {
  private final Drive m_Drive;
  private double m_velocity;
  public OnlyVelDrive(Drive _Drive, double velocity) {
    m_Drive = _Drive;
    m_velocity = velocity;
    addRequirements(m_Drive);
  }

  @Override
  public void initialize() {
    m_Drive.followMotorInFront();
  }

  @Override
  public void execute() {
    m_Drive.setToMasters(m_velocity, m_velocity);
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
