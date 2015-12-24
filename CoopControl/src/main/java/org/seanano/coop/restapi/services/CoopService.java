package org.seanano.coop.restapi.services;

import java.util.Arrays;
import java.util.Collection;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;

import org.seanano.coop.hardware.CoopFactory;
import org.seanano.coop.model.Coop;

/**
 * Rest service handling top level coop calls.
 */
@Path("/coops")
public class CoopService {
    /**
     * Gets all of the known coops.
     * 
     * @return collection of known coops
     * @throws Exception if unable to get the coops
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Collection<? extends Coop> handleGetCoop() throws Exception {
        return Arrays.asList(new Coop[] { CoopFactory.getCoop() });
    }

    /**
     * Gets a specific coop.
     * 
     * @param id identifier of the coop to retrieve
     * @return specified coop
     * @throws Exception if coop was not found or unable to retrieve
     */
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Coop handleGetCoop(@PathParam("id") Integer id) throws Exception {
        if (id != 0) {
            throw new WebApplicationException(404);
        }
        return CoopFactory.getCoop();
    }
}
