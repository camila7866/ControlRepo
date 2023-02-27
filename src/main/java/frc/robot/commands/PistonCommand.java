package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Piston;

public class PistonCommand extends CommandBase {
  private final Piston m_Piston;
  private boolean flag, m_isWithoutToggle;
  public PistonCommand(Piston _Piston, boolean isWithoutToggle) {
    m_Piston = _Piston; 
    m_isWithoutToggle = isWithoutToggle;
  }

  @Override
  public void initialize() {
    flag = false;
  }

  @Override
  public void execute() {
    if (m_isWithoutToggle){
      m_Piston.setReverse();
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
