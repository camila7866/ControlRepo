package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Intake;

public class IntakeCommand extends CommandBase {
  private final Intake m_Intake;
  private boolean delay;
  private double start_delay, end_delay;
  public IntakeCommand(Intake _Intake) {
    m_Intake = _Intake;
    addRequirements(m_Intake);
  }

  @Override
  public void initialize() {
  }
  
  @Override
  public void execute() {
    double power = RobotContainer.Control0.getRightTriggerAxis() - RobotContainer.Control0.getLeftTriggerAxis();
    if (!m_Intake.latch){
      power = -power;
    }
    SmartDashboard.putNumber("Vel Intake", power);
    m_Intake.setPowerIntake(power);
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
