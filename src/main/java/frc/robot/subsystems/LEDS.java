package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class LEDS extends SubsystemBase {
  DigitalOutput pin1 = new DigitalOutput(0);
  DigitalOutput pin2 = new DigitalOutput(1);
  boolean[] outputs = new boolean[2];
  public int state;
  String State = "";
  public boolean latch = true;

  public LEDS() {
    state = 1;
  }

  @Override
  public void periodic() {
    SmartDashboard.putString("State", State);
    states();
  }

  public void states() {
    state = latch?2:3;
    switch (state){
      case 1:
      outputs[0] = false;
      outputs[1] = false;
      State = "Cone";
      break;
      case 2:
      outputs[0] = true;
      outputs[1] = false;
      State = "Has Cone";
      break;
      case 3:
      outputs[0] = false;
      outputs[1] = true;
      State = "Cube";
      break;
      case 4:
      outputs[0] = true;
      outputs[1] = true;
      State = "Has Cube";
      break;
    }
    pin1.set(outputs[0]);
    pin2.set(outputs[1]);
    
  }
  public void toggle() {
     latch = !latch;   
  }
  public void CuneOrCube (){

  }
}
