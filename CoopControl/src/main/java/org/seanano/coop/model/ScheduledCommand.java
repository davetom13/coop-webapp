package org.seanano.coop.model;

/**
 * Bean class representing a command to be performed at a specific time.
 *
 * @param <T> type of command that is scheduled.
 */
public class ScheduledCommand<T extends SchedulableCommand> {
    private T command;
    private long scheduledTime;

    /**
     * Sets the command to be performed.
     * 
     * @param command command to be performed
     */
    public void setCommand(T command) {
        this.command = command;
    }

    /**
     * Gets the command to be performed.
     * 
     * @return command to be performed
     */
    public T getCommand() {
        return command;
    }

    /**
     * Sets the time the command will be performed.
     * 
     * @param scheduledTime time the command will be performed, in millis since the epoch
     */
    public void setScheduledTime(long scheduledTime) {
        this.scheduledTime = scheduledTime;
    }

    /**
     * Gets the time the command will be performed.
     * 
     * @return time the command will be performed, in millis since the epoch
     */
    public long getScheduledTime() {
        return scheduledTime;
    }
}
