package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Stretch;

public class StretchCommand extends CommandBase {
  private Stretch m_Stretch;
  private boolean m_isManual, flag = false;
  private double m_pos;
  public StretchCommand(Stretch _Stretch, boolean isManual, double pos) {
    m_Stretch = _Stretch;
    m_isManual = isManual;
    m_pos = pos;
    addRequirements(m_Stretch);
  }

  @Override
  public void initialize() {
    m_Stretch.ConfigForPosition();
  }

  @Override
  public void execute() {
    if (m_isManual){
      double power = RobotContainer.Control1.getLeftTriggerAxis() - RobotContainer.Control1.getRightTriggerAxis();
      if (Math.abs(power) <= 0.2){
        power = 0;
      }
      m_Stretch.StretchPower(power);
    }
    else {
      flag = m_Stretch.IsStopped(m_pos);
      m_Stretch.StretchPosition(m_pos);
    }
  }

  @Override
  public void end(boolean interrupted) {
    m_Stretch.StretchPower(0);
  }

  @Override
  public boolean isFinished() {
    return flag;
  }
}
