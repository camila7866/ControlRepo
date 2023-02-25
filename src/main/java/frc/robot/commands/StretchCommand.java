package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Stretch;

public class StretchCommand extends CommandBase {
  private Stretch m_Stretch;
  private boolean m_isManual, flag = false, initial_zero;
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
    initial_zero = true;
  }

  @Override
  public void execute() {
    if (m_isManual){
      m_Stretch.StretchPower(RobotContainer.Control1.getLeftTriggerAxis() - RobotContainer.Control1.getRightTriggerAxis());
    }
    else {
      if (initial_zero){
        if (!m_Stretch.IsStopped()){
          initial_zero = false;
        }
      }
      else {
        if (m_Stretch.IsStopped()){
          flag = true;
        }
      }
      SmartDashboard.putBoolean("Flag Command Stretch", flag);
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
