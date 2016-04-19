package org.seanano.coop.model;

import java.util.Collection;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Interface for a representation of a chicken coop.
 */
@ApiModel(description = "Top level coop object")
public interface Coop extends Identifiable {
    /**
     * Gets all of the doors of this coop.
     * 
     * @return all of the doors of this coop
     */
    @ApiModelProperty(value = "All of the coop's doors", required = true)
    public Collection<Door> getDoors();

    /**
     * Gets a specific door.
     * 
     * @param id identifier of door
     * @return specified door, null if not present
     */
    public Door getDoor(Integer id);

    /**
     * Gets all of the lights of this coop.
     * 
     * @return all of the lights of this coop
     */
    @ApiModelProperty(value = "All of the coop's lights", required = true)
    public Collection<Light> getLights();

    /**
     * Gets a specific light.
     * 
     * @param id identifier of light
     * @return specified light, null if not present
     */
    public Light getLight(Integer id);

    /**
     * Gets all of the cameras of this coop.
     * 
     * @return all of the cameras of this coop
     */
    @ApiModelProperty(value = "All of the coop's cameras", required = true)
    public Collection<Camera> getCameras();

    /**
     * Gets a specific camera.
     * 
     * @param id identifier of camera
     * @return specified camera, null if not present
     */
    public Camera getCamera(Integer id);

    /**
     * Gets the uptime of the embedded firmware in millis.
     * 
     * @return uptime of embedded firmware in millis
     */
    @ApiModelProperty(value = "Embedded device's uptime in milliseconds", required = true)
    public long getUptime();

    /**
     * Forces a refresh of the data for this coop.
     * 
     * @throws Exception if an error occurs refreshing the data
     */
    public void refresh() throws Exception;

    /**
     * Performs a control operation on a door.
     * 
     * @param door door to control
     * @param command command to perform
     * @throws Exception if an error occurs performing the command
     */
    public void control(Door door, DoorCommand command) throws Exception;

    /**
     * Performs a control operation on a light.
     * 
     * @param light light to control
     * @param command command to perform
     * @throws Exception if an error occurs performing the command
     */
    public void control(Light door, LightCommand command) throws Exception;

    /**
     * Performs a control operation on a camera.
     * 
     * @param camera camera to control
     * @param commands commands to perform
     * @throws Exception if an error occurs performing the command
     */
    public void control(Camera camera, CameraCommand[] commands) throws Exception;
}
