package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.DrivetrainSubsystem;
import frc.robot.subsystems.VisionProccesingSubsystem;

public class TurnToApriltagCommand extends Command {

  DrivetrainSubsystem ds_DrivetrainSubsystem;
  VisionProccesingSubsystem vps_VisionProccesingSubsystem;

  int i_ApriltagID;

  public TurnToApriltagCommand(DrivetrainSubsystem drivetrainSubsystem, VisionProccesingSubsystem visionProccesingSubsystem, int apriltagID) {
    this.ds_DrivetrainSubsystem = drivetrainSubsystem;
    this.vps_VisionProccesingSubsystem = visionProccesingSubsystem;
    this.i_ApriltagID = apriltagID;

    addRequirements(ds_DrivetrainSubsystem);
    addRequirements(vps_VisionProccesingSubsystem);
  }

  @Override
  public void initialize() {
    ds_DrivetrainSubsystem.swerveDrive(0, 0, 0);
  }

  @Override
  public void execute() {
    if(vps_VisionProccesingSubsystem.isSeeingApriltag(i_ApriltagID)){
      ds_DrivetrainSubsystem.swerveDrive(0, 0, -Math.toRadians(vps_VisionProccesingSubsystem.getApriltagYaw(i_ApriltagID))*15);
    }
  }

  @Override
  public void end(boolean interrupted) {
    ds_DrivetrainSubsystem.swerveDrive(0, 0, 0);
  }

  @Override
  public boolean isFinished() {
    if(Math.abs(vps_VisionProccesingSubsystem.getApriltagYaw(i_ApriltagID)) < 2){
      return true;
    }
    return false;
  }
}
