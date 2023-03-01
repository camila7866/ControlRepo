package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Piston extends SubsystemBase {
  DoubleSolenoid piston = new DoubleSolenoid(61 ,PneumaticsModuleType.REVPH, 12, 15);
  public Piston() {
    piston.set(Value.kForward); 
  }

  public void toggleSolenoid (){
    piston.toggle();
  }

  public void setReverse (){
    piston.set(Value.kReverse);
  }

  public void setForward(){
    piston.set(Value.kForward);
  }

  @Override
  public void periodic() {
  }
}
