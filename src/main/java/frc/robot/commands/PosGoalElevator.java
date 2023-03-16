package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.Intake;

public class PosGoalElevator extends CommandBase {
  private final Elevator m_Elevator;
  private double posicion;
  private boolean flag;
  private int m_level;
  public PosGoalElevator(Elevator _Elevator, int level) {
    m_Elevator = _Elevator;
    m_level = level;
    addRequirements(m_Elevator);
  }

  @Override
  public void initialize() {
    m_Elevator.ConfigForPosition();
    if (Intake.latch){
      if (m_level == 0){
        posicion = 0;
      } else if (m_level == 1){
        posicion = 50;
      }
      else {
        posicion = 90;
      }
    }
    else {
      if (m_level == 0){
        posicion = 0;
      } else if (m_level == 1){
        posicion = 50;
      }
      else {
        posicion = 90;
      }
    }
  }

  @Override
  public void execute() {
    flag = m_Elevator.IsStopped(posicion);
    m_Elevator.ElevatorPosition(posicion);
  }

  @Override
  public void end(boolean interrupted) {
    m_Elevator.ElevatorPower(0);
  }

  @Override
  public boolean isFinished() {
    return flag;
  }
}
