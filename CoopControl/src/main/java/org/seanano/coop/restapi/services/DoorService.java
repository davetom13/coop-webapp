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

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * Rest service handling coop specific door requests.
 */
@Api(value = "Door")
@Path("/coops/{coopId}/doors")
public class DoorService {
    private Coop coop;

    /**
     * Creates a DoorService for a specific coop.
     * 
     * @param coopId identifier of coop
     * @throws Exception if coop was not found or unable to retrieve
     */
    public DoorService(@ApiParam(value = "Coop identifier", required = true) @PathParam("coopId") Integer coopId)
            throws Exception {
        if (coopId != 0) {
            throw new WebApplicationException(404);
        }
        coop = CoopFactory.getCoop();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Retrieve information about all the doors in the specified coop.")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "Coop not found."),
            @ApiResponse(code = 500, message = "An error occurred getting info about the coop.")})
    public Collection<Door> handleGetDoors() {
        return coop.getDoors();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Retrieve information about a specific door in the specified coop.")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "Coop or door not found."),
            @ApiResponse(code = 500, message = "An error occurred getting info about the coop.")})
    public Door handleGetDoor(
            @ApiParam(value = "Coop specific door identifier", required = true) @PathParam("id") Integer id)
            throws Exception {
        Door door = coop.getDoor(id);
        if (door == null) {
            throw new WebApplicationException(404);
        }
        return door;
    }

    @POST
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Initiates a command on a specific door in the specified coop.", response = Door.class,
            responseContainer = "List")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "Coop or door not found."),
            @ApiResponse(code = 405, message = "Door is not controllable."),
            @ApiResponse(code = 500, message = "An error occurred getting info about the coop or performing the command.")})
    public void handleDoorCommand(
            @ApiParam(value = "Coop specific door identifier", required = true) @PathParam("id") Integer id,
            @ApiParam(value = "Command to perform", required = true) DoorCommand command)
            throws Exception {
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
