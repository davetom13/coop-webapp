package org.seanano.coop.hardware;

import org.seanano.coop.model.AbstractCoop;
import org.seanano.coop.model.Door;
import org.seanano.coop.model.DoorCommand;
import org.seanano.coop.model.DoorState;
import org.seanano.coop.model.Light;
import org.seanano.coop.model.LightCommand;
import org.seanano.coop.model.LightState;

/**
 * Software only coop that can be used for testing when the hardware is not available.
 */
public class TestCoop extends AbstractCoop {
    long start = System.currentTimeMillis();

    TestCoop(Integer id) {
        super(id);

        ScheduledDoor door = new ScheduledDoor(0, " A door");
        door = new ScheduledDoor(door, DoorState.CLOSED);
        updateDoor(door);

        ScheduledLight light = new ScheduledLight(0, "A light");
        light = new ScheduledLight(light, LightState.OFF);
        updateLight(light);
    }

    @Override
    public long getUptime() { return System.currentTimeMillis() - start; }

    @Override
    public void refresh() throws Exception {}

    @Override
    public void control(Door door, DoorCommand command) throws Exception {
        ScheduledDoor updatedDoor;

        switch (command.getCommand()) {
            case CLOSE:
                updatedDoor = new ScheduledDoor(door, DoorState.CLOSED);
                break;
            case OPEN:
                updatedDoor = new ScheduledDoor(door, DoorState.OPENED);
                break;
            default:
                throw new Exception("Unknown command " + command.getCommand());
        }

        updateDoor(updatedDoor);
    }

    @Override
    public void control(Light door, LightCommand command) throws Exception {
        ScheduledLight updatedLight;

        switch (command.getCommand()) {
            case OFF:
                updatedLight = new ScheduledLight(door, LightState.OFF);
                break;
            case ON:
                updatedLight = new ScheduledLight(door, LightState.ON);
                break;
            default:
                throw new Exception("Unknown command " + command.getCommand());
        }

        updateLight(updatedLight);
    }
}
