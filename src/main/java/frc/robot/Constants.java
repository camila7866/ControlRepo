package frc.robot;

public final class Constants {
  public static final double kTrackWidthMeters = 0.5;
  public static final double kDiamWheel = 0.1524;
  public static final double kPerWheel = Math.PI * kDiamWheel;
  public static final double cpr = 2048;
  public static final double ksVolts = 0.22;
    public static final double kvVoltSecondsPerMeter = 1.98;
    public static final double kaVoltSecondsSquaredPerMeter = 0.2;
    public static final double kMaxSpeedMetersPerSecond = 3;
    public static final double kMaxAccelerationMetersPerSecondSquared = 1;

    // Reasonable baseline values for a RAMSETE follower in units of meters and seconds
    public static final double kRamseteB = 2;
    public static final double kRamseteZeta = 0.7;
  public static final double kPDriveVel = 8.5;
} 
