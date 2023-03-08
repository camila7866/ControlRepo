package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.Intake;

public class AutoBalance extends CommandBase {
  private final Drive m_Drive;
  private final Intake m_Intake;
  private PIDController pidController = new PIDController(2, 0, 0);
  public AutoBalance(Drive _Drive, Intake _Intake) {
    m_Drive = _Drive;
    m_Intake = _Intake;
    addRequirements(m_Drive);
    addRequirements(m_Intake);
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
    //m_Drive.setToMasters(output, output);
    m_Intake.intake.set(output);
  }

  @Override
  public void end(boolean interrupted) {
    m_Intake.intake.set(0);
    //m_Drive.setToMasters(0, 0);
  }

  @Override
  public boolean isFinished() {
    return pidController.atSetpoint();
  }
}
