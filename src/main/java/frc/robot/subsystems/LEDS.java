package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class LEDS extends SubsystemBase {
  DigitalOutput pin1 = new DigitalOutput(0);
  DigitalOutput pin2 = new DigitalOutput(1);
  boolean[] outputs = new boolean[2];
  public int state = 3;
  String State = "";
  public boolean latch = true;

  public LEDS() {
  }

  @Override
  public void periodic() {
    SmartDashboard.putString("State", State);
    states();
  }

  public void states() {
    state = latch?1:3;
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
      State = "Cube";
      break;
      case 3:
      State = "Has Cube";
      outputs[0] = true;
      outputs[1] = true;
      break;
      default:
        State = "Cone Def";
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
  public void CuneOrCube (){

  }
}
