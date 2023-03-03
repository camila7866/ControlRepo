package frc.robot;

import frc.robot.commands.ArmCommand;
import frc.robot.commands.AutoBalance;
import frc.robot.commands.AutoMotionMagic;
import frc.robot.commands.DriveGoToAngle;
import frc.robot.commands.DriveStraight;
import frc.robot.commands.ElevatorCommand;
import frc.robot.commands.IntakeCommand;
import frc.robot.commands.PistonCommand;
import frc.robot.commands.SoloPruebas;
import frc.robot.commands.StretchCommand;
import frc.robot.commands.TeleopDrive;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Piston;
import frc.robot.subsystems.Stretch;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

public class RobotContainer {
  SendableChooser<Command> m_chooser_zone = new SendableChooser<>();

  private final Drive m_Drive = new Drive();
  private final TeleopDrive m_TeleopDrive = new TeleopDrive(m_Drive, false);

  private final Elevator m_Elevator = new Elevator();
  private final ElevatorCommand m_ElevatorCommand = new ElevatorCommand(m_Elevator, true, 0);

  private final Stretch m_Stretch = new Stretch();
  private final StretchCommand m_StretchCommand = new StretchCommand(m_Stretch, true, 0);

  private final Intake m_Intake = new Intake();
  private final IntakeCommand m_IntakeCommand = new IntakeCommand(m_Intake, true, 0);

  private final Arm m_Arm = new Arm();
  private final ArmCommand m_ArmCommand = new ArmCommand(m_Arm, true, 0, false);

  private final Piston m_Piston = new Piston();

  private final SequentialCommandGroup auto_left = new SequentialCommandGroup(
    new AutoMotionMagic(m_Drive, -3.5, false),
    new AutoMotionMagic(m_Drive, -2, true)
  );
  private final SequentialCommandGroup auto_middle = new SequentialCommandGroup(
    new AutoMotionMagic(m_Drive, -0.3, false),
    new DriveGoToAngle(m_Drive, 180),
    new AutoMotionMagic(m_Drive, 2.8, false)
  );
  private final SequentialCommandGroup auto_right = new SequentialCommandGroup(
    new AutoMotionMagic(m_Drive, -3.5, false),
    new AutoMotionMagic(m_Drive, -2, true)
  );
  private final SequentialCommandGroup restartAll = new SequentialCommandGroup(
    new PistonCommand(m_Piston, true),
    new ParallelCommandGroup(
      new ArmCommand(m_Arm, false, 0, false),
      new StretchCommand(m_Stretch, false, 0)
    ),
    new ElevatorCommand(m_Elevator, false, 0)
  );
  private final SequentialCommandGroup toTakeCone = new SequentialCommandGroup(
    new ElevatorCommand(m_Elevator, false, 60),
    new ParallelCommandGroup(
      new ArmCommand(m_Arm, false, -35, false),
      new PistonCommand(m_Piston, false)
    )
  );
  private final SequentialCommandGroup toTakeCube = new SequentialCommandGroup(
    new ElevatorCommand(m_Elevator, false, 60),
    new SequentialCommandGroup(
      new ArmCommand(m_Arm, false, -35, false), 
      new ArmCommand(m_Arm, false, -60, true)
    ),
    new IntakeCommand(m_Intake, false, -0.5)
  );

  public static CommandXboxController Control0 = new CommandXboxController(0);
  public static CommandXboxController Control1 = new CommandXboxController(1);
  
  public RobotContainer() {
    m_chooser_zone.setDefaultOption("Middle Zone", auto_middle);
    m_chooser_zone.addOption("Left Zone", auto_left);
    m_chooser_zone.addOption("Right Zone", auto_right);
    SmartDashboard.putData("Zone", m_chooser_zone);
    m_Drive.setDefaultCommand(m_TeleopDrive);
    m_Elevator.setDefaultCommand(m_ElevatorCommand);
    m_Stretch.setDefaultCommand(m_StretchCommand);
    m_Arm.setDefaultCommand(m_ArmCommand);
    m_Intake.setDefaultCommand(m_IntakeCommand);
    configureBindings();
  }

  private void configureBindings() {
    //Control 0
    Control0.leftBumper().toggleOnTrue(new TeleopDrive(m_Drive, true));
    Control0.rightBumper().toggleOnTrue(m_TeleopDrive);
    Control0.povUp().toggleOnTrue(new DriveGoToAngle(m_Drive, 0));
    Control0.povLeft().toggleOnTrue(new DriveGoToAngle(m_Drive, -90));
    Control0.povRight().toggleOnTrue(new DriveGoToAngle(m_Drive, 90));
    Control0.povDown().toggleOnTrue(new DriveGoToAngle(m_Drive, 180));
    Control0.a().toggleOnTrue(restartAll);
    Control0.b().whileTrue(toTakeCone);
    Control0.x().toggleOnTrue(new PistonCommand(m_Piston, false));
    Control0.y().whileTrue(toTakeCube);
    //Control 1
    Control1.leftBumper().toggleOnTrue(new StretchCommand(m_Stretch, false, 0));
    Control1.rightBumper().toggleOnTrue(new StretchCommand(m_Stretch, false, -360));
    Control1.b().toggleOnTrue(
      new SequentialCommandGroup(
        new ArmCommand(m_Arm, false, -35, false), 
        new ArmCommand(m_Arm, false, -60, true)
      )
    ); 
    Control1.x().toggleOnTrue(new SequentialCommandGroup(
      new PistonCommand(m_Piston, true),
      new ArmCommand(m_Arm, false, 0, false)));
    Control1.y().toggleOnTrue(new SequentialCommandGroup(
      new PistonCommand(m_Piston, true), 
      new ArmCommand(m_Arm, false, -35, false)));
    Control1.povDown().toggleOnTrue(new ElevatorCommand(m_Elevator, false, 0));
    Control1.povLeft().toggleOnTrue(new ElevatorCommand(m_Elevator, false, 60));
    Control1.povRight().toggleOnTrue(new ElevatorCommand(m_Elevator, false, 60));
    Control1.povUp().toggleOnTrue(new ElevatorCommand(m_Elevator, false, 90)); 
  }

  public Command getAutonomousCommand() {
    SequentialCommandGroup Auto = new SequentialCommandGroup(
      new PistonCommand(m_Piston, true),
      new ArmCommand(m_Arm, false, -40, false),
      new ParallelDeadlineGroup(new WaitCommand(1.5), new IntakeCommand(m_Intake, false, 1)),
      new ArmCommand(m_Arm, false, 0, false),
      m_chooser_zone.getSelected()
    );
    return (Auto); 
  }
}
