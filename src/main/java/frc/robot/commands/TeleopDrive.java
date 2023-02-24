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
    double y_right = -RobotContainer.Control0.getRightY();
    double m; 
    if ((x_right >= -0.1 && x_right <= 0.1) && (y_right >= -0.1 && y_right <= 0.1)){
      x_right = 0;
      if (x_left != 0){
        m = y_left/x_left;   
      } else {
        m = 2;
      }
      if ((m == 2) || (Math.abs(m) >= 1)) {
        x_left = 0;
      }
      else {
        y_left = 0;
      }
    }
    else {
      x_left = 0;
      y_left = 0;
    }
    double power_y, power_x, power_twist;
    power_twist = x_right * 0.75;
    if (m_isFieldCentric){
      double botHeading = m_Drive.getBotHeadingFOC();
      double botHeadingDegrees = m_Drive.getBotHeadingDegrees();
      double angle_rounded;
      if (Math.abs(botHeadingDegrees) < 45){
          angle_rounded = 0;
      } 
      else if (Math.abs(botHeadingDegrees) < 135){
          angle_rounded = Math.PI/2;
          if (botHeadingDegrees < 0){
              angle_rounded = angle_rounded * -1;
          }
      } 
      else {
          angle_rounded = Math.PI;
      }
      power_y = x_left * Math.sin(angle_rounded) + y_left * Math.cos(angle_rounded);
      power_x = x_left * Math.cos(angle_rounded) - y_left * Math.sin(angle_rounded);
    }
    else {
      power_y = y_left;
      power_x = x_left;
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
