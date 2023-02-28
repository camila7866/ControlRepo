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
    double power_x, power_y, power_twist;
    power_twist = x_right;
    if (m_isFieldCentric){
      double botHeading = m_Drive.getBotHeadingFOC();
      power_y = x_left * Math.sin(botHeading) + y_left * Math.cos(botHeading);
      power_x = x_left * Math.cos(botHeading) - y_left * Math.sin(botHeading);
    }
    else {
      power_x = x_left;
      power_y = y_left;
    }
    if (power_x >= -0.2 && power_x <= 0.2){
      power_x = 0;
    }
    if (power_y >= -0.2 && power_y <= 0.2){
      power_y = 0;
    }
    if (power_twist >= -0.2 && power_twist <= 0.2){
      power_twist = 0;
    }
    m_Drive.dDer.set(ControlMode.PercentOutput, power_y - power_x - power_twist);
    m_Drive.dIzq.set(ControlMode.PercentOutput, power_y + power_x + power_twist);
    m_Drive.tDer.set(ControlMode.PercentOutput, power_y + power_x - power_twist);
    m_Drive.tIzq.set(ControlMode.PercentOutput, power_y - power_x + power_twist);
  }

  @Override
  public void end(boolean interrupted) {}

  @Override
  public boolean isFinished() {
    return false;
  }
}