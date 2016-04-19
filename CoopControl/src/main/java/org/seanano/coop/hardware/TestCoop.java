package org.seanano.coop.hardware;

import org.seanano.coop.model.AbstractCoop;
import org.seanano.coop.model.Camera;
import org.seanano.coop.model.CameraCommand;
import org.seanano.coop.model.Door;
import org.seanano.coop.model.DoorCommand;
import org.seanano.coop.model.DoorState;
import org.seanano.coop.model.Light;
import org.seanano.coop.model.LightCommand;
import org.seanano.coop.model.LightState;

/**
 * Software only coop that can be used for testing when the hardware is not available.
 */
class TestCoop extends AbstractCoop {
    long start = System.currentTimeMillis();

    TestCoop(Integer id) {
        super(id);

        ScheduledDoor door = new ScheduledDoor(0, " A door");
        door = new ScheduledDoor(door, DoorState.CLOSED);
        updateDoor(door);

        ScheduledLight light = new ScheduledLight(0, "A light");
        light = new ScheduledLight(light, LightState.OFF);
        updateLight(light);
        
        PanTiltCamera camera = new PanTiltCamera(0, "A camera");
        camera = new PanTiltCamera(camera, 90, 90);
        updateCamera(camera);
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
                throw new Exception("Unknown door command " + command.getCommand());
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
                throw new Exception("Unknown light command " + command.getCommand());
        }

        updateLight(updatedLight);
    }

    @Override
    public void control(Camera camera, CameraCommand[] commands) throws Exception {
        int panAngle = camera.getPanAngle();
        int tiltAngle = camera.getTiltAngle();
        
        for (CameraCommand command : commands) {
            switch (command.getCommand()) {
                case UPDATE_PAN_ANGLE:
                    panAngle = command.getAngle();
                    break;
                case UPDATE_TILT_ANGLE:
                    tiltAngle = command.getAngle();
                    break;
                default:
                    throw new Exception("Unknown camera command " + command.getCommand());
            }
        }

        updateCamera(new PanTiltCamera(camera, panAngle, tiltAngle));
    }
}
