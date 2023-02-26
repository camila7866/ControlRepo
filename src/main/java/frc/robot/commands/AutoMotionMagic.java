package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Drive;

public class AutoMotionMagic extends CommandBase {
  private final Drive m_Drive;
  private boolean m_isLateral;
  private double m_goal;
  public AutoMotionMagic (Drive drive, double goal, boolean isLateral) {
    m_isLateral = isLateral;
    m_Drive = drive;
    m_goal = Constants.ratio * (goal / Constants.kWheel);
    addRequirements(m_Drive);
  }


  @Override
  public void initialize() {
    m_Drive.configMastersForPosition();
    if (m_isLateral){
      m_Drive.followMotorInCrossover();
    }
    else {
      m_Drive.followMotorInFront();
    }
    m_Drive.ResetEncoders();
  }

  @Override
  public void execute() {
    if (m_isLateral){
      m_Drive.RunToPosition(-m_goal, m_goal);
    }
    else {
      m_Drive.RunToPosition(m_goal, m_goal);
    }
  }

  @Override
  public void end(boolean interrupted) {}

  @Override
  public boolean isFinished() {
    return m_Drive.MastersInZero();
  }
}