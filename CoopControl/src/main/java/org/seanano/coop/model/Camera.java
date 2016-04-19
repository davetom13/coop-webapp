package org.seanano.coop.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Interface for a representation of a camera.
 */
@ApiModel(description = "Representation of a camera")
public interface Camera extends Identifiable {
    /**
     * Gets a human readable description of this object.
     * 
     * @return human readable description of this object
     */
    @ApiModelProperty(value = "Human readable description of the camera", required = true)
    public String getDescription();

    /**
     * Gets whether this object is controllable or not.
     * 
     * @return true if this object is controllable, false otherwise
     */
    @ApiModelProperty(value = "Shows whether the camera is controllable", required = true, readOnly = true)
    public boolean isControllable();

    /**
     * Gets the current pan angle of the camera.
     * 
     * @return current pan angle, null if unknown.
     */
    @ApiModelProperty(value = "Current camera pan angle", required = false)
    public Integer getPanAngle();
    
    /**
     * Gets the current tilt angle of the camera.
     * 
     * @return current tilt angle, null if unknown.
     */
    @ApiModelProperty(value = "Current camera tilt angle", required = false)
    public Integer getTiltAngle();
}
