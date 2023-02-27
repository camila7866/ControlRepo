package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drive;

public class DriveGoToAngle extends CommandBase {
  private final Drive m_Drive;
  private PIDController pidController = new PIDController(0.0002, 0, 0);
  public DriveGoToAngle(Drive _Drive, double target_angle) {
    m_Drive = _Drive;
    pidController.enableContinuousInput(-180, 180);
    pidController.setTolerance(5);
    pidController.setSetpoint(target_angle);
    addRequirements(m_Drive);
  }

  @Override
  public void initialize() {
    pidController.reset();
    m_Drive.followMotorInFront();
  }

  @Override
  public void execute() {
    double out = pidController.calculate(m_Drive.getBotHeadingDegrees());
    m_Drive.setToMasters(out, -out);
  }

  @Override
  public void end(boolean interrupted) {
    m_Drive.setToMasters(0, 0);
  }

  @Override
  public boolean isFinished() {
    return pidController.atSetpoint();
  }
}
