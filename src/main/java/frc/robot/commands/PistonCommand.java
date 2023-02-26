package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Piston;

public class PistonCommand extends CommandBase {
  private final Piston m_Piston;
  private boolean flag;
  public PistonCommand(Piston _Piston) {
    m_Piston = _Piston; 
  }

  @Override
  public void initialize() {
    flag = false;
  }

  @Override
  public void execute() {
    m_Piston.toggleSolenoid();
    flag = true;
  }

  @Override
  public void end(boolean interrupted) {}

  @Override
  public boolean isFinished() {
    return flag;
  }
}
