package frc.robot;

import frc.robot.commands.ArmCommand;
import frc.robot.commands.AutoMotionMagic;
import frc.robot.commands.Calibrating;
import frc.robot.commands.ChangeState;
import frc.robot.commands.DriveGoToAngle;
import frc.robot.commands.ElevatorCommand;
import frc.robot.commands.IntakeCommand;
import frc.robot.commands.IntakeCommandAuto;
import frc.robot.commands.IntakeForCone;
import frc.robot.commands.OnlyVelDrive;
import frc.robot.commands.PosArmDoubleSub;
import frc.robot.commands.PosArmSingle;
import frc.robot.commands.PosGoalArm;
import frc.robot.commands.PosGoalElevator;
import frc.robot.commands.PosGoalStretch;
import frc.robot.commands.RestartArm;
import frc.robot.commands.RestartElevator;
import frc.robot.commands.RestartStretch;
import frc.robot.commands.StretchCommand;
import frc.robot.commands.TeleopDrive;
import frc.robot.commands.VelDriveWithBalance;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.Stretch;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

public class RobotContainer {
  public static CommandXboxController Control0 = new CommandXboxController(0);
  public static CommandXboxController Control1 = new CommandXboxController(1);

  SendableChooser<Command> m_chooser_zone = new SendableChooser<>();

  private final Limelight m_Limelight = new Limelight();

  private final Drive m_Drive = new Drive();
    private final TeleopDrive m_TeleopDrive = new TeleopDrive(m_Drive, false);

    public final Elevator m_Elevator = new Elevator();
    private final ElevatorCommand m_ElevatorCommand = new ElevatorCommand(m_Elevator, true, 0);

    public final Stretch m_Stretch = new Stretch();
  private final StretchCommand m_StretchCommand = new StretchCommand(m_Stretch, true, 0);

  private final Intake m_Intake = new Intake();
  private final IntakeCommand m_IntakeCommand = new IntakeCommand(m_Intake);

  public final Arm m_Arm = new Arm();
  private final ArmCommand m_ArmCommand = new ArmCommand(m_Arm, true, 0);

  private final SequentialCommandGroup AutoMiddle = new SequentialCommandGroup(
    new ParallelCommandGroup(
      new ElevatorCommand(m_Elevator, false, 90),
      new StretchCommand(m_Stretch, false, -150),
      new ArmCommand(m_Arm, false, -25)
    ),
    new ParallelDeadlineGroup(
      new WaitCommand(1),
      new IntakeForCone(m_Intake, 0.5)
    ),
    new ParallelCommandGroup(
      new RestartElevator(m_Elevator),
      new RestartStretch(m_Stretch),
      new RestartArm(m_Arm)
    ),
    new DriveGoToAngle(m_Drive, -90), 
    new DriveGoToAngle(m_Drive, -180),
    new AutoMotionMagic(m_Drive, 2.587, 4600, 4500)
  );

  private final SequentialCommandGroup AutoMiddle2 = new SequentialCommandGroup(
    new ParallelCommandGroup(
      new ElevatorCommand(m_Elevator, false, 90),
      new StretchCommand(m_Stretch, false, -170),
      new ArmCommand(m_Arm, false, -30)
    ),
    new ParallelDeadlineGroup(
      new WaitCommand(1),
      new IntakeForCone(m_Intake, 0.5)
    ),
    new ParallelCommandGroup(
      new AutoMotionMagic(m_Drive, -0.5, 4600, 4500),
      new RestartElevator(m_Elevator),
      new RestartStretch(m_Stretch),
      new RestartArm(m_Arm)
    ),
    new DriveGoToAngle(m_Drive, -90),
    new DriveGoToAngle(m_Drive, -179),
    new AutoMotionMagic(m_Drive, 3, 5000, 4500),
    new DriveGoToAngle(m_Drive, 90),
    new DriveGoToAngle(m_Drive, 179),
    new AutoMotionMagic(m_Drive, 2.25, 5000, 4500)
  );

  private final SequentialCommandGroup AutoSideRedAlliance = new SequentialCommandGroup(
    new ParallelCommandGroup(
      new ElevatorCommand(m_Elevator, false, 90),
      new StretchCommand(m_Stretch, false, -145),
      new ArmCommand(m_Arm, false, -60)
    ),
    new ParallelDeadlineGroup(new WaitCommand(1), new IntakeForCone(m_Intake, -0.5)),
    new ParallelCommandGroup(
      new AutoMotionMagic(m_Drive, -5, 4600, 4500), 
      new RestartStretch(m_Stretch),
      new RestartElevator(m_Elevator),
      new RestartArm(m_Arm)
    ),
    new DriveGoToAngle(m_Drive, 90),
    new DriveGoToAngle(m_Drive, 180),
    new ParallelDeadlineGroup( 
      new IntakeCommandAuto(m_Intake, -0.5, true),
      new SequentialCommandGroup(
        new ArmCommand(m_Arm, false, -57),
        new StretchCommand(m_Stretch, false, -250)
      )
    ),
    new ParallelCommandGroup(
      new RestartStretch(m_Stretch),
      new RestartArm(m_Arm)
    ),
    new DriveGoToAngle(m_Drive, 90),
    new DriveGoToAngle(m_Drive, 0),
    new AutoMotionMagic(m_Drive, 5, 4500, 4600),
    new IntakeCommandAuto(m_Intake, -0.2, false)
  );

  private final SequentialCommandGroup AutoSideBlueAlliance = new SequentialCommandGroup(
    new ParallelCommandGroup(
      new ElevatorCommand(m_Elevator, false, 90),
      new StretchCommand(m_Stretch, false, -145),
      new ArmCommand(m_Arm, false, -60)
    ),
    new ParallelDeadlineGroup(new WaitCommand(1), new IntakeForCone(m_Intake, -0.5)),
    new ParallelCommandGroup(
      new AutoMotionMagic(m_Drive, -5, 4600, 4500), 
      new RestartStretch(m_Stretch),
      new RestartElevator(m_Elevator),
      new RestartArm(m_Arm)
    ),
    new DriveGoToAngle(m_Drive, -90),
    new DriveGoToAngle(m_Drive, -180),
    new ParallelDeadlineGroup( 
      new IntakeCommandAuto(m_Intake, -0.5, true),
      new SequentialCommandGroup(
        new ArmCommand(m_Arm, false, -57),
        new StretchCommand(m_Stretch, false, -250)
      )
    ),
    new ParallelCommandGroup(
      new RestartStretch(m_Stretch),
      new RestartArm(m_Arm)
    ),
    new DriveGoToAngle(m_Drive, -90),
    new DriveGoToAngle(m_Drive, 0),
    new AutoMotionMagic(m_Drive, 5, 4500, 4600),
    new IntakeCommandAuto(m_Intake, -0.2, false)
  );

  public final ParallelCommandGroup restartAll = new ParallelCommandGroup(
    new RestartElevator(m_Elevator),
    new RestartStretch(m_Stretch),
    new RestartArm(m_Arm)
  );

  private final ParallelCommandGroup cuboSuelo = new ParallelCommandGroup(
    new ElevatorCommand(m_Elevator, false, 0),
    new StretchCommand(m_Stretch, false, 0),
    new ArmCommand(m_Arm, false, -45)
  );

  private final ParallelCommandGroup conoSuelo = new ParallelCommandGroup(
    new ElevatorCommand(m_Elevator, false, 0),
    new StretchCommand(m_Stretch, false, -16),
    new ArmCommand(m_Arm, false, -60
    )
  );

  private final SequentialCommandGroup conoParado = new SequentialCommandGroup(
    new ElevatorCommand(m_Elevator, false, 55),
    new ParallelCommandGroup(
      new StretchCommand(m_Stretch, false, -16),
      new ArmCommand(m_Arm, false, -74)
    )
  );

  private final ParallelCommandGroup posSingleSub = new ParallelCommandGroup(
    new ElevatorCommand(m_Elevator, false, 0),
    new StretchCommand(m_Stretch, false, 0),
    new PosArmSingle(m_Arm)
  );

  private final ParallelCommandGroup posDoubleSub = new ParallelCommandGroup(
    new ElevatorCommand(m_Elevator, false, 103),
    new StretchCommand(m_Stretch, false, -20),
    new PosArmDoubleSub(m_Arm)
  );

  private final ParallelCommandGroup objectDown = new ParallelCommandGroup(
    new PosGoalElevator(m_Elevator, 0),
    new PosGoalArm(m_Arm, 0),
    new PosGoalStretch(m_Stretch, 0)
  );

  private final ParallelCommandGroup objectMiddle = new ParallelCommandGroup(
    new PosGoalElevator(m_Elevator, 1),
    new PosGoalArm(m_Arm, 1),
    new PosGoalStretch(m_Stretch, 1)
  );

  private final ParallelCommandGroup objectTall = new ParallelCommandGroup(
    new PosGoalElevator(m_Elevator, 2),
    new PosGoalArm(m_Arm, 2),
    new PosGoalStretch(m_Stretch, 2)
  );
  
  public Command getResetComm(){
    return restartAll;
  }

  public RobotContainer() {
    m_chooser_zone.setDefaultOption("Auto Middle", AutoMiddle);
    m_chooser_zone.addOption("Lateral Red Alliance", null);
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
    Control0.button(7).onTrue(new ChangeState(m_Intake));
    Control0.rightBumper().toggleOnTrue(new TeleopDrive(m_Drive, true));
    Control0.y().toggleOnTrue(new DriveGoToAngle(m_Drive, 0));
    Control0.x().toggleOnTrue(new DriveGoToAngle(m_Drive, -90));
    Control0.b().toggleOnTrue(new DriveGoToAngle(m_Drive, 90));
    Control0.povDown().toggleOnTrue(new ElevatorCommand(m_Elevator, false, 0));
    Control0.povRight().toggleOnTrue(new ElevatorCommand(m_Elevator, false, 50));
    Control0.povUp().toggleOnTrue(new ElevatorCommand(m_Elevator, false, 102));
    //Control 1 
    Control1 .button(6).onTrue(posDoubleSub);
    Control1.button(7).onTrue(new ChangeState(m_Intake));
    Control1.leftBumper().toggleOnTrue(restartAll);
    Control1.rightBumper().toggleOnTrue(posSingleSub);
    Control1.a().toggleOnTrue(cuboSuelo);
    Control1.b().toggleOnTrue(conoSuelo);
    Control1.x().toggleOnTrue(conoSuelo);
    Control1.y().toggleOnTrue(conoParado);
    Control1.povDown().toggleOnTrue(objectDown);
    Control1.povLeft().toggleOnTrue(objectMiddle);
    Control1.povRight().toggleOnTrue(objectMiddle);
    Control1.povUp().toggleOnTrue(objectTall);
  }

  public Command getAutonomousCommand() {
    return (m_chooser_zone.getSelected());
  }
}