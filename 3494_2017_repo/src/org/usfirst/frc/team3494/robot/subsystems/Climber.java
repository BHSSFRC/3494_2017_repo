package org.usfirst.frc.team3494.robot.subsystems;

import org.usfirst.frc.team3494.robot.DriveDirections;
import org.usfirst.frc.team3494.robot.Robot;
import org.usfirst.frc.team3494.robot.RobotMap;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Climber subsystem. Contains methods for controlling the rope climber.
 * 
 * @since 0.0.0
 */
public class Climber extends Subsystem implements IMotorizedSubsystem {

	private CANTalon motor;
	private boolean driveTrainMode;
	private DoubleSolenoid pto; // zarya named it

	// Put methods for controlling this subsystem
	// here. Call these from Commands.
	public Climber() {
		super("Climber");
		this.motor = new CANTalon(RobotMap.CLIMBER_MOTOR);
		this.motor.setInverted(true);
		this.pto = new DoubleSolenoid(RobotMap.CLIMBER_PTO_FORWARD, RobotMap.CLIMBER_PTO_BACKARD);
		this.pto.set(Value.kForward);
		this.driveTrainMode = false;
	}

	@Override
	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
	}

	/**
	 * Climbs in the specified direction.
	 * 
	 * @param dir
	 *            The direction to climb. Setting this to anything other than
	 *            {@link DriveDirections#UP} or {@link DriveDirections#DOWN}
	 *            will stop the climber.
	 */
	public void climb(DriveDirections dir) {
		if (dir.equals(DriveDirections.UP) && !this.driveTrainMode) {
			this.motor.set(0.35);
		} else if (dir.equals(DriveDirections.DOWN) && !this.driveTrainMode) {
			this.motor.set(-0.35);
		} else {
			// stop the climber
			this.motor.set(0);
		}
	}

	@Override
	public void stopAll() {
		this.motor.set(0);
	}

	@Override
	public void setAll(double speed) {
		this.motor.set(speed);
	}

	/**
	 * Engages or disengages the drivetrain from the climber. Note that with the
	 * drivetrain engaged controlling the climber by this subsystem becomes
	 * impossible (you must use {@link Drivetrain} instead.)
	 * 
	 * @see Drivetrain
	 * @param value
	 *            The state to set the PTO piston in.
	 */
	public void setPTO(Value value) {
		this.pto.set(value);
		if (value.equals(Value.kOff) || value.equals(Value.kReverse)) {
			this.driveTrainMode = true;
		} else {
			this.driveTrainMode = false;
		}
	}

	public void disengagePTO() {
		this.setPTO(Value.kForward);
	}

	public void engagePTO() {
		this.setPTO(Value.kReverse);
	}

	public boolean getState() {
		return this.driveTrainMode;
	}

	public double getMotorCurrent() {
		return Robot.pdp.getCurrent(RobotMap.CLIMBER_MOTOR_PDP);
	}
}
