package org.seanano.coop.model;

/**
 * Abstract Door representation. Handles everything but isControllable() and getNextScheduledCommand().
 */
public abstract class AbstractDoor implements Door {
    private final Integer id;
    private final String description;
    private final DoorState state;

    /**
     * Creates a new AbstractDoor.
     * 
     * @param id identifier of door
     * @param description human readable description of door
     * @param state state of door
     */
    public AbstractDoor(Integer id, String description, DoorState state) {
        this.id = id;
        this.description = description;
        this.state = state;
    }

    /**
     * Creates a new AbstractDoor from an existing Door and a new state.
     * 
     * @param door existing door
     * @param state new state of door
     */
    public AbstractDoor(Door door, DoorState state) {
        this.id = door.getId();
        this.description = door.getDescription();
        this.state = state;
    }

    @Override
    public Integer getId() { return id; }

    @Override
    public String getDescription() { return description; }

    @Override
    public DoorState getState() { return state; }
}
