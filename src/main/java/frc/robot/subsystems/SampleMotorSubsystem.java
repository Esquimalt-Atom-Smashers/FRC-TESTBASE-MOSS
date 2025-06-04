package frc.robot.subsystems;

import java.util.function.DoubleSupplier;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.ClosedLoopConfig.FeedbackSensor;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.motorcontrol.MotorController;
import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class SampleMotorSubsystem extends SubsystemBase {
    private SparkMax motor = new SparkMax(3, MotorType.kBrushless);
    ;
    
    private double maxPower = 1.0;
//    private DoubleSupplier powerSupplier;
    private SparkMaxConfig motorConfig = new SparkMaxConfig();
    private SparkClosedLoopController motorController = motor.getClosedLoopController();


    private static final double FIRST_POS = 0;
    private static final double SECOND_POS = 200;
    private static final double INTAKE_VOLTAGE = 10;

    private Timer timer = new Timer();


    public SampleMotorSubsystem() {
        //this.powerSupplier = powerSupplier;
        motorConfig.smartCurrentLimit(1,3,200);
        motorConfig.closedLoop.feedbackSensor(FeedbackSensor.kPrimaryEncoder)
        .p(0.1).i(0.000).d(0.01).maxOutput(0.3).minOutput(-0.2);

        motor.configure(motorConfig, ResetMode.kResetSafeParameters, PersistMode.kNoPersistParameters);
        motorController.setReference(FIRST_POS, SparkMax.ControlType.kPosition);  
    }

    public void setMotorPower(double motorPower) {
        motor.set(motorPower);
    }

    @Override
    public void periodic() {
        if(timer.hasElapsed(1.0)) {
            System.out.println("motor pos"+ motor.getEncoder().getPosition());
            timer.reset();
          }
    }

    private void intake(){
        motorController.setReference(INTAKE_VOLTAGE, ControlType.kVoltage);
      }

    private void stop(){
        motorController.setReference(0, ControlType.kVoltage);
      }

    public Command intakeUntilStalledCommand() {
        return Commands.runOnce(() -> {intake();}, this)
        .andThen(Commands.waitUntil(() -> {//wait for motor to spin up
            System.out.println("Intake Velocity = "+ motor.getEncoder().getVelocity());
            return motor.getEncoder().getVelocity()<=-20;}))
        .andThen(Commands.waitUntil(() -> {//wiat for the algae ball to stop the motor
            if (motor.getEncoder().getVelocity()>=-2){
            stop();
            return true;
            } else {
            return false;
            }}))
        .withName("stalled");
    }
}
