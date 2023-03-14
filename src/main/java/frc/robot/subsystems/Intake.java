package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Intake extends SubsystemBase {
  private final CANSparkMax intake = new CANSparkMax(7, MotorType.kBrushless);
  private final DigitalOutput pin1 = new DigitalOutput(0);
  private final DigitalOutput pin2 = new DigitalOutput(1);
  private final DigitalInput sensor = new DigitalInput(2);
  private boolean[] outputs = new boolean[2];
  private String State = "";
  public boolean latch = true;
  public int state = 0;
  public Intake() {
    intake.setIdleMode(IdleMode.kBrake);
    SmartDashboard.putString("State", State);
    states();
  }

  @Override
  public void periodic() {
    SmartDashboard.putString("State", State);
    states();
  }

  public void states() {
    state = latch?0:3;
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
    return (!sensor.get());
  }

}
