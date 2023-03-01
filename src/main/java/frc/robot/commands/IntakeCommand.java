package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Intake;

public class IntakeCommand extends CommandBase {
  private final Intake m_Intake;
  private boolean m_IsManual;
  private double vel_aut;
  public IntakeCommand(Intake _Intake, boolean isManual, double vel) {
    m_Intake = _Intake;
    m_IsManual = isManual;
    vel_aut = vel; 
    addRequirements(m_Intake);
  }

  @Override
  public void initialize() {}

  @Override
  public void execute() {
    if (m_IsManual){
      m_Intake.intake.set(RobotContainer.Control0.getRightTriggerAxis() - RobotContainer.Control0.getLeftTriggerAxis());
    }
    else {
      m_Intake.intake.set(vel_aut);
    }
  }

  @Override
  public void end(boolean interrupted) {
    m_Intake.intake.set(0);
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
