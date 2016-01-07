package org.seanano.coop.hardware;

import org.seanano.coop.model.AbstractLight;
import org.seanano.coop.model.Light;
import org.seanano.coop.model.LightCommand;
import org.seanano.coop.model.LightState;
import org.seanano.coop.model.ScheduledCommand;

/**
 * Simple implementation of a light with a static scheduler.
 */
class ScheduledLight extends AbstractLight {
    /**
     * Creates a new ScheduledLight.
     * 
     * @param id identifier of the light
     * @param description human readable description of the light
     */
    ScheduledLight(Integer id, String description) {
        super(id, description, LightState.UNKNOWN);
    }

    /**
     * Creates a new ScheduledLight.
     * 
     * @param light existing light
     * @param state new state for light
     */
    ScheduledLight(Light light, LightState state) {
        super(light, state);
    }

    @Override
    public boolean isControllable() { return true; }

    @Override
    public ScheduledCommand<LightCommand> getNextScheduledCommand() {
        return CoopScheduler.getNextLightCommand();
    }
}
