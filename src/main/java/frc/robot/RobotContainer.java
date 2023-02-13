package frc.robot;

import frc.robot.commands.Auto1;
import frc.robot.commands.RestartHeading;
import frc.robot.commands.TeleopDrive;
import frc.robot.subsystems.Drive;

import java.util.List;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.RamseteController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryConfig;
import edu.wpi.first.math.trajectory.TrajectoryGenerator;
import edu.wpi.first.math.trajectory.constraint.DifferentialDriveVoltageConstraint;
import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RamseteCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

public class RobotContainer {
  SendableChooser<Command> m_chooser_alliance = new SendableChooser<>();
  SendableChooser<Command> m_chooser_zone = new SendableChooser<>();
  private final Drive m_Drive = new Drive();
  private final TeleopDrive m_TeleopDrive = new TeleopDrive(m_Drive, false);
  public static CommandXboxController Control0 = new CommandXboxController(0);
  public static CommandXboxController Control1 = new CommandXboxController(1);

  public RobotContainer() {
    m_chooser_alliance.setDefaultOption("Red Alliance", m_TeleopDrive);
    m_chooser_alliance.addOption("Blue Alliance", m_TeleopDrive);
    m_chooser_zone.setDefaultOption("Left Zone", m_TeleopDrive);
    m_chooser_zone.addOption("Middle Zone", m_TeleopDrive);
    m_chooser_zone.addOption("Right Zone", m_TeleopDrive);
    SmartDashboard.putData("Alliance", m_chooser_alliance);
    SmartDashboard.putData("Zone", m_chooser_zone);
    //m_chooser_alliance.getSelected();
    m_Drive.setDefaultCommand(m_TeleopDrive);
    configureBindings();
  }

  private void configureBindings() {
    Control0.a().toggleOnTrue(new Auto1(m_Drive, 1000, false));
    Control0.b().toggleOnTrue(new Auto1(m_Drive, 1000, true));
    Control0.x().toggleOnTrue(new Auto1(m_Drive, -1000, false));
    Control0.y().toggleOnTrue(new Auto1(m_Drive, -1000, true));
  }

  public Command getAutonomousCommand() {
    return (m_TeleopDrive);
  }
}
