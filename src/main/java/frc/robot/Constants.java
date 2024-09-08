package frc.robot;

import com.pathplanner.lib.path.PathConstraints;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.geometry.Translation3d;

public class Constants {

    public class DrivetrainConstants{
        public static final double c_MaxSpeed = 6.0;
        public static final double c_MaxAcceleration = 3.0;

        public static final double c_MaxAngularSpeed = 720;
        public static final double c_MaxAngularVelocity = 540;

        public static final double c_SpeedP = 0.0020645;
        public static final double c_SpeedI = 0;
        public static final double c_SpeedD = 0;

        public static final double c_AngleP = 0.01;
        public static final double c_AngleI = 0;
        public static final double c_AngleD = 0;

        public static final double c_StartingXPosition = 1.35;
        public static final double c_StartingYPosition = 5.55;
        public static final Rotation2d c_StartingRotation = Rotation2d.fromDegrees(0);
        public static final Pose2d c_StartingPose = new Pose2d(c_StartingXPosition, c_StartingYPosition, c_StartingRotation);

        public static final PathConstraints c_PathplannerPathConstraints = new PathConstraints(c_MaxSpeed, c_MaxAcceleration, c_MaxAngularSpeed, c_MaxAngularVelocity);
    }

    public class ControllerConstants{
        public static final int c_ControllerID = 0;
    }

    public class VisionProccesingConstants{
        public static final double c_CameraXPostion = 0.1;
        public static final double c_CameraYPostion = 0;
        public static final double c_CameraZPostion = 0.5;
        public static final Translation3d c_CameraPosition = new Translation3d(c_CameraXPostion, c_CameraYPostion, c_CameraZPostion);

        public static final double c_CameraRoll = 0;
        public static final double c_CameraPitch = Math.toRadians(-15);
        public static final double c_CameraYaw = 0;
        public static final Rotation3d c_CameraRotation = new Rotation3d(c_CameraRoll, c_CameraPitch, c_CameraYaw);

        public static final Transform3d c_CameraTransform = new Transform3d(c_CameraPosition, c_CameraRotation);

    }
    
}
