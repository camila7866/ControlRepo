package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Arm;

public class RestartArm extends CommandBase {
  private final Arm m_Arm;
  private boolean flag;
  public RestartArm(Arm _Arm) {
    m_Arm = _Arm;
    addRequirements(m_Arm);
  }

  @Override
  public void initialize() {
    flag = false;
  }

  @Override
  public void execute() {
    m_Arm.ArmPower(0.8);
    flag = m_Arm.limit_for.isPressed();
  }

  @Override
  public void end(boolean interrupted) {
    m_Arm.ArmPower(0);
    m_Arm.ResetEncoder();
  }

  @Override
  public boolean isFinished() {
    return flag;
  }
}
