package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Arm;

public class ArmCommandFeedForward extends CommandBase {
  private final Arm m_Arm;
  public ArmCommandFeedForward(Arm _Arm) {
    m_Arm = _Arm;
    addRequirements(m_Arm);
  }

  @Override
  public void initialize() {}

  @Override
  public void execute() {}

  @Override
  public void end(boolean interrupted) {}

  @Override
  public boolean isFinished() {
    return false;
  }
}
