package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Intake;

public class PosArmSingle extends CommandBase {
  private final Arm m_Arm;
  private double posicion;
  private boolean flag;
  public PosArmSingle(Arm _Arm) {
    m_Arm = _Arm; 
    addRequirements(m_Arm);
  }

  @Override
  public void initialize() {
    m_Arm.ConfigForPosition(1200, 4800);
    if (!Intake.latch){
      posicion = -9;
    }
    else {
      posicion = -24;
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
