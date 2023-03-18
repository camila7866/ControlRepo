package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drive;

public class AutoBalance extends CommandBase {
  private final Drive m_Drive;
  private PIDController pidController = new PIDController(2, 0, 0);
  public AutoBalance(Drive _Drive) {
    m_Drive = _Drive;
    addRequirements(m_Drive);
  }
  
  @Override
  public void initialize() {
    m_Drive.followMotorInFront();
    pidController.setSetpoint(m_Drive.getZeroPitch());
    pidController.setTolerance(0.1);
  }
  

  @Override
  public void execute() {
    SmartDashboard.putBoolean("AutoBalanceFlag", pidController.atSetpoint());
    SmartDashboard.putNumber("PitchCommand", m_Drive.getPitch());
    double input = m_Drive.getPitch();
    double output = pidController.calculate(input); 
    m_Drive.setToMasters(output, output);
  }

  @Override
  public void end(boolean interrupted) {
    m_Drive.setToMasters(0, 0);
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
