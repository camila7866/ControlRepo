package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drive;

public class AutoBalance extends CommandBase {
  private final Drive m_Drive;
  private PIDController pidController = new PIDController(0.025, 0, 0);
  public AutoBalance(Drive _Drive) {
    m_Drive = _Drive;
    addRequirements(m_Drive);
  }
  
  @Override
  public void initialize() {
    m_Drive.followMotorInFront();
    //Diferencia de maximo 15 grados
    pidController.setSetpoint(90);
    pidController.setTolerance(2);
  }
  

  @Override
  public void execute() {
    double input = m_Drive.getPitch();
    double output = -pidController.calculate(input); 
    m_Drive.setToMasters(output, output);
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
