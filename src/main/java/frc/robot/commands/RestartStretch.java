package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Stretch;

public class RestartStretch extends CommandBase {
  private final Stretch m_Stretch;
  private boolean flag;
  public RestartStretch(Stretch _Stretch) {
    m_Stretch = _Stretch;
    addRequirements(m_Stretch);
  }

  @Override
  public void initialize() {
    flag = false;
  }

  @Override
  public void execute() {
    m_Stretch.StretchPower(1);
    flag = m_Stretch.limit_for.isPressed();
  }

  @Override
  public void end(boolean interrupted) {
    m_Stretch.StretchPower(0);
    m_Stretch.ResetEncoder();
  }

  @Override
  public boolean isFinished() {
    return flag;
  }
}
