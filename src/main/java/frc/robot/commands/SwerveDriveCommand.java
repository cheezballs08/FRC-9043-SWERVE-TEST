package frc.robot.commands;

import java.util.function.Supplier;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.DrivetrainSubsystem;

public class SwerveDriveCommand extends Command {

  DrivetrainSubsystem ds_DrivetrainSubsystem;

  Supplier<Double> f_XSpeedFunction, f_YSpeedFunction, f_RSpeedFunction;

  public SwerveDriveCommand(DrivetrainSubsystem drivetrainSubsystem, Supplier<Double> xSpeedFunction, Supplier<Double> ySpeedFunction, Supplier<Double> rSpeedFunction){
    this.ds_DrivetrainSubsystem = drivetrainSubsystem;
    this.f_XSpeedFunction = xSpeedFunction;
    this.f_YSpeedFunction = ySpeedFunction;
    this.f_RSpeedFunction = rSpeedFunction;
    addRequirements(ds_DrivetrainSubsystem);
  }

  @Override
  public void initialize() {}

  @Override
  public void execute() {
    double d_XSpeed = -f_XSpeedFunction.get()*3.7;
    double d_YSpeed = -f_YSpeedFunction.get()*3.7;
    double d_RSpeed = -f_RSpeedFunction.get()*6;

    ds_DrivetrainSubsystem.swerveDrive(d_XSpeed, d_YSpeed, d_RSpeed);
  }

  @Override
  public void end(boolean interrupted) {}

  @Override
  public boolean isFinished() {
    return false;
  }
}
