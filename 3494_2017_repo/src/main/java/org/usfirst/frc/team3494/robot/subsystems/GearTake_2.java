package org.usfirst.frc.team3494.robot.subsystems;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.AnalogTrigger;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.team3494.robot.RobotMap;

/**
 * Gear holder subsystem. Contains all methods for controlling the robot's gear
 * holder.
 */
public class GearTake_2 extends Subsystem {
    /**
     * The solenoid that controls the ramp on the gear intake. Should stay
     * retracted most of the time.
     */
    private DoubleSolenoid rampenoid;
    /**
     * Solenoid that opens and closes the door on the gear holder. Typically in
     * kReverse.
     */
    private DoubleSolenoid doornoid;
    // analog linebreak sensor
    /**
     * AnalogInput object to represent the linebreak sensor on the gear holder.
     */
    public AnalogInput ai;
    /**
     * AnalogTrigger object to represent the linebreak sensor on the gear holder (more abstract.)
     */
    public AnalogTrigger at;

    public GearTake_2() {
        super("Gear holder");
        doornoid = new DoubleSolenoid(RobotMap.GEAR_DOOR_F, RobotMap.GEAR_DOOR_R);
        rampenoid = new DoubleSolenoid(0, RobotMap.GEAR_RAMP);
        ai = new AnalogInput(0);
        at = new AnalogTrigger(ai);
        at.setLimitsVoltage(3, 5);
    }

    @Override
    public void initDefaultCommand() {
    }

    /**
     * Sets the position of the intake ramp.
     *
     * @param value The position to set the ramp to.
     */
    public void setRamp(Value value) {
        rampenoid.set(value);
    }

    /**
     * Sets the position of the actual gear holder.
     *
     * @param value The position to set the holder to.
     */
    public void setGrasp(Value value) {
        doornoid.set(value);
    }

    /**
     * Releases the gear with a call to {@link GearTake_2#setGrasp}.
     */
    public void releaseGear() {
        setGrasp(Value.kForward);
    }

    /**
     * Closes the gear holder with a call to {@link GearTake_2#setGrasp}.
     */
    public void closeHolder() {
        setGrasp(Value.kReverse);
    }

    /**
     * Gets the state of the intake ramp solenoid. Equivalent to
     * {@code this.rampenoid.get()}, but {@link GearTake_2#rampenoid} is
     * private.
     *
     * @return The value of {@code this.rampenoid.get()}.
     */
    public Value getRampState() {
        return rampenoid.get();
    }

    /**
     * Gets the state of the gear holder. Equivalent to
     * {@code this.rampenoid.get()}, but {@link GearTake_2#rampenoid} is
     * private.
     *
     * @return The value of {@code this.rampenoid.get()}.
     */
    public Value getGearState() {
        return doornoid.get();
    }
}
