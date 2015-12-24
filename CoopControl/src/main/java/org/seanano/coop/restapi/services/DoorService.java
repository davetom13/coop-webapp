package org.seanano.coop.restapi.services;

import java.util.Collection;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;

import org.seanano.coop.hardware.CoopFactory;
import org.seanano.coop.model.Coop;
import org.seanano.coop.model.Door;
import org.seanano.coop.model.DoorCommand;

/**
 * Rest service handling coop specific door requests.
 */
@Path("/coops/{coopId}/doors")
public class DoorService {
    private Coop coop;

    /**
     * Creates a DoorService for a specific coop.
     * 
     * @param coopId identifier of coop
     * @throws Exception if coop was not found or unable to retrieve
     */
    public DoorService(@PathParam("coopId") Integer coopId) throws Exception {
        if (coopId != 0) {
            throw new WebApplicationException(404);
        }
        coop = CoopFactory.getCoop();
    }

    /**
     * Gets all the doors associated with the coop.
     * 
     * @return all doors associated with coop
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Collection<? extends Door> handleGetDoors() {
        return coop.getDoors();
    }

    /**
     * Gets a specific door for the coop.
     * 
     * @param id identifier of door for the coop
     * @return requested door
     * @throws Exception if requested door not found
     */
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Door handleGetDoor(@PathParam("id") Integer id) throws Exception {
        Door door = coop.getDoor(id);
        if (door == null) {
            throw new WebApplicationException(404);
        }
        return door;
    }

    /**
     * Performs a command on a specific door for the coop.  Note this call will return once the command is started.
     * 
     * @param id identifier of door for the coop
     * @param command command to perform
     * @throws Exception if requested door not found or unable to perform the command
     */
    @POST
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void handleDoorCommand(@PathParam("id") Integer id, DoorCommand command) throws Exception {
        Door door = coop.getDoor(id);
        if (door == null) {
            throw new WebApplicationException(404);
        }

        if (!door.isControllable()) {
            throw new WebApplicationException(405);
        }

        // TODO log this manual command
        coop.control(door, command);
    }
}
