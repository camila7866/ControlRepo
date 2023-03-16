package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Stretch;

public class PosGoalStretch extends CommandBase {
  private final Stretch m_Stretch;
  private int m_level;
  private double posicion;
  private boolean flag;
  public PosGoalStretch(Stretch _Stretch, int level) {
    m_Stretch = _Stretch;
    m_level = level;
    addRequirements(m_Stretch);
  }

  @Override
  public void initialize() {
    flag = false;
    m_Stretch.ConfigForPosition();
    if (Intake.latch){
      if (m_level == 0){
        posicion = 0;
      } else if (m_level == 1){
        posicion = -100;
      }
      else {
        posicion = -200;
      }
    }
    else {
      if (m_level == 0){
        posicion = 0;
      } else if (m_level == 1){
        posicion = -100;
      }
      else {
        posicion = -200;
      }
    }
  }

  @Override
  public void execute() {
    flag = m_Stretch.IsStopped(posicion);
    m_Stretch.StretchPosition(posicion);
  }

  @Override
  public void end(boolean interrupted) {
    m_Stretch.StretchPower(0);
  }

  @Override
  public boolean isFinished() {
    return flag;
  }
}
