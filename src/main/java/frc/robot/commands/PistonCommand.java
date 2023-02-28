package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Piston;

public class PistonCommand extends CommandBase {
  private final Piston m_Piston;
  private boolean flag, m_isReverse, m_isForward;
  public PistonCommand(Piston _Piston, boolean isReverse, boolean isForward) {
    m_Piston = _Piston; 
    m_isForward = isForward;
    m_isReverse = isReverse;
  }

  @Override
  public void initialize() {
    flag = false;
  }

  @Override
  public void execute() {
    if (m_isReverse){
      m_Piston.setReverse();
    }
    else if (m_isForward){
      m_Piston.setForward();
    }
    else {
      m_Piston.toggleSolenoid();
    }
    flag = true;
  }

  @Override
  public void end(boolean interrupted) {}

  @Override
  public boolean isFinished() {
    return flag;
  }
}
