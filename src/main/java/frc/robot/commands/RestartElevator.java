package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Elevator;

public class RestartElevator extends CommandBase {
  private final Elevator m_Elevator;
  private boolean flag;
  public RestartElevator(Elevator _Elevator) {
    m_Elevator = _Elevator;
    addRequirements(m_Elevator);
  }

  @Override
  public void initialize() {
    flag = false;
  }

  @Override
  public void execute() {
    m_Elevator.ElevatorPower(-0.75);
    flag = m_Elevator.limit_rev.isPressed();
  }

  @Override
  public void end(boolean interrupted) {
    m_Elevator.ElevatorPower(0);
    m_Elevator.ResetEncoder();
  }

  @Override
  public boolean isFinished() {
    return flag;
  }
}
