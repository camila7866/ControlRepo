package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Drive;

public class RestartHeading extends CommandBase {
  private final Drive m_Drive;
  ProfiledPIDController pidController = new ProfiledPIDController(
    0, 
    0, 
    0, 
    new TrapezoidProfile.Constraints(0, 0));
  public RestartHeading(Drive _Drive, double angle) {
    m_Drive = _Drive;
    addRequirements(_Drive);
    pidController.setTolerance(2);
    pidController.enableContinuousInput(-180, 180);
    pidController.setGoal(angle);
  }

  @Override
  public void initialize() {
    pidController.reset(m_Drive.gyro.getRotation2d().getDegrees());
    m_Drive.followMotorInFront();
  }

  @Override
  public void execute() {
    double vel = pidController.calculate(m_Drive.gyro.getRotation2d().getDegrees());
  }

  @Override
  public void end(boolean interrupted) {}

  @Override
  public boolean isFinished() {
    return pidController.atSetpoint();
  }
}
