// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import static edu.wpi.first.units.Units.*;

import java.util.jar.Attributes.Name;


import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

import frc.robot.subsystems.SampleMotorSubsystem;

public class RobotContainer {
    private final CommandXboxController joystick = new CommandXboxController(0);
    private static final double XBOX_DEADBAND = 0.1;


    //Create Subsystems
    public final SampleMotorSubsystem sampleMotorSubsystem = new SampleMotorSubsystem();

    public RobotContainer() {
        //register the named commands for auto
        registerCommands();
        configureBindings();
    }

    private void configureBindings() {
        //Elevator Testing Controls
         joystick.a().onTrue(sampleMotorSubsystem.intakeUntilStalledCommand());
    }

    private void registerCommands(){}
}