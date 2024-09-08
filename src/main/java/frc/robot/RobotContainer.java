package frc.robot;

import java.io.File;
import com.pathplanner.lib.auto.AutoBuilder;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.Constants.ControllerConstants;
import frc.robot.commands.AimAtApriltagCommand;
import frc.robot.commands.FieldRelativeSwerveDriveCommand;
import frc.robot.commands.SwerveDriveCommand;
import frc.robot.commands.TurnToApriltagCommand;
import frc.robot.subsystems.DrivetrainSubsystem;
import frc.robot.subsystems.VisionProccesingSubsystem;

public class RobotContainer {

  CommandXboxController cxc_CommandXboxController = new CommandXboxController(ControllerConstants.c_ControllerID);

  Trigger t_XButtonTrigger = cxc_CommandXboxController.x();
  Trigger t_AButtonTrigger = cxc_CommandXboxController.a();
  Trigger t_BButtonTrigger = cxc_CommandXboxController.b();
  Trigger t_YButtonTrigger = cxc_CommandXboxController.y();

  File fi_SwerveJSONDirectory = new File(Filesystem.getDeployDirectory(), "swerve");

  DrivetrainSubsystem ds_DrivetrainSubsystem = new DrivetrainSubsystem(fi_SwerveJSONDirectory);
  VisionProccesingSubsystem vps_VisionProccesingSubsystem = new VisionProccesingSubsystem(ds_DrivetrainSubsystem);

  SwerveDriveCommand sdc_SwerveDriveCommand = new SwerveDriveCommand(ds_DrivetrainSubsystem, cxc_CommandXboxController::getLeftY, cxc_CommandXboxController::getLeftX, cxc_CommandXboxController::getRightX);
  FieldRelativeSwerveDriveCommand frsdc_FieldRelativeSwerveDriveCommand = new FieldRelativeSwerveDriveCommand(ds_DrivetrainSubsystem, cxc_CommandXboxController::getLeftY, cxc_CommandXboxController::getLeftX, cxc_CommandXboxController::getRightX);
  AimAtApriltagCommand aaac_AimAtApriltag7Command = new AimAtApriltagCommand(ds_DrivetrainSubsystem, vps_VisionProccesingSubsystem, 7, cxc_CommandXboxController::getLeftY, cxc_CommandXboxController::getLeftX, cxc_CommandXboxController::getRightX, true);
  TurnToApriltagCommand ttac_TurnToApriltag7Command = new TurnToApriltagCommand(ds_DrivetrainSubsystem, vps_VisionProccesingSubsystem, 7);

  
  public RobotContainer() {
    configureBindings();
  }

  private void configureBindings() {
    ds_DrivetrainSubsystem.setDefaultCommand(sdc_SwerveDriveCommand);
    t_AButtonTrigger.toggleOnTrue(frsdc_FieldRelativeSwerveDriveCommand);
    t_YButtonTrigger.onTrue(aaac_AimAtApriltag7Command);
    t_XButtonTrigger.onTrue(ttac_TurnToApriltag7Command);
  }

  public Command getAutonomousCommand() {
    return Commands.sequence(
    AutoBuilder.buildAuto("TestAuto"),
    ttac_TurnToApriltag7Command
    );
  }
}
