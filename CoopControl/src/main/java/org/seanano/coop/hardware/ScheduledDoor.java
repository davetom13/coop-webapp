package org.seanano.coop.hardware;

import org.seanano.coop.model.AbstractDoor;
import org.seanano.coop.model.Door;
import org.seanano.coop.model.DoorCommand;
import org.seanano.coop.model.DoorState;
import org.seanano.coop.model.ScheduledCommand;

/**
 * Simple implementation of a door with a static scheduler.
 */
class ScheduledDoor extends AbstractDoor {
    /**
     * Creates a new ScheduledDoor.
     * 
     * @param id identifier of the door
     * @param description human readable description of the door
     */
    ScheduledDoor(Integer id, String description) {
        super(id, description, DoorState.UNKNOWN);
    }

    /**
     * Creates a new ScheduledDoor.
     * 
     * @param door existing door
     * @param state new state for door
     */
    ScheduledDoor(Door door, DoorState state) {
        super(door, state);
    }

    @Override
    public boolean isControllable() { return true; }

    @Override
    public ScheduledCommand<DoorCommand> getNextScheduledCommand() {
        return CoopScheduler.getNextDoorCommand();
    }
}
