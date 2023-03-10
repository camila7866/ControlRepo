package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Drive;

public class AutoMotionMagic extends CommandBase {
  private final Drive m_Drive;
  private boolean m_isLateral, flag, first_zero;
  private double m_goal;
  private int counter;
  public AutoMotionMagic (Drive drive, double goal, boolean isLateral) {
    m_isLateral = isLateral;
    m_Drive = drive;
    m_goal = Constants.cpr * Constants.ratio * (goal / Constants.kWheel);
    flag = false;
    addRequirements(m_Drive);
  }


  @Override
  public void initialize() {
    flag = false;
    m_Drive.configMastersForPosition();
    if (m_isLateral){
      m_Drive.followMotorInCrossover();
    }
    else {
      m_Drive.followMotorInFront();
    }
    m_Drive.ResetEncoders();
    first_zero = true;
  }

  @Override
  public void execute() {
    if (first_zero){
      if (!m_Drive.MastersInZero()){
        first_zero = false;
      }
    }
    else {
      if (m_Drive.MastersInZero()){
        flag = true;
      }
    }
    if (m_isLateral){
      m_Drive.RunToPosition(-m_goal, m_goal);
    }
    else {
      m_Drive.RunToPosition(m_goal, m_goal);
    }
    SmartDashboard.putNumber("AutoMotionGoal", m_goal);
    SmartDashboard.putBoolean("AutoMotionMagicIsFinished", flag);
    SmartDashboard.putBoolean("MasterInzero", m_Drive.MastersInZero());
  }

  @Override
  public void end(boolean interrupted) {
  }

  @Override
  public boolean isFinished() {
    return flag;
  }
}
