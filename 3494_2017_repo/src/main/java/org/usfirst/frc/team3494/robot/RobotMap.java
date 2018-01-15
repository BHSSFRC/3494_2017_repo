package org.usfirst.frc.team3494.robot;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
    // For example to map the left and right motors, you could define the
    // following variables to use with your drivetrain subsystem.
    // public static int leftMotor = 1;
    // public static int rightMotor = 2;

    // If you are using multiple modules, make sure to define both the port
    // number and the module. For example you with a rangefinder:
    // public static int rangefinderPort = 1;
    // public static int rangefinderModule = 1;
    // Joystick Numbers
    public static final int XBOX_ONE = 0;
    public static final int XBOX_TWO = 1;
    // drivetrain left
    public static final int leftTalonOne = 17;
    public static final int leftTalonTwo = 1;
    public static final int leftTalonThree = 2;
    // drivetrain right
    public static final int rightTalonOne = 13;
    public static final int rightTalonTwo = 14;
    public static final int rightTalonThree = 15;
    // encoders
    public static final int ENCODER_RIGHT_A = 7;
    public static final int ENCODER_RIGHT_B = 6;
    public static final int ENCODER_LEFT_A = 9;
    public static final int ENCODER_LEFT_B = 8;
    // climber
    public static final int CLIMBER_MOTOR = 3;
    public static final int CLIMBER_MOTOR_PDP = RobotMap.CLIMBER_MOTOR;
    public static final int CLIMBER_PTO = 7;

    public static final int COMPRESSOR = 0;
    // gear holder pistons
    public static final int GEAR_DOOR_F = 3;
    public static final int GEAR_DOOR_R = 2;
    public static final int GEAR_RAMP = 4;

    public static final int CONVEYER_M = 10;

    public static final long TALON_RESET_DELAY = 100;
}
