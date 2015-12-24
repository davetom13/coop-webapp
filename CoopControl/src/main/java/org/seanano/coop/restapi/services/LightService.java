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
import org.seanano.coop.model.Light;
import org.seanano.coop.model.LightCommand;

/**
 * Rest service handling coop specific light requests.
 */
@Path("/coops/{coopId}/lights")
public class LightService {
    private Coop coop;

    /**
     * Creates a LightService for a specific coop.
     * 
     * @param coopId identifier of coop
     * @throws Exception if coop was not found or unable to retrieve
     */
    public LightService(@PathParam("coopId") Integer coopId) throws Exception {
        if (coopId != 0) {
            throw new WebApplicationException(404);
        }
        coop = CoopFactory.getCoop();
    }

    /**
     * Gets all the lights associated with the coop.
     * 
     * @return all lights associated with coop
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Collection<? extends Light> handleGetLights() {
        return coop.getLights();
    }

    /**
     * Gets a specific light for the coop.
     * 
     * @param id identifier of light for the coop
     * @return requested light
     * @throws Exception if requested light not found
     */
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Light handleGetLight(@PathParam("id") Integer id) throws Exception {
        Light light = coop.getLight(id);
        if (light == null) {
            throw new WebApplicationException(404);
        }
        return light;
    }

    /**
     * Performs a command on a specific light for the coop.
     * 
     * @param id identifier of light for the coop
     * @param command command to perform
     * @throws Exception if requested light not found or unable to perform the command
     */
    @POST
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void handleLightCommand(@PathParam("id") Integer id, LightCommand command) throws Exception {
        Light light = coop.getLight(id);
        if (light == null) {
            throw new WebApplicationException(404);
        }

        if (!light.isControllable()) {
            throw new WebApplicationException(405);
        }

        // TODO log this manual command
        coop.control(light, command);
    }
}
