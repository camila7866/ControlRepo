// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drive;

public class Calibrating extends CommandBase {
  private final Drive m_Drive;
  private double pitch;
  public Calibrating(Drive _Drive) {
    m_Drive = _Drive;
    addRequirements(m_Drive);
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_Drive.restartNavx();

  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    pitch = m_Drive.getPitch();
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {

  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    m_Drive.setZeroPitch(pitch);
    return !m_Drive.Calibrating();
  }
}
