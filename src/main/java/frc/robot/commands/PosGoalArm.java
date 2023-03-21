package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Intake;

public class PosGoalArm extends CommandBase {
  private final Arm m_Arm;
  private boolean flag;
  private int m_level;
  private double posicion;
  public PosGoalArm(Arm _Arm, int level) {
    m_Arm = _Arm;
    m_level = level;
    addRequirements(m_Arm);
  }

  @Override
  public void initialize() {
    flag = false;
    m_Arm.ConfigForPosition(1200, 4800);
    if (Intake.latch){
      if (m_level == 0){
        posicion = -130;
      } else if (m_level == 1){
        posicion = -115;
      }
      else {
        posicion = -89;
      }
    }
    else {
      if (m_level == 0){
        posicion = -70;
      } else if (m_level == 1){
        posicion = -40;
      }
      else {
        posicion = -55;
      }
    }
  }

  @Override
  public void execute() {
    flag = m_Arm.IsStopped(posicion);
    m_Arm.ArmPosition(posicion);
  }

  @Override
  public void end(boolean interrupted) {
    m_Arm.ArmPower(0);
  }

  @Override
  public boolean isFinished() {
    return flag;
  }
}
