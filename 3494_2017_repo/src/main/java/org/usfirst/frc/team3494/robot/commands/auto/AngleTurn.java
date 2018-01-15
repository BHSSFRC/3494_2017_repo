package org.usfirst.frc.team3494.robot.commands.auto;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team3494.robot.Robot;

/**
 * Turns the robot using the gyro board mounted to the RoboRIO. The angle to
 * turn by must be specified in the constructor. Angle tolerance is specified by
 * Robot.prefs (key {@code angle tolerance}.)
 *
 * @see org.usfirst.frc.team3494.robot.Robot
 * @see org.usfirst.frc.team3494.robot.subsystems.Drivetrain
 * @since 0.0.2
 */
public class AngleTurn extends Command {

    private double angle;
    private static double tolerance;

    /**
     * Constructor.
     *
     * @param angle The number of degrees to turn.
     */
    public AngleTurn(double angle) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        requires(Robot.driveTrain);
        this.angle = angle;
        try {
            tolerance = Robot.prefs.getDouble("angle tolerance", 2.5);
        } catch (NullPointerException e) {
            tolerance = 2.5;
        }
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        Robot.ahrs.reset();
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        if (!(Robot.ahrs.getAngle() > angle - tolerance && Robot.ahrs.getAngle() < angle + tolerance)) {
            if (angle > 0) {
                Robot.driveTrain.adjustedTankDrive(-0.4, 0.4);
                return;
            } else if (angle < 0) {
                Robot.driveTrain.adjustedTankDrive(0.4, -0.4);
                return;
            } else {
                Robot.driveTrain.adjustedTankDrive(0.4, 0.4);
            }
        }
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return Robot.ahrs.getAngle() > angle - tolerance && Robot.ahrs.getAngle() < angle + tolerance;
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
        Robot.driveTrain.resetRight();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
    }
}
