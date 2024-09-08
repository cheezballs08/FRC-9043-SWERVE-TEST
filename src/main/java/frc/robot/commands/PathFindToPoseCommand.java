package frc.robot.commands;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.path.PathConstraints;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;;

public class PathFindToPoseCommand extends SequentialCommandGroup {

  public PathFindToPoseCommand(Pose2d pose, PathConstraints pathConstraints, double goalEndVelocity, double rotationDelayDistance) {
    addCommands(
      AutoBuilder.pathfindToPose(pose, pathConstraints, goalEndVelocity, rotationDelayDistance)
    );
  }
}
