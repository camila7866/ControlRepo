package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.LEDS;

public class ChangeState extends CommandBase {
  private final LEDS m_LEDS;
  public ChangeState(LEDS _LEDS) {
    m_LEDS = _LEDS;
    addRequirements(m_LEDS);
  }

  @Override
  public void initialize() {
  }
  
  @Override
  public void execute() {
    m_LEDS.toggle();

  }

  @Override
  public void end(boolean interrupted) {}

  @Override
  public boolean isFinished() {
    return true;
  }
}
