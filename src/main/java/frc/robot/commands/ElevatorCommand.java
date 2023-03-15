package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Elevator;

public class ElevatorCommand extends CommandBase {
  private final Elevator m_Elevator;
  private boolean m_isManual, flag = false;
  private double m_pos;
  public ElevatorCommand(Elevator _Elevator, boolean isManual, double pos) {
    m_Elevator = _Elevator;
    m_isManual = isManual; 
    m_pos = pos;
    addRequirements(m_Elevator);
  }

  @Override
  public void initialize() {
    m_Elevator.ConfigForPosition();
    flag = false;
  }

  @Override
  public void execute() {
    if (m_isManual){
      double power = -RobotContainer.Control1.getLeftY();
      if (Math.abs(power) <= 0.2){
        power = 0;
      }
      m_Elevator.ElevatorPower(power);
    }
    else {
      flag = m_Elevator.IsStopped(m_pos);
      m_Elevator.ElevatorPosition(m_pos);
    }
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
