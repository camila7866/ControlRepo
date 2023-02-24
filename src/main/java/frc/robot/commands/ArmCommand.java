package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Arm;

public class ArmCommand extends CommandBase {
  private final Arm m_Arm;
  private boolean m_isManual, flag = false;
  private double m_pos;
  public ArmCommand(Arm _Arm, boolean isManual, double pos) {
    m_Arm = _Arm;
    m_isManual = isManual; 
    m_pos = pos;
    addRequirements(m_Arm);
  }

  @Override
  public void initialize() {
    m_Arm.ConfigForPosition();
  }

  @Override
  public void execute() {
    if (m_isManual){
      m_Arm.ArmPower(RobotContainer.Control1.getRightY());
    }
    else {
      if (!m_Arm.IsStopped()) {
        m_Arm.ArmPosition(m_pos);
      }
      else {
        flag = true;
      }
    }
  }

  @Override
  public void end(boolean interrupted) {
    m_Arm.ArmPower(0);
  }

  @Override
  public boolean isFinished() {
    return flag;
  }
}
