package org.seanano.coop.model;

import java.util.Collection;

/**
 * Interface for a representation of a chicken coop.
 */
public interface Coop extends Identifiable {
    /**
     * Gets all of the doors of this coop.
     * 
     * @return all of the doors of this coop
     */
    public Collection<? extends Door> getDoors();

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
    public Collection<? extends Light> getLights();

    /**
     * Gets a specific light.
     * 
     * @param id identifier of light
     * @return specified light, null if not present
     */
    public Light getLight(Integer id);

    /**
     * Gets the uptime of the embedded firmware in millis.
     * 
     * @return uptime of embedded firmware in millis
     */
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
}
