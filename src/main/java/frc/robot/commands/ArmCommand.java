package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Arm;

public class ArmCommand extends CommandBase {
  private Arm m_Arm;
  private boolean m_isManual, flag = false, m_isFullPos;
  private double m_pos;
  public ArmCommand(Arm _Arm, boolean isManual, double pos, boolean isFullPos) {
    m_Arm = _Arm;
    m_isManual = isManual;
    m_isFullPos = isFullPos;
    m_pos = pos;
    addRequirements(m_Arm);
  }

  @Override
  public void initialize() {
    if (m_isFullPos){
      m_Arm.ConfigForPosition(1200, 1200);
    }
    else {
      m_Arm.ConfigForPosition(1500, 2000); 
    }
  }

  @Override
  public void execute() {
    if (m_isManual){
      double power = RobotContainer.Control1.getRightY();
      if (Math.abs(power) <= 0.2){
        power = 0;
      }
      m_Arm.ArmPower(power);
    }
    else {
      flag = m_Arm.IsStopped(m_pos);
      m_Arm.ArmPosition(m_pos);
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
