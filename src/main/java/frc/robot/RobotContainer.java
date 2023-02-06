package frc.robot;

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
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RamseteCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

public class RobotContainer {
  private final Drive m_Drive = new Drive();
  private final TeleopDrive m_TeleopDrive = new TeleopDrive(m_Drive, false);
  public static CommandXboxController Control0 = new CommandXboxController(0);
  public static CommandXboxController Control1 = new CommandXboxController(1);

  public RobotContainer() {
    m_Drive.setDefaultCommand(m_TeleopDrive);
    configureBindings();
  }

  private void configureBindings() {
    Control0.b().toggleOnTrue(new RestartHeading(m_Drive, 0));
    Control0.y().toggleOnTrue(new RestartHeading(m_Drive, 90));
    Control0.x().toggleOnTrue(new RestartHeading(m_Drive, 180));
    Control0.a().toggleOnTrue(new RestartHeading(m_Drive, 270));
  }

  public Command getAutonomousCommand() {
    DifferentialDriveVoltageConstraint auVoltageConstraint = new DifferentialDriveVoltageConstraint(
      new SimpleMotorFeedforward(Constants.ksVolts, Constants.kvVoltSecondsPerMeter, Constants.kaVoltSecondsSquaredPerMeter), 
      m_Drive.differentialDriveKinematics, 
      10);
    TrajectoryConfig trajectoryConfig = new TrajectoryConfig(
      Constants.kMaxSpeedMetersPerSecond, 
      Constants.kMaxAccelerationMetersPerSecondSquared).setKinematics(m_Drive.differentialDriveKinematics).addConstraint(auVoltageConstraint);
    Trajectory exampleTrajectory = TrajectoryGenerator.generateTrajectory(
      new Pose2d(0, 0, new Rotation2d(0)),
      // Pass through these two interior waypoints, making an 's' curve path
      List.of(new Translation2d(1, 1), new Translation2d(2, -1)),
      // End 3 meters straight ahead of where we started, facing forward
      new Pose2d(3, 0, new Rotation2d(0)),
      // Pass config
      trajectoryConfig);
    RamseteCommand ramseteCommand =
      new RamseteCommand(
          exampleTrajectory,
          m_Drive::getPose,
          new RamseteController(Constants.kRamseteB, Constants.kRamseteZeta),
          new SimpleMotorFeedforward(
            Constants.ksVolts,
            Constants.kvVoltSecondsPerMeter,
            Constants.kaVoltSecondsSquaredPerMeter),
          m_Drive.differentialDriveKinematics,
          m_Drive::getWheelSpeeds,
          new PIDController(Constants.kPDriveVel, 0, 0),
          new PIDController(Constants.kPDriveVel, 0, 0),
          // RamseteCommand passes volts to the callback
          m_Drive::tankDriveVolts,
          m_Drive);
    m_Drive.resetOdometry(exampleTrajectory.getInitialPose());
    return (ramseteCommand.andThen(() -> m_Drive.tankDriveVolts(0, 0)));
  }
}
