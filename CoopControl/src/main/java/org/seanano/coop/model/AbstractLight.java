package org.seanano.coop.model;

/**
 * Abstract Light representation. Handles everything but isControllable() and getNextScheduledCommand()..
 */
public abstract class AbstractLight implements Light {
    private Integer id;
    private String description;
    private LightState state;

    /**
     * Creates a new AbstractLight.
     * 
     * @param id identifier of light
     * @param description human readable description of light
     * @param state state of light
     */
    public AbstractLight(Integer id, String description, LightState state) {
        this.id = id;
        this.description = description;
        this.state = state;
    }

    /**
     * Creates a new AbstractLight from an existing Light and a new state.
     * 
     * @param light existing light
     * @param state new state of light
     */
    public AbstractLight(Light light, LightState state) {
        this.id = light.getId();
        this.description = light.getDescription();
        this.state = state;
    }

    @Override
    public Integer getId() { return id; }

    @Override
    public String getDescription() { return description; }

    @Override
    public LightState getState() { return state; }

}
