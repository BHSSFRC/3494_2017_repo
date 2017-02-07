package org.usfirst.frc.team3494.robot.subsystems;

import org.usfirst.frc.team3494.robot.RobotMap;
import org.usfirst.frc.team3494.robot.commands.drive.Drive;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Drivetrain subsystem. Contains all methods for controlling the robot's
 * drivetrain. Also has in instance of RobotDrive (wpiDrive) if you want to use
 * that.
 * 
 * @since 0.0.0
 */
public class Drivetrain extends Subsystem {
	private CANTalon driveLeftMaster;
	private CANTalon driveLeftFollower_One;
	private CANTalon driveLeftFollower_Two;
	
	private CANTalon driveRightMaster;
	private CANTalon driveRightFollower_One;
	private CANTalon driveRightFollower_Two;
	/**
	 * Instance of wpiDrive for using WPI's driving code. Should <em>not</em> be
	 * used for tank driving (use {@link Drivetrain#TankDrive} instead.)
	 * 
	 * @since 0.0.0
	 */
	public RobotDrive wpiDrive;
	public Drivetrain() {
		super("Drivetrain");

		this.driveLeftMaster = new CANTalon(RobotMap.leftTalonOne);
		this.driveLeftFollower_One = new CANTalon(RobotMap.leftTalonTwo);
		this.driveLeftFollower_Two = new CANTalon(RobotMap.leftTalonThree);
		// master follower
		this.driveLeftFollower_One.changeControlMode(CANTalon.TalonControlMode.Follower);
		this.driveLeftFollower_One.set(driveLeftMaster.getDeviceID());
		this.driveLeftFollower_Two.changeControlMode(CANTalon.TalonControlMode.Follower);
		this.driveLeftFollower_Two.set(driveLeftMaster.getDeviceID());

		this.driveRightMaster = new CANTalon(RobotMap.rightTalonOne);
		this.driveRightFollower_One = new CANTalon(RobotMap.rightTalonTwo);
		this.driveRightFollower_Two = new CANTalon(RobotMap.rightTalonThree);
		// master follower
		this.driveRightFollower_One.changeControlMode(CANTalon.TalonControlMode.Follower);
		this.driveRightFollower_One.set(driveRightMaster.getDeviceID());
		this.driveRightFollower_Two.changeControlMode(CANTalon.TalonControlMode.Follower);
		this.driveRightFollower_Two.set(driveRightMaster.getDeviceID());

		this.wpiDrive = new RobotDrive(driveLeftMaster, driveRightMaster);
	}
	// Put methods for controlling this subsystem
	// here. Call these from Commands.

	@Override
	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		setDefaultCommand(new Drive());
	}

	/**
	 * Drives the drivetrain tank drive style. The drivetrain will continue to
	 * run until stopped with a method like {@link Drivetrain#StopDrive()}.
	 * 
	 * @param left
	 *            The power to drive the left side. Should be a {@code double}
	 *            between 0 and 1.
	 * @param right
	 *            The power to drive the right side. Should be a {@code double}
	 *            between 0 and 1.
	 */
	public void TankDrive(double left, double right) {
		if (left > RobotMap.DRIVE_TOLERANCE && right > RobotMap.DRIVE_TOLERANCE) {
			driveLeftMaster.set(left);
			driveRightMaster.set(right);
		}
	}

	/**
	 * Stops all drive motors. Does not require re-enabling motors after use.
	 * 
	 * @since 0.0.0
	 */
	public void StopDrive() {
		driveLeftMaster.set(0);
		driveRightMaster.set(0);
	}
}