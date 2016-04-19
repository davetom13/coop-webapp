package org.seanano.coop.hardware;

import java.security.InvalidParameterException;
import java.util.Collection;

import org.seanano.coop.model.AbstractCoop;
import org.seanano.coop.model.Camera;
import org.seanano.coop.model.CameraCommand;
import org.seanano.coop.model.Door;
import org.seanano.coop.model.DoorCommand;
import org.seanano.coop.model.DoorState;
import org.seanano.coop.model.Light;
import org.seanano.coop.model.LightCommand;
import org.seanano.coop.model.LightState;

import com.pi4j.io.i2c.I2CBus;
import com.pi4j.io.i2c.I2CDevice;
import com.pi4j.io.i2c.I2CFactory;

/**
 * Coop implementation using the <a href="https://github.com/seanano/coop-alamode">coop-alamode</a> arduino firmware.
 */
class ArduinoCoop extends AbstractCoop {
    private static final int ARDUINO_I2C_ADDRESS = 0x10;
    
    private static final int I2C_WRITE_SIZE = 2;
    private static final int I2C_WRITE_COMMAND_OFFSET = 0;
    private static final int I2C_WRITE_DATA_OFFSET = 1;

    private static final byte I2C_DOOR_COMMAND = 0x01;
    private static final byte I2C_LIGHT_COMMAND = 0x02;
    private static final byte I2C_PAN_COMMAND = 0x03;
    private static final byte I2C_TILT_COMMAND = 0x04;

    private static final byte I2C_DOOR_COMMAND_OPEN = 0x01;
    private static final byte I2C_DOOR_COMMAND_CLOSE = 0x02;

    private static final byte I2C_LIGHT_COMMAND_ON = 0x01;
    private static final byte I2C_LIGHT_COMMAND_OFF = 0x02;

    private static final byte DOOR_STATE_UNKNOWN = 0;
    private static final byte DOOR_STATE_CLOSE = 1;
    private static final byte DOOR_STATE_CLOSING = 2;
    private static final byte DOOR_STATE_OPEN = 3;
    private static final byte DOOR_STATE_OPENING = 4;

    private static final byte LIGHT_STATE_ON = 1;
    private static final byte LIGHT_STATE_OFF = 0;
    
    private static final int MIN_PAN_TILT_ANGLE = 0;
    private static final int MAX_PAN_TILT_ANGLE = 180;

    private ScheduledDoor door;
    private ScheduledLight light;
    private PanTiltCamera camera;
    private I2CDevice arduinoI2C;
    private long uptime;

    /**
     * Creates a new ArduinoCoop.
     * 
     * @param id identifier of coop
     * @throws Exception if unable to initialize communication with coop
     */
    ArduinoCoop(int id) throws Exception {
        super(id);
        
        I2CBus bus = I2CFactory.getInstance(I2CBus.BUS_1);
        arduinoI2C = bus.getDevice(ARDUINO_I2C_ADDRESS);

        door = new ScheduledDoor(0, "Pop door");
        updateDoor(door);

        light = new ScheduledLight(0, "Main Light");
        updateLight(light);
        
        camera = new PanTiltCamera(0, "Pan/Tilt controllable Camera");
        updateCamera(camera);
    }

    @Override
    public synchronized Collection<Door> getDoors() { return super.getDoors(); };

    @Override
    public synchronized Door getDoor(Integer id) { return super.getDoor(id); }

    @Override
    public synchronized Collection<Light> getLights() { return super.getLights(); }

    @Override
    public synchronized Light getLight(Integer id) { return super.getLight(id); }

    @Override
    public synchronized Collection<Camera> getCameras() { return super.getCameras(); }

    @Override
    public synchronized Camera getCamera(Integer id) { return super.getCamera(id); }
    
    @Override
    public synchronized long getUptime() { return uptime; }

    @Override
    public synchronized void refresh() throws Exception {
        byte[] response = new byte[9];
        arduinoI2C.read(response, 0, response.length);
        if (response[0] != 0x02) {
            throw new Exception(
                    "Unexpected response version, expected 0x02, saw 0x" + Integer.toHexString(response[0]));
        }

        int doorStateValue = (response[1] & 0xF0) >> 4;
        DoorState doorState = getDoorState(doorStateValue);
        if (doorState != door.getState()) {
            door = new ScheduledDoor(door, doorState);
            updateDoor(door);
        }

        int lightStateValue = (response[1] & 0x0F);
        LightState lightState = getLightState(lightStateValue);
        if (lightState != light.getState()) {
            light = new ScheduledLight(light, lightState);
            updateLight(light);
        }

        uptime = 0;
        uptime |= (response[2] & 0xFFL) << 32;
        uptime |= (response[3] & 0xFFL) << 24;
        uptime |= (response[4] & 0xFFL) << 16;
        uptime |= (response[5] & 0xFFL) << 8;
        uptime |= (response[6] & 0xFFL);

        int panAngle = Byte.toUnsignedInt(response[7]);
        int tiltAngle = Byte.toUnsignedInt(response[8]);
        if ((camera.getPanAngle() == null) || (camera.getPanAngle() != panAngle)
                || (camera.getTiltAngle() == null) != (camera.getTiltAngle() != tiltAngle)) {
            camera = new PanTiltCamera(camera, panAngle, tiltAngle);
            updateCamera(camera);
        }
    }

    @Override
    public synchronized void control(Door door, DoorCommand command) throws Exception {
        byte[] i2cBytes = new byte[I2C_WRITE_SIZE];
        i2cBytes[I2C_WRITE_COMMAND_OFFSET] = I2C_DOOR_COMMAND;
        switch (command.getCommand()) {
            case OPEN:
                i2cBytes[I2C_WRITE_DATA_OFFSET] = I2C_DOOR_COMMAND_OPEN;
                break;
            case CLOSE:
                i2cBytes[I2C_WRITE_DATA_OFFSET] = I2C_DOOR_COMMAND_CLOSE;
                break;
            default:
                throw new InvalidParameterException("invalid door command: " + command);
        }
        arduinoI2C.write(i2cBytes, 0, i2cBytes.length);
    }

    @Override
    public synchronized void control(Light light, LightCommand command) throws Exception {
        byte[] i2cBytes = new byte[I2C_WRITE_SIZE];
        i2cBytes[I2C_WRITE_COMMAND_OFFSET] = I2C_LIGHT_COMMAND;
        switch (command.getCommand()) {
            case ON:
                i2cBytes[I2C_WRITE_DATA_OFFSET] = I2C_LIGHT_COMMAND_ON;
                break;
            case OFF:
                i2cBytes[I2C_WRITE_DATA_OFFSET] = I2C_LIGHT_COMMAND_OFF;
                break;
            default:
                throw new InvalidParameterException("invalid light command: " + command);
        }
        arduinoI2C.write(i2cBytes, 0, i2cBytes.length);
    }

    @Override
    public synchronized void control(Camera camera, CameraCommand[] commands) throws Exception {
        byte[] i2cBytes = new byte[I2C_WRITE_SIZE];
        Integer angle;

        for (CameraCommand command : commands) {
            switch (command.getCommand()) {
                case UPDATE_PAN_ANGLE:
                    i2cBytes[I2C_WRITE_COMMAND_OFFSET] = I2C_PAN_COMMAND;
                    angle = command.getAngle();
                    validateCameraAngle(angle);
                    i2cBytes[I2C_WRITE_DATA_OFFSET] = angle.byteValue();
                    break;
                case UPDATE_TILT_ANGLE:
                    i2cBytes[I2C_WRITE_COMMAND_OFFSET] = I2C_TILT_COMMAND;
                    angle = command.getAngle();
                    validateCameraAngle(angle);
                    i2cBytes[I2C_WRITE_DATA_OFFSET] = angle.byteValue();
                    break;
                default:
                    throw new InvalidParameterException("invalid camera command: " + command);
            }
            arduinoI2C.write(i2cBytes, 0, i2cBytes.length);
        }
    }

    @Override
    protected synchronized void updateDoor(Door door) { super.updateDoor(door); }

    @Override
    protected synchronized void updateLight(Light light) { super.updateLight(light); }

    /**
     * Converts a door state returned by the firmware to an enum.
     * 
     * @param state door state from firmware
     * @return enum representing door state
     */
    private DoorState getDoorState(int state) {
        switch (state) {
            case DOOR_STATE_UNKNOWN:
                return DoorState.UNKNOWN;
            case DOOR_STATE_CLOSE:
                return DoorState.CLOSED;
            case DOOR_STATE_CLOSING:
                return DoorState.CLOSING;
            case DOOR_STATE_OPEN:
                return DoorState.OPENED;
            case DOOR_STATE_OPENING:
                return DoorState.OPENING;
            default:
                return DoorState.UNKNOWN;
        }
    }

    /**
     * Converts a light state returned by the firmware to an enum.
     * 
     * @param state light state from firmware
     * @return enum representing light state
     */
    private LightState getLightState(int state) {
        switch (state) {
            case LIGHT_STATE_ON:
                return LightState.ON;
            case LIGHT_STATE_OFF:
                return LightState.OFF;
            default:
                return LightState.UNKNOWN;
        }
    }
    
    /**
     * Validates a Camera angle.  An angle is considered valid if it is between 0 and 180.
     * 
     * @param angle angle to validate
     * @throws Exception if angle is not valid
     */
    private void validateCameraAngle(Integer angle) throws Exception {
        if (angle == null) {
            throw new Exception("Camera angle must be specified");
        }
        if ((angle < MIN_PAN_TILT_ANGLE) || (angle > MAX_PAN_TILT_ANGLE)) {
            throw new Exception("Camera angle must be between 0 and 180 (inclusive)");
        }
    }

}
