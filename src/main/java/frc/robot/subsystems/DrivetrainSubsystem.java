package frc.robot.subsystems;

import java.io.File;
import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.util.HolonomicPathFollowerConfig;
import com.pathplanner.lib.util.PIDConstants;
import com.pathplanner.lib.util.ReplanningConfig;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.DrivetrainConstants;
import swervelib.SwerveDrive;
import swervelib.parser.SwerveParser;

public class DrivetrainSubsystem extends SubsystemBase {

  SwerveDrive sd_SwerveDrive;

  public DrivetrainSubsystem(File file) {
    try {sd_SwerveDrive = new SwerveParser(file).createSwerveDrive(DrivetrainConstants.c_MaxSpeed);} catch (Exception e) {}

    sd_SwerveDrive.resetOdometry(DrivetrainConstants.c_StartingPose);
    sd_SwerveDrive.setCosineCompensator(false);

    configurePathPlanner();
  }

  @Override
  public void periodic() {}

  public void configurePathPlanner(){
    HolonomicPathFollowerConfig hpfc_HolonomicPathFollowerConfig = new HolonomicPathFollowerConfig(
      new PIDConstants(DrivetrainConstants.c_SpeedP, DrivetrainConstants.c_SpeedI, DrivetrainConstants.c_SpeedD),
      new PIDConstants(DrivetrainConstants.c_AngleP, DrivetrainConstants.c_AngleI, DrivetrainConstants.c_AngleD),
      DrivetrainConstants.c_MaxSpeed,
      sd_SwerveDrive.swerveDriveConfiguration.getDriveBaseRadiusMeters(), 
      new ReplanningConfig());

    AutoBuilder.configureHolonomic(
      this::getCurrentPose,
      this::resetPose, 
      this::getRobotRelativeChassisSpeeds, 
      this::setChassisSpeeds, 
      hpfc_HolonomicPathFollowerConfig, 
      () -> {boolean b_toFlip = DriverStation.getAlliance().get() == DriverStation.Alliance.Red ? false : true; return b_toFlip;}, 
      this);
  }

  public void swerveDrive(double xSpeed, double ySpeed, double rSpeed){
    sd_SwerveDrive.drive(new Translation2d(xSpeed, ySpeed), rSpeed, false, true);
  }

  public void fieldRelativeSwerveDrive(double xSpeed, double ySpeed, double rSpeed){
    sd_SwerveDrive.drive(new Translation2d(xSpeed, ySpeed), rSpeed, true, true);
  }

  public Pose2d getCurrentPose(){
    return sd_SwerveDrive.getPose();
  }

  public void resetPose(Pose2d pose){
    sd_SwerveDrive.resetOdometry(pose);
  }

  public ChassisSpeeds getRobotRelativeChassisSpeeds() {
    return sd_SwerveDrive.getRobotVelocity();
  }

  public void setChassisSpeeds(ChassisSpeeds chassisSpeeds){
    sd_SwerveDrive.setChassisSpeeds(chassisSpeeds);
  }

  public void stopModules(){
    sd_SwerveDrive.drive(new ChassisSpeeds());
  }
}
