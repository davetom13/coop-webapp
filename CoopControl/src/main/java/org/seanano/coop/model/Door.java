package org.seanano.coop.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Interface for a representation of a door.
 */
@ApiModel(description = "Representation of a door")
public interface Door extends Identifiable {
    /**
     * Gets a human readable description of this object.
     * 
     * @return human readable description of this object
     */
    @ApiModelProperty(value = "Human readable description of the door", required = true)
    public String getDescription();

    /**
     * Gets whether this object is controllable or not.
     * 
     * @return true if this object is controllable, false otherwise
     */
    @ApiModelProperty(value = "Shows whether the door is controllable", required = true, readOnly = true)
    public boolean isControllable();

    /**
     * Gets the current state of this object.
     * 
     * @return current state of this object
     */
    @ApiModelProperty(value = "Current state of the door", required = true)
    public DoorState getState();

    /**
     * Gets the next command scheduled to be performed on this object.
     * 
     * @return next command scheduled to be performed on this object, null if none
     */
    @ApiModelProperty(value = "Next scheduled command for the door", required = false)
    public ScheduledCommand<DoorCommand> getNextScheduledCommand();
}
