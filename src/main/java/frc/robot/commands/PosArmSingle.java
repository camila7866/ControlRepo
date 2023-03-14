package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Intake;

public class PosArmSingle extends CommandBase {
  private final Intake m_Intake;
  private final Arm m_Arm;
  private double posicion;
  private boolean flag;
  public PosArmSingle(Intake _Intake, Arm _Arm) {
    m_Intake = _Intake;
    m_Arm = _Arm;
    addRequirements(m_Arm);
  }

  @Override
  public void initialize() {
    m_Arm.ConfigForPosition(1500, 2000);
    if (!m_Intake.latch){
      posicion = -15;
    }
    else {
      posicion = -30;
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
