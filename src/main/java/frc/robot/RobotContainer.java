package frc.robot;

import frc.robot.commands.Auto2;
import frc.robot.commands.TeleopDrive;
import frc.robot.subsystems.Drive;
import edu.wpi.first.math.trajectory.TrajectoryConfig;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

public class RobotContainer {
  SendableChooser<Command> m_chooser_zone = new SendableChooser<>();
  private final Drive m_Drive = new Drive();
  private final TeleopDrive m_TeleopDrive = new TeleopDrive(m_Drive, false);
  public static CommandXboxController Control0 = new CommandXboxController(0);
  public static CommandXboxController Control1 = new CommandXboxController(1);

  TrajectoryConfig tConfig = new TrajectoryConfig(0, 0);

  public RobotContainer() {
    m_chooser_zone.setDefaultOption("Left Zone", m_TeleopDrive);
    m_chooser_zone.addOption("Middle Zone", m_TeleopDrive);
    m_chooser_zone.addOption("Right Zone", m_TeleopDrive);
    SmartDashboard.putData("Zone", m_chooser_zone);
    m_chooser_zone.getSelected();
    m_Drive.setDefaultCommand(m_TeleopDrive);
    configureBindings();
  }

  private void configureBindings() {
    Control0.a().toggleOnTrue(new Auto2(m_Drive, 10, 0.4, false));
    Control0.b().toggleOnTrue(new Auto2(m_Drive, 10, 0.4, true));
  }

  public Command getAutonomousCommand() {
    return (m_TeleopDrive);
  }
}
