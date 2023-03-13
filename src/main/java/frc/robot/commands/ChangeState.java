package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Intake;

public class ChangeState extends CommandBase {
  private final Intake m_Intake;
  public ChangeState(Intake _Intake) {
    m_Intake = _Intake;
  }

  @Override
  public void initialize() {}

  @Override
  public void execute() {
    m_Intake.toggle();
  }

  @Override
  public void end(boolean interrupted) {}

  @Override
  public boolean isFinished() {
    return true;
  }
}
