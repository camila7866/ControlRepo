package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Intake;

public class IntakeCommandAuto extends CommandBase {
  private final Intake m_Intake;
  private double m_vel;
  private boolean flag, m_TakeObject;
  public IntakeCommandAuto(Intake _Intake, double _vel, boolean TakeObject) {
    m_Intake = _Intake;
    m_vel = _vel;
    m_TakeObject = TakeObject; 
    addRequirements(m_Intake);
  }

  @Override
  public void initialize() {
    flag = false;
  }

  @Override
  public void execute() {
    m_Intake.setPowerIntake(m_vel);
    if (m_TakeObject){
      flag = m_Intake.getSensorState();
    }
    else {
      flag = !m_Intake.getSensorState();
    }
  }

  @Override
  public void end(boolean interrupted) {
    m_Intake.setPowerIntake(0);
  }

  @Override
  public boolean isFinished() {
    return flag;
  }
}
