package org.usfirst.frc.team3494.robot;

import java.util.ArrayList;

import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.imgproc.Imgproc;
import org.usfirst.frc.team3494.robot.commands.auto.ConstructedAuto;
import org.usfirst.frc.team3494.robot.subsystems.Climber;
import org.usfirst.frc.team3494.robot.subsystems.Drivetrain;
import org.usfirst.frc.team3494.robot.subsystems.GearTake;
import org.usfirst.frc.team3494.robot.subsystems.Intake;
import org.usfirst.frc.team3494.robot.subsystems.Kompressor;
import org.usfirst.frc.team3494.robot.subsystems.Turret;
import org.usfirst.frc.team3494.robot.vision.GripPipeline;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.cscore.VideoCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.vision.VisionThread;

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

	Command autonomousCommand;
	public static SendableChooser<Command> chooser;
	public static Preferences prefs;

	public static UsbCamera camera_0;
	public static UsbCamera camera_1;
	// Vision items
	VisionThread visionThread;
	public static double centerX = 0.0;
	@SuppressWarnings("unused")
	private ArrayList<MatOfPoint> filteredContours;
	private ArrayList<Double> averages;

	private final Object imgLock = new Object();

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		chooser = new SendableChooser<Command>();
		driveTrain = new Drivetrain();
		climber = new Climber();
		turret = new Turret();
		kompressor = new Kompressor();
		intake = new Intake();
		gearTake = new GearTake();
		oi = new OI();
		ahrs = new AHRS(SerialPort.Port.kMXP);
		// Auto programs come after all subsystems are created
		chooser.addDefault("To the baseline!", new ConstructedAuto(AutoGenerator.crossBaseLine()));
		chooser.addObject("Other command", new ConstructedAuto(AutoGenerator.crossBaseLine()));
		// put chooser on DS
		SmartDashboard.putData("Auto mode", chooser);
		// get preferences
		prefs = Preferences.getInstance();
		camera_0 = CameraServer.getInstance().startAutomaticCapture("Gear View", 0);
		camera_1 = CameraServer.getInstance().startAutomaticCapture("Intake View", 1);
		// Create and start vision thread
		visionThread = new VisionThread(camera_0, new GripPipeline(), pipeline -> {
			if (!pipeline.filterContoursOutput().isEmpty()) {
				MatOfPoint firstCont = pipeline.filterContoursOutput().get(0);
				MatOfPoint secondCont = pipeline.filterContoursOutput().get(1);
				double average_y_one = 0;
				for (Point p : firstCont.toList()) {
					average_y_one += p.y;
				}
				double average_y_two = 0;
				for (Point p : secondCont.toList()) {
					average_y_two += p.y;
				}
				// divide by number of points to give actual average
				average_y_two = average_y_two / secondCont.toList().size();
				average_y_one = average_y_one / firstCont.toList().size();
				Rect r = Imgproc.boundingRect(pipeline.findContoursOutput().get(0));
				synchronized (imgLock) {
					centerX = r.x + (r.width / 2);
					filteredContours = pipeline.filterContoursOutput();
					// add averages to list
					averages.add(average_y_one);
					averages.add(average_y_two);
				}
			}
		});
		visionThread.start();
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
		camera_0.setExposureManual(15);
		camera_0.setWhiteBalanceManual(VideoCamera.WhiteBalance.kFixedIndoor);
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
		camera_0.setExposureManual(50);
		camera_0.setWhiteBalanceAuto();
	}

	/**
	 * This function is called periodically during operator control.
	 */
	@Override
	public void teleopPeriodic() {
		/*
		 * if (Robot.climber.getMotorCurrent() > 10.0D) {
		 * Robot.climber.engagePTO(); }
		 */
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
