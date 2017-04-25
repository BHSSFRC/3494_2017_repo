package org.usfirst.frc.team3494.robot;

import java.util.ArrayList;

import org.usfirst.frc.team3494.robot.commands.auto.DistanceDrive;
import org.usfirst.frc.team3494.robot.commands.auto.DropGear;
import org.usfirst.frc.team3494.robot.commands.auto.PIDAngleDrive;
import org.usfirst.frc.team3494.robot.commands.auto.PIDFullDrive;
import org.usfirst.frc.team3494.robot.commands.auto.SetGearGrasp;
import org.usfirst.frc.team3494.robot.commands.auto.XYDrive;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
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
	 * The distance of the first pull in gear placing.
	 */
	private static final double FIRST_PULL = 101.5 - 35.5 - 17.75 - 5.5;
	/**
	 * The angle to turn after the first pull in gear placing.
	 */
	private static final double ANGLE = 60;
	/**
	 * After turning {@link AutoGenerator#ANGLE} degrees, drive this distance.
	 */
	private static final double SECOND_PULL = 35 - 17.75;

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
		ArrayList<Command> list = new ArrayList<>();
		list.add(new XYDrive(36, 36));
		return list;
	}

	/**
	 * Drives to the baseline.
	 *
	 * @see org.usfirst.frc.team3494.robot.commands.auto.DistanceDrive
	 * @since 0.0.3
	 * @return A list of commands suitable for use with
	 *         {@link org.usfirst.frc.team3494.robot.commands.auto.ConstructedAuto}.
	 */
	public static ArrayList<Command> crossBaseLine() {
		ArrayList<Command> list = new ArrayList<>();
		list.add(new DistanceDrive(72, UnitTypes.INCHES));
		return list;
	}

	public static ArrayList<Command> placeCenterGear() {
		ArrayList<Command> list = new ArrayList<>();
		list.add(new PIDFullDrive(110.75));
		return list;
	}

	/**
	 * Drives forward, turns right, drives forward again.
	 *
	 * @see org.usfirst.frc.team3494.robot.commands.auto.ConstructedAuto
	 *      Constructed Auto
	 * @return A list for use with
	 *         {@link org.usfirst.frc.team3494.robot.commands.auto.ConstructedAuto
	 *         ConstructedAuto}
	 */
	public static ArrayList<Command> gearPassiveRight() {
		ArrayList<Command> list = new ArrayList<>();
		list.add(new PIDFullDrive(FIRST_PULL, 0, true));
		list.add(new PIDAngleDrive(ANGLE));
		list.add(new PIDFullDrive(SECOND_PULL));
		// list.add(new DistanceDrive(-60, UnitTypes.INCHES));
		return list;
	}

	/**
	 * Drives forward, turns left, drives forward again.
	 *
	 * @see org.usfirst.frc.team3494.robot.commands.auto.ConstructedAuto
	 *      Constructed Auto
	 * @return A list for use with
	 *         {@link org.usfirst.frc.team3494.robot.commands.auto.ConstructedAuto
	 *         ConstructedAuto}
	 */
	public static ArrayList<Command> gearPassiveLeft() {
		ArrayList<Command> list = new ArrayList<>();
		list.add(new PIDFullDrive(FIRST_PULL, 0, true));
		list.add(new PIDAngleDrive(-ANGLE));
		list.add(new PIDFullDrive(SECOND_PULL));
		// list.add(new DistanceDrive(-60, UnitTypes.INCHES));
		return list;
	}

	/**
	 * Same as {@link AutoGenerator#gearPassiveLeft()}, but drops the gear on
	 * the peg at the end.
	 *
	 * @see org.usfirst.frc.team3494.robot.commands.auto.ConstructedAuto
	 *      Constructed Auto
	 * @return A list for use with
	 *         {@link org.usfirst.frc.team3494.robot.commands.auto.ConstructedAuto
	 *         ConstructedAuto}
	 */
	public static ArrayList<Command> activeGearLeft() {
		ArrayList<Command> list = AutoGenerator.gearPassiveLeft();
		list.add(new DropGear());
		list.add(new PIDFullDrive(-15));
		list.add(new SetGearGrasp(Value.kReverse));
		list.add(new PIDAngleDrive(ANGLE));
		return list;
	}

	/**
	 * Same as {@link AutoGenerator#gearPassiveRight()}, but drops the gear on
	 * the peg at the end.
	 *
	 * @see org.usfirst.frc.team3494.robot.commands.auto.ConstructedAuto
	 *      Constructed Auto
	 * @return A list for use with
	 *         {@link org.usfirst.frc.team3494.robot.commands.auto.ConstructedAuto
	 *         ConstructedAuto}
	 */
	public static ArrayList<Command> activeGearRight() {
		ArrayList<Command> list = AutoGenerator.gearPassiveRight();
		list.add(new DropGear());
		list.add(new PIDFullDrive(-15));
		list.add(new SetGearGrasp(Value.kReverse));
		list.add(new PIDAngleDrive(-ANGLE));
		return list;
	}

	public static ArrayList<Command> fullBlueRight() {
		ArrayList<Command> list = AutoGenerator.activeGearRight();
		list.add(new PIDFullDrive(300));
		return list;
	}

	public static ArrayList<Command> fullBlueLeft() {
		ArrayList<Command> list = AutoGenerator.activeGearLeft();
		list.add(new PIDFullDrive(102));
		list.add(new PIDAngleDrive(40));
		list.add(new PIDFullDrive(250));
		list.add(new PIDFullDrive(-40));
		return list;
	}

	public static ArrayList<Command> fullRedRight() {
		ArrayList<Command> list = AutoGenerator.activeGearRight();
		list.add(new PIDFullDrive(102));
		list.add(new PIDAngleDrive(-40));
		list.add(new PIDFullDrive(250));
		list.add(new PIDFullDrive(40));
		return list;
	}

	public static ArrayList<Command> fullRedLeft() {
		ArrayList<Command> list = AutoGenerator.activeGearLeft();
		list.add(new PIDFullDrive(300));
		return list;
	}
}
