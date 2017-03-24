package org.usfirst.frc.team3494.robot;

import java.util.ArrayList;

import org.usfirst.frc.team3494.robot.commands.DelayCommand;
import org.usfirst.frc.team3494.robot.commands.auto.DistanceDrive;
import org.usfirst.frc.team3494.robot.commands.auto.PIDAngleDrive;
import org.usfirst.frc.team3494.robot.commands.auto.PIDFullDrive;
import org.usfirst.frc.team3494.robot.commands.auto.XYDrive;
import org.usfirst.frc.team3494.robot.commands.gears.ToggleGearPosition;
import org.usfirst.frc.team3494.robot.commands.gears.ToggleGearRamp;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Class containing methods that return valid lists to pass to
 * {@link org.usfirst.frc.team3494.robot.commands.auto.ConstructedAuto}.
 * 
 * @since 0.0.3
 * @see org.usfirst.frc.team3494.robot.commands.auto.ConstructedAuto
 */
public class AutoGenerator {
	/**
	 * Test method. Drives to XY (36, 36) (inches).
	 * 
	 * @since 0.0.3
	 * @see org.usfirst.frc.team3494.robot.commands.auto.ConstructedAuto
	 * @see org.usfirst.frc.team3494.robot.commands.auto.XYDrive
	 * @return A list of commands suitable for use with
	 *         {@link org.usfirst.frc.team3494.robot.commands.auto.ConstructedAuto}.
	 */
	public static ArrayList<Command> autoOne() {
		ArrayList<Command> list = new ArrayList<Command>();
		list.add(new XYDrive(36, 36));
		return list;
	}

	/**
	 * Drives to the baseline and earns us five points (hopefully.)
	 * 
	 * @see org.usfirst.frc.team3494.robot.commands.auto.DistanceDrive
	 * @since 0.0.3
	 * @return A list of commands suitable for use with
	 *         {@link org.usfirst.frc.team3494.robot.commands.auto.ConstructedAuto}.
	 */
	public static ArrayList<Command> crossBaseLine() {
		ArrayList<Command> list = new ArrayList<Command>();
		list.add(new DistanceDrive(-72, UnitTypes.INCHES));
		return list;
	}

	public static ArrayList<Command> placeCenterGear() {
		ArrayList<Command> list = new ArrayList<Command>();
		list.add(new DistanceDrive(-111 / 2, UnitTypes.INCHES));
		list.add(new ToggleGearPosition());
		list.add(new ToggleGearRamp());
		list.add(new DelayCommand(250));
		list.add(new DistanceDrive(10, UnitTypes.INCHES));
		return list;
	}

	public static ArrayList<Command> gearPlaceAttempt() {
		ArrayList<Command> list = new ArrayList<Command>();
		list.add(new PIDFullDrive(-80));
		list.add(new PIDAngleDrive(60));
		list.add(new PIDFullDrive(-60));
		return list;
	}
	
	public static ArrayList<Command> gearPlaceAttemptLeft() {
		ArrayList<Command> list = new ArrayList<Command>();
		list.add(new PIDFullDrive(-80));
		list.add(new PIDAngleDrive(-60));
		list.add(new PIDFullDrive(-60));
		return list;
	}
}
