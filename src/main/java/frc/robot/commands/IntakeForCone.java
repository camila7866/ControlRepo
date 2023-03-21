package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Intake;

public class IntakeForCone extends CommandBase {
  private final Intake m_Intake;
  private double m_vel;
  public IntakeForCone(Intake _Intake, double vel) {
    m_Intake = _Intake;
    m_vel = vel;
    addRequirements(m_Intake);
  }

  @Override
  public void initialize() {}

  @Override
  public void execute() {
    m_Intake.setPowerIntake(m_vel);
  }

  @Override
  public void end(boolean interrupted) {
    m_Intake.setPowerIntake(0);
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
