package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Piston;

public class PistonCommand extends CommandBase {
  private final Piston m_Piston;
  private boolean flag = false, m_isReverse;
  public PistonCommand(Piston s_Piston, boolean isReverse) {
    m_Piston = s_Piston; 
    m_isReverse = isReverse;
    addRequirements(m_Piston);
    flag = false;
  }

  @Override
  public void initialize() {
    flag = false;
  }

  @Override
  public void execute() {
    SmartDashboard.putBoolean("a", flag);
    if (m_isReverse){
      m_Piston.setReverse();
    }
    else {
      m_Piston.toggleSolenoid();
    }
    flag = true;
  }

  @Override
  public void end(boolean interrupted) {
    flag = false;
  }

  @Override
  public boolean isFinished() {
    SmartDashboard.putBoolean("a", flag);
    return flag;
  }
}
