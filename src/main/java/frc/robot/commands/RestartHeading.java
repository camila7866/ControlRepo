package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drive;

public class RestartHeading extends CommandBase {
  private final Drive m_Drive;
  private double targetHeading, currentHeading;
  private PIDController pidController = new PIDController(0, 0, 0);
  public RestartHeading(Drive _Drive, double angle) {
    m_Drive = _Drive;
    targetHeading = angle;
    addRequirements(_Drive);
  }

  @Override
  public void initialize() {
    pidController.reset();
    pidController.setSetpoint(targetHeading);
    pidController.setTolerance(5);
  }

  @Override
  public void execute() {
    currentHeading = m_Drive.gyro.getRotation2d().getDegrees();
    double vel = pidController.calculate(currentHeading);
    SmartDashboard.putNumber("Vel Pid: ", vel);
    m_Drive.givePower(-vel, vel, -vel, vel);
  }

  @Override
  public void end(boolean interrupted) {}

  @Override
  public boolean isFinished() {
    return pidController.atSetpoint();
  }
}
