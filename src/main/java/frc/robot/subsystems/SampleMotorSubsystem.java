package frc.robot.subsystems;

import java.util.function.DoubleSupplier;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class SampleMotorSubsystem extends SubsystemBase {
    private SparkMax motor;
    
    private double maxPower = 1.0;
    private DoubleSupplier powerSupplier;

    public SampleMotorSubsystem(DoubleSupplier powerSupplier) {
        this.powerSupplier = powerSupplier;
        motor = new SparkMax(3, MotorType.kBrushless);
    }

    public void setMotorPower(double motorPower) {
        motor.set(motorPower);
    }

    @Override
    public void periodic() {
        motor.set(powerSupplier.getAsDouble() * maxPower);
    }
}
