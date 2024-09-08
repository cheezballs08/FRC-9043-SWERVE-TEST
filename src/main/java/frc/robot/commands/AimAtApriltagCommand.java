// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.DrivetrainSubsystem;
import frc.robot.subsystems.VisionProccesingSubsystem;

public class AimAtApriltagCommand extends Command {

  VisionProccesingSubsystem vps_VisionProccesingSubsystem;
  DrivetrainSubsystem ds_DrivetrainSubsystem;

  Supplier<Double> f_XSpeedFunction, f_YSpeedFunction, f_RSpeedFunction;

  int i_ApriltagID;

  boolean b_isFieldRelative;

  public AimAtApriltagCommand(DrivetrainSubsystem drivetrainSubsystem, VisionProccesingSubsystem visionProccesingSubsystem, int apriltagID, Supplier<Double> xSpeedFunction, Supplier<Double> ySpeedFunction, Supplier<Double> rSpeedFunction, boolean isFieldRelative){
    this.f_XSpeedFunction = xSpeedFunction;
    this.f_YSpeedFunction = ySpeedFunction;
    this.f_RSpeedFunction = rSpeedFunction;
    this.i_ApriltagID = apriltagID;
    this.b_isFieldRelative = isFieldRelative;
    this.ds_DrivetrainSubsystem = drivetrainSubsystem;
    this.vps_VisionProccesingSubsystem = visionProccesingSubsystem;
    addRequirements(ds_DrivetrainSubsystem);
    addRequirements(vps_VisionProccesingSubsystem);
  }

  @Override
  public void initialize() {
    ds_DrivetrainSubsystem.swerveDrive(0, 0, 0);
  }

  @Override
  public void execute() {
    double d_XSpeed = -f_XSpeedFunction.get()*3.7;
    double d_YSpeed = -f_YSpeedFunction.get()*3.7;
    double d_RSpeed = -f_RSpeedFunction.get()*6;

    if(vps_VisionProccesingSubsystem.isSeeingApriltag(i_ApriltagID)){
      if(b_isFieldRelative){
        ds_DrivetrainSubsystem.fieldRelativeSwerveDrive(d_XSpeed, d_YSpeed, -Math.toRadians(vps_VisionProccesingSubsystem.getApriltagYaw(i_ApriltagID))*10);
      }
      else{
        ds_DrivetrainSubsystem.swerveDrive(d_XSpeed, d_YSpeed, -Math.toRadians(vps_VisionProccesingSubsystem.getApriltagYaw(i_ApriltagID))*10);
      }
    }
    else{
      if(b_isFieldRelative){
        ds_DrivetrainSubsystem.fieldRelativeSwerveDrive(d_XSpeed, d_YSpeed, d_RSpeed);
      }
      else{
        ds_DrivetrainSubsystem.swerveDrive(d_XSpeed, d_YSpeed, d_RSpeed);
      }
    }
  }

  @Override
  public void end(boolean interrupted) {}

  @Override
  public boolean isFinished() {
    return false;
  }
}
