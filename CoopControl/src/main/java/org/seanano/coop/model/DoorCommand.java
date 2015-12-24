package org.seanano.coop.model;

/**
 * Bean class representation of a command to be performed on a door.
 */
public class DoorCommand implements SchedulableCommand {
    private Command command;

    /**
     * Creates a new DoorCommand. Provided to allow automatic creation as part of data marshaling. If used, the
     * setCommand must be called with a valid command.
     */
    public DoorCommand() {}

    /**
     * Creates a new DoorCommand.
     * 
     * @param command command to perform.
     */
    public DoorCommand(Command command) {
        assert command != null;
        this.command = command;
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
     * Enum for the valid commands to perform.
     */
    public enum Command {
        OPEN,
        CLOSE
    }
}
