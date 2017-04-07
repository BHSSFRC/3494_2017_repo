package org.usfirst.frc.team3494.robot.commands.climb;

import org.usfirst.frc.team3494.robot.Robot;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Toggles the climber from drivetrain to climb motor.
 */
public class ClimberPTOSetter extends Command {

	private boolean b;

	public ClimberPTOSetter(boolean engage) {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		requires(Robot.climber);
		requires(Robot.kompressor);
		requires(Robot.driveTrain);
		this.b = engage;
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		if (this.b) {
			Robot.climber.engagePTO();
			Robot.kompressor.compress.stop();
			for (CANTalon c : Robot.driveTrain.leftSide) {
				c.setCurrentLimit(35);
				c.EnableCurrentLimit(true);
			}
			for (CANTalon c : Robot.driveTrain.rightSide) {
				c.setCurrentLimit(35);
				c.EnableCurrentLimit(true);
			}
		} else {
			Robot.climber.disengagePTO();
			Robot.kompressor.compress.start();
		}
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return true;
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
	}
}
