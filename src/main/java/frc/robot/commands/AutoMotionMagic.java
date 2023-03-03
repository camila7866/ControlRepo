package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Drive;

public class AutoMotionMagic extends CommandBase {
  private final Drive m_Drive;
  private boolean m_isLateral, flag;
  private double m_goal;
  public AutoMotionMagic (Drive drive, double goal, boolean isLateral) {
    m_isLateral = isLateral;
    m_Drive = drive;
    m_goal = Constants.ratio * (goal / Constants.kWheel);
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
  }

  @Override
  public void execute() {
    flag = m_Drive.MastersInZero(Math.abs(m_goal));
    //double Daux = Math.abs(m_goal) - Math.abs(m_Drive.dDer.getSelectedSensorPosition());
    //flag = m_Drive.MastersInZero(m_goal)
    if (m_isLateral){
      m_Drive.RunToPosition(-m_goal, m_goal);
    }
    else {
      m_Drive.RunToPosition(m_goal, m_goal);
    }
    //if (Daux <=5) {
      //flag = true; 
    //}
    SmartDashboard.putBoolean("Flag Auto", flag);
  }

  @Override
  public void end(boolean interrupted) {
    flag = false;
  }

  @Override
  public boolean isFinished() {
    return flag;
  }
}
