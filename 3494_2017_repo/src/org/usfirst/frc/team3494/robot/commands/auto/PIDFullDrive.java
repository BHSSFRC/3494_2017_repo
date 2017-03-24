package org.usfirst.frc.team3494.robot.commands.auto;

import org.usfirst.frc.team3494.robot.Robot;
import org.usfirst.frc.team3494.robot.UnitTypes;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Drives straight/forward with an angle (WIP) using the drivetrain's PID loop.
 * Only works in inches.
 */
public class PIDFullDrive extends Command {

	private double distance;
	private double angle;

	/**
	 * Constructor.
	 * 
	 * @param dist
	 *            The distance to drive, in inches.
	 * @param angle
	 *            The angle to turn to.
	 */
	public PIDFullDrive(double dist, double angle) {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		requires(Robot.driveTrain);
		this.distance = dist;
	}

	/**
	 * Other constructor. Uses default angle of 0 degrees.
	 * 
	 * @param dist
	 *            The distance to drive in inches.
	 */
	public PIDFullDrive(double dist) {
		this(dist, 0);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		Robot.ahrs.reset();
		Robot.driveTrain.enable();
		Robot.driveTrain.setSetpoint(this.angle);
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		SmartDashboard.putNumber("angle", Robot.ahrs.getAngle());
		System.out.println(Robot.ahrs.getYaw());
		// System.out.println(Robot.driveTrain.PIDTune);
		Robot.driveTrain.ArcadeDrive(0.4, -Robot.driveTrain.PIDTune, true);
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		// return false;
		return Math.abs(Robot.driveTrain.getAvgDistance(UnitTypes.INCHES)) >= Math.abs(this.distance);
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		Robot.driveTrain.disable();
		Robot.driveTrain.stopAll();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
		this.end();
	}
}
