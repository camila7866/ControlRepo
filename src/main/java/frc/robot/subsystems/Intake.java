package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.SparkMaxAnalogSensor;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMax.SoftLimitDirection;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.SparkMaxAnalogSensor.Mode;

import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Intake extends SubsystemBase {
  private final CANSparkMax intake = new CANSparkMax(7, MotorType.kBrushless);
  public SparkMaxAnalogSensor sensor = intake.getAnalog(Mode.kRelative);
  private final DigitalOutput pin1 = new DigitalOutput(0);
  private final DigitalOutput pin2 = new DigitalOutput(1);
  private boolean[] outputs = new boolean[2];
  private String State = "";
  public boolean latch = true;
  public int state = 0;
  public Intake() {
    intake.setIdleMode(IdleMode.kBrake);
    intake.enableSoftLimit(SoftLimitDirection.kForward, false);
    intake.enableSoftLimit(SoftLimitDirection.kReverse, false);

    SmartDashboard.putString("State", State);
    states();
  }

  @Override
  public void periodic() {
    SmartDashboard.putString("State", State);
    states();
  }

  public void states() {
    state = latch?3:0;
    if (state == 0 && getSensorState()){
      state = 1;
    }
    if (state == 3 && getSensorState()){
      state = 2;
    }
        
    switch (state){
      case 0:
      State = "Cone";
      outputs[0] = false;
      outputs[1] = false;
      break;
      case 1:
      State = "Has Cone";
      outputs[0] = false;
      outputs[1] = true;
      break;
      case 2:
      outputs[0] = true;
      outputs[1] = false;
      State = "Has Cube";
      break;
      case 3:
      State = "Cube";
      outputs[0] = true;
      outputs[1] = true;
      break;
      default:
      State = "Cone";
      outputs[0] = false;
      outputs[1] = false;
      break;
    }
    pin1.set(outputs[0]);
    pin2.set(outputs[1]);  
  }

  public void toggle() {
    latch = !latch;
  }

  public void setPowerIntake (double vel){
    intake.set(vel);
  }

  public boolean getSensorState (){
    boolean value = false;
    if (sensor.getPosition() == 0){
      value = true;
    }
    return (value);
  }

}
