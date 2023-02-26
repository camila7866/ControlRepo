package frc.robot;

import frc.robot.commands.ArmCommand;
import frc.robot.commands.AutoBalance;
import frc.robot.commands.AutoMotionMagic;
import frc.robot.commands.DriveGoToAngle;
import frc.robot.commands.ElevatorCommand;
import frc.robot.commands.IntakeCommand;
import frc.robot.commands.PistonCommand;
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
    new AutoMotionMagic(m_Drive, 4.19, false),
    new AutoMotionMagic(m_Drive, 2, true)
  );
  private final SequentialCommandGroup auto_middle = new SequentialCommandGroup(
    new AutoMotionMagic(m_Drive, 2, false)
  );
  private final SequentialCommandGroup auto_right = new SequentialCommandGroup(
    new AutoMotionMagic(m_Drive, 4.19, false),
    new AutoMotionMagic(m_Drive, -2, true)
  );
  private final SequentialCommandGroup pos_mid = new SequentialCommandGroup(
    new ElevatorCommand(m_Elevator, false, 0),
    new ParallelCommandGroup(
      new ArmCommand(m_Arm, false, 0, false),
      new StretchCommand(m_Stretch, false, 0)
    )
  );
  private final SequentialCommandGroup pos_high = new SequentialCommandGroup(
    new ElevatorCommand(m_Elevator, false, 0),
    new ParallelCommandGroup(
      new ArmCommand(m_Arm, false, 0, false),
      new StretchCommand(m_Stretch, false, 0)
    )
  );
  private final SequentialCommandGroup restartAll = new SequentialCommandGroup(
    new ElevatorCommand(m_Elevator, false, 0),
    new ParallelCommandGroup(
      new ArmCommand(m_Arm, false, 0, false),
      new StretchCommand(m_Stretch, false, 0)
    )
  );

  public static CommandXboxController Control0 = new CommandXboxController(0);
  public static CommandXboxController Control1 = new CommandXboxController(1);
  
  public RobotContainer() {
    DriverStation.getLocation();
    m_chooser_zone.setDefaultOption("Left Zone", auto_left);
    m_chooser_zone.addOption("Middle Zone", auto_middle);
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
    //Control0.a().toggleOnTrue(new DriveGoToAngle(m_Drive, 0)); 
    //Control0.b().toggleOnTrue(new DriveGoToAngle(m_Drive, 90)); 
    //Control0.x().toggleOnTrue(new DriveGoToAngle(m_Drive, -90)); 
    //Control0.y().toggleOnTrue(new DriveGoToAngle(m_Drive, 180));
    //Control1.a().toggleOnTrue(pos_mid);
    //Control1.b().toggleOnTrue(pos_high);
    //Control1.x().toggleOnTrue(restartAll);
    //Control1.y().toggleOnTrue(new AutoBalance(m_Drive));
    //Control1.leftBumper().toggleOnTrue(new PistonCommand(m_Piston));
    /*
    */
    Control0.leftBumper().toggleOnTrue(new TeleopDrive(m_Drive, true));
    Control0.rightBumper().toggleOnTrue(m_TeleopDrive);
    Control0.povUp().toggleOnTrue(new DriveGoToAngle(m_Drive, 0));
    Control0.povRight().toggleOnTrue(new DriveGoToAngle(m_Drive, 90));
    Control0.povLeft().toggleOnTrue(new DriveGoToAngle(m_Drive, -90));
    Control0.povDown().toggleOnTrue(new DriveGoToAngle(m_Drive, 180));
    Control0.button(4).toggleOnTrue(new PistonCommand(m_Piston));
    Control1.a().toggleOnTrue(new ArmCommand(m_Arm, false, 0, false));
    Control1.b().toggleOnTrue(new ArmCommand(m_Arm, false, -30, false)); 
    Control1.x().toggleOnTrue(new SequentialCommandGroup(new ArmCommand(m_Arm, false, -37, false), new ArmCommand(m_Arm, false, -60, true)));
    Control1.y().toggleOnTrue(new ArmCommand(m_Arm, false, -60, true)); 
    Control0.a().toggleOnTrue(new ElevatorCommand(m_Elevator, false, 0));
    Control0.b().toggleOnTrue(new ElevatorCommand(m_Elevator, false, 60));
    Control0.x().toggleOnTrue(new ElevatorCommand(m_Elevator, false, 110));
    Control1.leftBumper().toggleOnTrue(new PistonCommand(m_Piston));
  }

  public Command getAutonomousCommand() {
    ParallelDeadlineGroup intakeWithTime = new ParallelDeadlineGroup(new WaitCommand(2) , new IntakeCommand(m_Intake, false, 0.15));
    SequentialCommandGroup Auto = new SequentialCommandGroup(
      pos_high,
      intakeWithTime,
      restartAll,
      m_chooser_zone.getSelected(),
      new AutoBalance(m_Drive)
    );
    return (m_TeleopDrive);
  }
}

/*
 * SOFT LIMITS
 * Elevador: 125
 * Stretch: -360
 * Brazo: -60 
 */
/*
 * CONTROL 0
 * Joystick Left - Adelante, Atras y Laterales
 * Joystick Right - Giro
 * Boton A - Robot gira viendo atras
 * Boton B - Robot gira viendo derecha
 * Boton X - Robot gira viendo izquierda
 * Boton Y - Robot gira viendo enfrente
 * Left Bumper - Activar FOC
 * Right Bumper - Activar mecanum normal
 * Manual del Intake
 * Left Trigger - Mete
 * Right Trigger - Saca
 * CONTROL 1
 * Manual Elevador
 * Joystick Left - Sube/Baja Elevador
 * Manual Brazo
 * Joystick Right - Mueve Brazo
 * Boton A - Posicion media
 * Boton B - Posicion alta
 * Boton X - Restablecer Elevador y Stretch y Brazo
 * Boton Y - Activar Balanceo 
 * Left Bumper - Cierra Garra
 * Right Bumper - Abre Garra
 * Manual del Stretch
 * Left Trigger - Mete
 * Right Trigger - Saca
 */