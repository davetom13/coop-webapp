package org.seanano.coop.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Interface for a representation of a light.
 */
@ApiModel(description = "Representation of a light")
public interface Light extends Identifiable {
    /**
     * Gets a human readable description of this object.
     * 
     * @return human readable description of this object
     */
    @ApiModelProperty(value = "Human readable description of the light", required = true)
    public String getDescription();

    /**
     * Gets whether this object is controllable or not.
     * 
     * @return true if this object is controllable, false otherwise
     */
    @ApiModelProperty(value = "Shows whether the light is controllable", required = true, readOnly = true)
    public boolean isControllable();

    /**
     * Gets the current state of this object.
     * 
     * @return current state of this object
     */
    @ApiModelProperty(value = "Current state of the light", required = true)
    public LightState getState();

    /**
     * Gets the next command scheduled to be performed on this object.
     * 
     * @return next command scheduled to be performed on this object, null if none
     */
    @ApiModelProperty(value = "Next scheduled command for the light", required = false)
    public ScheduledCommand<LightCommand> getNextScheduledCommand();
}
