package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Drive;

public class AutoMotionMagic extends CommandBase {
  private final Drive m_Drive;
  private boolean flag, first_zero;
  private double m_goal, m_max_vel, m_max_accel;
  public AutoMotionMagic (Drive drive, double goal, double max_vel, double max_accel) {
    m_Drive = drive;
    m_goal = Constants.cpr * Constants.ratio * (goal / Constants.kWheel);
    m_max_vel = max_vel;
    m_max_accel = max_accel;
    flag = false;
    addRequirements(m_Drive);
  }


  @Override
  public void initialize() {
    flag = false;
    m_Drive.configMastersForPosition(m_max_vel, m_max_accel);
    m_Drive.followOnlyOneMaster();
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
    m_Drive.RunToPosition(m_goal, m_goal);
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
