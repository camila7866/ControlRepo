// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drive;

public class AutoDrive extends CommandBase {
  double YDistance;
  private Drive auxDrive;
  public AutoDrive(Drive aDrive, double aYDistance) {
    auxDrive = aDrive;
    addRequirements(auxDrive);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    auxDrive.ResetEncoders();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    auxDrive.DriveFB(YDistance);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
