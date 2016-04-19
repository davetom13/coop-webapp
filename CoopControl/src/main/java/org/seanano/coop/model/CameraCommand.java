package org.seanano.coop.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Bean class representation of a command to be performed on a camera.
 */
@ApiModel(description = "Command for controlling a camera")
public class CameraCommand {
    @ApiModelProperty(value = "Camera command to be executed.", required = true)
    private Command command;

    @ApiModelProperty(value = "Angle for the pan / tilt commands.", required = false)
    Integer angle;
    
    /**
     * Creates a new CameraCommand. Provided to allow automatic creation as part of data marshaling. If used, the
     * setCommand must be called with a valid command.
     */
    public CameraCommand() {}

    /**
     * Creates a new CameraCommand.
     * 
     * @param command command to perform.
     * @param value
     */
    public CameraCommand(Command command, Integer angle) {
        assert command != null;
        this.command = command;
        this.angle = angle;
    }

    /**
     * Sets the command to be performed. Should only be called if the no-arg constructor was used.
     * 
     * @param command command to be performed.
     */
    public void setCommand(Command command) {
        assert command == null;
        this.command = command;
    }

    /**
     * Gets the command to be performed.
     * 
     * @return command to be performed.
     */
    public Command getCommand() {
        assert command != null;
        return command;
    }
    
    /**
     * Gets the angle to be set by this command.
     * 
     * @return angle to be set, null if not an angle command
     */
    public Integer getAngle() {
        return angle;
    }
    
    /**
     * Enum for the valid commands to perform.
     */
    public enum Command {
        UPDATE_PAN_ANGLE,
        UPDATE_TILT_ANGLE
    }
}
