package frc.robot.commands;

import frc.robot.RobotContainer;
import frc.robot.subsystems.Drive;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class TeleopDrive extends CommandBase {
  private final Drive m_Drive;
  private boolean m_isFieldCentric;

  public TeleopDrive(Drive subsystem, boolean isFieldCentric) {
    m_Drive = subsystem;
    m_isFieldCentric = isFieldCentric; 
    addRequirements(subsystem);
  }

  @Override
  public void initialize() {
  }

  @Override
  public void execute() {
    double x_left = RobotContainer.Control0.getLeftX();
    double y_left = -RobotContainer.Control0.getLeftY();
    double x_right = RobotContainer.Control0.getRightX();
    if (x_left >= -0.2 && x_left <= 0.2){
      x_left = 0;
    }
    if (y_left >= -0.2 && y_left <= 0.2){
      y_left = 0;
    }
    if (x_right >= -0.2 && x_right <= 0.2){
      x_right = 0;
    }
    x_right = x_right * 0.75;
    m_Drive.dDer.set(ControlMode.PercentOutput, y_left - x_left - x_right);
    m_Drive.dIzq.set(ControlMode.PercentOutput, y_left + x_left + x_right);
    m_Drive.tDer.set(ControlMode.PercentOutput, y_left + x_left - x_right);
    m_Drive.tIzq.set(ControlMode.PercentOutput, y_left - x_left + x_right);
  }

  @Override
  public void end(boolean interrupted) {}

  @Override
  public boolean isFinished() {
    return false;
  }
}
