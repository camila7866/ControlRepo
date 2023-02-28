package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drive;

public class DriveStraight extends CommandBase {
  private final Drive m_Drive;
  private final double kp = 0.01111;
  private double m_target_angle, m_right, m_left;
  public DriveStraight(Drive _Drive, double target_angle, double right, double left) {
    m_Drive = _Drive;
    m_target_angle = target_angle;
    m_right = right;
    m_left = left;
    addRequirements(m_Drive);
  }

  @Override
  public void initialize() {
  }
  
  @Override
  public void execute() {
    double e = m_target_angle - m_Drive.getBotHeadingDegrees();
    double output_right = m_right, output_left = m_left;
    if (e < -2){
      output_left -= kp * Math.abs(e);
    }
    else if (e > 2){
      output_right -= kp * Math.abs(e);
    }
    m_Drive.setToMasters(output_left, output_right);
  }

  @Override
  public void end(boolean interrupted) {}

  @Override
  public boolean isFinished() {
    return false;
  }
}
