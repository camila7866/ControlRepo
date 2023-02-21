package frc.robot.commands;

import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drive;

public class Auto1 extends CommandBase {
  private final Drive m_Drive;
  private ProfiledPIDController pidController = new ProfiledPIDController(
    0.005, 
    0, 
    0, 
    new TrapezoidProfile.Constraints(3000, 1500));
  private boolean m_isLateral;
  public Auto1(Drive drive, double goal, boolean isLateral) {
    m_isLateral = isLateral;
    m_Drive = drive;
    addRequirements(m_Drive);
    pidController.setTolerance(5);
    pidController.setGoal(goal);
  }

  @Override
  public void initialize() {
    if (m_isLateral){
      m_Drive.followMotorInCrossover();
    }
    else {
      m_Drive.followMotorInFront();
    }
    m_Drive.ResetEncoders();
    pidController.reset(0);
  }

  @Override
  public void execute() {
    double vel = pidController.calculate(m_Drive.dIzq.getSelectedSensorPosition());
    if (m_isLateral){
      m_Drive.setToMasters(vel, -vel);
    }
    else { 
      m_Drive.setToMasters(vel, vel);
    }
  }

  @Override
  public void end(boolean interrupted) {
    m_Drive.setToMasters(0, 0);
  }

  @Override
  public boolean isFinished() {
    return pidController.atGoal();
  }
}
