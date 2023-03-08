package frc.robot.subsystems;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Limelight extends SubsystemBase {
  NetworkTable table_limelight = NetworkTableInstance.getDefault().getTable("limelight");
  NetworkTableEntry tx = table_limelight.getEntry("tx");
  NetworkTableEntry ty = table_limelight.getEntry("ty");
  NetworkTableEntry ts = table_limelight.getEntry("ts");
  NetworkTableEntry tid = table_limelight.getEntry("tid");
  public Limelight() {}

  @Override
  public void periodic() {
    SmartDashboard.putNumber("Tx", tx.getDouble(0.0));
    SmartDashboard.putNumber("Ty", ty.getDouble(0.0));
    SmartDashboard.putNumber("Ts", ts.getDouble(0.0));
    SmartDashboard.putNumber("Id AprilTag", tid.getDouble(-1));
  }
}
