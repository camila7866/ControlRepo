package frc.robot.commands;

import frc.robot.RobotContainer;
import frc.robot.subsystems.Drive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class TeleopDrive extends CommandBase {
  private final Drive m_subsystem;
  private boolean m_isFieldCentric;

  public TeleopDrive(Drive subsystem, boolean isFieldCentric) {
    m_subsystem = subsystem;
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
    m_subsystem.givePower(
      //dDer
      y_left - x_left - x_right,
      //dIzq
      y_left + x_left + x_right,
      //tDer
      y_left + x_left -x_right,
      //tIzq 
      y_left - x_left + x_right
    );
    /*
     else {
       double heading = m_subsystem.gyro.getRotation2d().getRadians();
       double rotx = x_left * Math.cos(heading) - y_left * Math.sin(heading);
       Double roty = x_left * Math.sin(heading) + y_left * Math.cos(heading);
       double d = Math.max(Math.abs(roty) + Math.abs(rotx) + Math.abs(x_right), 1);
       m_subsystem.givePower(
       (roty - rotx - x_right)/d,
       (roty + rotx + x_right)/d,
       (roty + rotx -x_right)/d, 
       (roty - rotx + x_right)/d);
     }
     */
  }

  @Override
  public void end(boolean interrupted) {}

  @Override
  public boolean isFinished() {
    return false;
  }
}
