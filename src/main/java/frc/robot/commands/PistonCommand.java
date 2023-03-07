package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Piston;

public class PistonCommand extends CommandBase {
  private final Piston m_Piston;
  private boolean flag = false, m_isReverse, m_isForward;
  public PistonCommand(Piston s_Piston, boolean isReverse, boolean isForward) {
    m_Piston = s_Piston; 
    m_isReverse = isReverse;
    m_isForward = isForward;
    addRequirements(m_Piston);
    flag = false;
  }

  @Override
  public void initialize() {
    flag = false;
  }

  @Override
  public void execute() {
    if (m_isForward){
      m_Piston.setForward();
    }
    else if (m_isReverse){
      m_Piston.setReverse();
    }
    else {
      m_Piston.toggleSolenoid();
    }
    flag = true;
  }

  @Override
  public void end(boolean interrupted) {
  }

  @Override
  public boolean isFinished() {
    SmartDashboard.putBoolean("a", flag);
    return flag;
  }
}
