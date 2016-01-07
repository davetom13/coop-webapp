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

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * Rest service handling top level coop calls.
 */
@Api(value = "Coop")
@Path("/coops")
public class CoopService {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Retrieve information about all the attached coops.",  response = Coop.class,
            responseContainer = "List")
    @ApiResponse(code = 500, message = "An error occurred getting info about the coops.")
    public Collection<Coop> handleGetCoop() throws Exception {
        return Arrays.asList(new Coop[] { CoopFactory.getCoop() });
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Retrieve information about a specific coop.")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "Coop not found."),
            @ApiResponse(code = 500, message = "An error occurred getting info about the coop.")})
    public Coop handleGetCoop(@ApiParam(value = "Coop identifier", required = true) @PathParam("id") Integer id)
            throws Exception {
        if (id != 0) {
            throw new WebApplicationException(404);
        }
        return CoopFactory.getCoop();
    }
}
