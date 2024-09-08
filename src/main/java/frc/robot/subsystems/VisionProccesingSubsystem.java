package frc.robot.subsystems;

import java.util.List;

import org.photonvision.PhotonCamera;
import org.photonvision.simulation.PhotonCameraSim;
import org.photonvision.simulation.SimCameraProperties;
import org.photonvision.simulation.VisionSystemSim;
import org.photonvision.targeting.PhotonPipelineResult;
import org.photonvision.targeting.PhotonTrackedTarget;

import edu.wpi.first.apriltag.AprilTagFieldLayout;
import edu.wpi.first.apriltag.AprilTagFields;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.VisionProccesingConstants;

public class VisionProccesingSubsystem extends SubsystemBase {
  
  DrivetrainSubsystem ds_DrivetrainSubsystem;

  VisionSystemSim vss_VisionSystemSim = new VisionSystemSim("PhotonVisionProccesingSystem");

  AprilTagFieldLayout atfl_AprilTagFieldLayout;

  SimCameraProperties scp_SimCameraProperties = new SimCameraProperties();

  PhotonCamera pc_PhotonCamera = new PhotonCamera("PhotonCamera");

  PhotonCameraSim pcs_PhotonCameraSim = new PhotonCameraSim(pc_PhotonCamera, scp_SimCameraProperties);

  PhotonPipelineResult ppr_PhotonPipelineResult;  
  
  List<PhotonTrackedTarget> lptt_PhotonTrackedTargets;

  boolean b_HasTargets = false;

  public VisionProccesingSubsystem(DrivetrainSubsystem drivetrainSubsystem) {
    this.ds_DrivetrainSubsystem = drivetrainSubsystem;

    try {atfl_AprilTagFieldLayout = AprilTagFieldLayout.loadFromResource(AprilTagFields.k2024Crescendo.m_resourceFile);} catch(Exception e){}

    vss_VisionSystemSim.addAprilTags(atfl_AprilTagFieldLayout);

    vss_VisionSystemSim.addCamera(pcs_PhotonCameraSim, VisionProccesingConstants.c_CameraTransform);

    pcs_PhotonCameraSim.enableDrawWireframe(true);
  }

  @Override
  public void periodic() {
    ppr_PhotonPipelineResult = pc_PhotonCamera.getLatestResult();

    lptt_PhotonTrackedTargets = ppr_PhotonPipelineResult.getTargets();

    b_HasTargets = ppr_PhotonPipelineResult.hasTargets();

    SmartDashboard.putBoolean("Is SeeingApriltagID:7", isSeeingApriltag(7));
  }

  @Override
  public void simulationPeriodic() {
    vss_VisionSystemSim.update(ds_DrivetrainSubsystem.getCurrentPose());
  }

  public PhotonTrackedTarget getTarget(int id){
    if(b_HasTargets){
      for(PhotonTrackedTarget target : lptt_PhotonTrackedTargets){
        if(target.getFiducialId() == id){
          return target;
        }
      }
    }
    return null;
  }

  public boolean isSeeingApriltag(int id){
    if(getTarget(id) != null){
      return true;
    }
    return false;
  }

  public double getApriltagYaw(int id){
    if(getTarget(id) != null){
      return getTarget(id).getYaw();
    }
    return 0;
  }
}
