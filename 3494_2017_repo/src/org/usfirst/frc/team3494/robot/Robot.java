package org.usfirst.frc.team3494.robot;

import org.usfirst.frc.team3494.robot.commands.auto.ConstructedAuto;
import org.usfirst.frc.team3494.robot.subsystems.Climber;
import org.usfirst.frc.team3494.robot.subsystems.Drivetrain;
import org.usfirst.frc.team3494.robot.subsystems.GearTake;
import org.usfirst.frc.team3494.robot.subsystems.Intake;
import org.usfirst.frc.team3494.robot.subsystems.Kompressor;
import org.usfirst.frc.team3494.robot.subsystems.Turret;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {

	public static OI oi;
	public static Drivetrain driveTrain;
	public static Climber climber;
	public static Turret turret;
	public static Intake intake;
	public static Kompressor kompressor;
	public static GearTake gearTake;
	/**
	 * The gyro board mounted to the RoboRIO.
	 * 
	 * @since 0.0.2
	 */
	public static AHRS ahrs;
	public static PowerDistributionPanel pdp = new PowerDistributionPanel();

	CommandGroup autonomousCommand;
	public static SendableChooser<CommandGroup> chooser;
	public static Preferences prefs;

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		chooser = new SendableChooser<CommandGroup>();
		driveTrain = new Drivetrain();
		climber = new Climber();
		turret = new Turret();
		kompressor = new Kompressor();
		intake = new Intake();
		gearTake = new GearTake();
		oi = new OI();
		ahrs = new AHRS(SerialPort.Port.kMXP);
		// Auto programs come after all subsystems are created\
		chooser.addDefault("To the baseline!", new ConstructedAuto(AutoGenerator.crossBaseLine()));
		chooser.addObject("Other command", new ConstructedAuto(AutoGenerator.crossBaseLine()));
		@SuppressWarnings("unused")
		UsbCamera camera = CameraServer.getInstance().startAutomaticCapture();
		// put chooser on DS
		SmartDashboard.putData("Auto mode", chooser);
		// get preferences
		prefs = Preferences.getInstance();
	}

	/**
	 * This function is called once each time the robot enters Disabled mode.
	 * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
	 */
	@Override
	public void disabledInit() {

	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString code to get the auto name from the text box below the Gyro
	 *
	 * You can add additional auto modes by adding additional commands to the
	 * chooser code above (like the commented example) or additional comparisons
	 * to the switch structure below with additional strings &amp; commands.
	 */
	@Override
	public void autonomousInit() {
		autonomousCommand = chooser.getSelected();
		// schedule the autonomous command (example)
		if (autonomousCommand != null) {
			autonomousCommand.start();
		}
	}

	/**
	 * This function is called periodically during autonomous.
	 */
	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void teleopInit() {
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
		if (autonomousCommand != null) {
			autonomousCommand.cancel();
		}
	}

	/**
	 * This function is called periodically during operator control.
	 */
	@Override
	public void teleopPeriodic() {
		SmartDashboard.putData(Scheduler.getInstance());
		Scheduler.getInstance().run();
		SmartDashboard.putNumber("[left] distance", Robot.driveTrain.getLeftDistance(UnitTypes.RAWCOUNT));
		SmartDashboard.putNumber("[left] distance inches", Robot.driveTrain.getLeftDistance(UnitTypes.INCHES));

		SmartDashboard.putNumber("[right] distance", Robot.driveTrain.getRightDistance(UnitTypes.RAWCOUNT));
		SmartDashboard.putNumber("[right] distance inches", Robot.driveTrain.getRightDistance(UnitTypes.INCHES));

		SmartDashboard.putNumber("Motor 0", Robot.pdp.getCurrent(0));
		SmartDashboard.putNumber("Motor 1", Robot.pdp.getCurrent(1));
		SmartDashboard.putNumber("Motor 2", Robot.pdp.getCurrent(2));

		SmartDashboard.putNumber("Motor 13", Robot.pdp.getCurrent(13));
		SmartDashboard.putNumber("Motor 14", Robot.pdp.getCurrent(14));
		SmartDashboard.putNumber("Motor 15", Robot.pdp.getCurrent(15));

	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
		LiveWindow.run();
	}
}
