package org.seanano.coop.model;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Abstract Coop implementation which covers the basics.
 */
public abstract class AbstractCoop implements Coop {
    private Integer id;
    private Map<Integer, Door> doorMap = new HashMap<>();
    private Map<Integer, Light> lightMap = new HashMap<>();
    
    protected AbstractCoop(Integer id) {
        this.id = id;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public Collection<? extends Door> getDoors() {
        return doorMap.values();
    }

    @Override
    public Door getDoor(Integer id) {
        return doorMap.get(id);
    }

    @Override
    public Collection<? extends Light> getLights() {
        return lightMap.values();
    }

    @Override
    public Light getLight(Integer id) {
        return lightMap.get(id);
    }

    /**
     * Updates the door in this coop.
     * 
     * @param door updated door
     */
    protected void updateDoor(Door door) {
        doorMap.put(door.getId(), door);
    }

    /**
     * Updates the light in this coop.
     * 
     * @param light updated light
     */
    protected void updateLight(Light light) {
        lightMap.put(light.getId(), light);
    }
}
