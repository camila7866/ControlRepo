package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drive;

public class Auto2 extends CommandBase {
  private final Drive m_Drive;
  private boolean m_isLateral;
  private int m_goal;
  private double m_vel;
  public Auto2 (Drive drive, int goal, double vel, boolean isLateral) {
    m_isLateral = isLateral;
    m_Drive = drive;
    m_goal = goal;
    m_vel = vel;
    addRequirements(m_Drive);
  }


  @Override
  public void initialize() {
    m_Drive.configMastersForPosition();
    m_Drive.followMotorInFront();
    m_Drive.ResetEncoders();
  }

  @Override
  public void execute() {
    m_Drive.RunToPosition(m_goal, m_goal, m_vel, m_vel);
  }

  @Override
  public void end(boolean interrupted) {}

  @Override
  public boolean isFinished() {
    return false;
  }
}
