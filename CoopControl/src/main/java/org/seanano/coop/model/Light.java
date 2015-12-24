package org.seanano.coop.model;

/**
 * Interface for a representation of a door.
 */
public interface Light extends Identifiable {
    /**
     * Gets a human readable description of this object.
     * 
     * @return human readable description of this object
     */
    public String getDescription();

    /**
     * Gets whether this object is controllable or not.
     * 
     * @return true if this object is controllable, false otherwise
     */
    public boolean isControllable();

    /**
     * Gets the current state of this object.
     * 
     * @return current state of this object
     */
    public LightState getState();

    /**
     * Gets the next command scheduled to be performed on this object.
     * 
     * @return next command scheduled to be performed on this object, null if none
     */
    public ScheduledCommand<LightCommand> getNextScheduledCommand();
}
