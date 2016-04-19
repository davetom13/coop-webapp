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
import org.seanano.coop.model.Camera;
import org.seanano.coop.model.CameraCommand;
import org.seanano.coop.model.Coop;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * Rest service handling coop specific camera requests.
 */
@Api(value = "Camera")
@Path("/coops/{coopId}/cameras")
public class CameraService {
    private Coop coop;

    /**
     * Creates a CameraService for a specific coop.
     * 
     * @param coopId identifier of coop
     * @throws Exception if coop was not found or unable to retrieve
     */
    public CameraService(@ApiParam(value = "Coop identifier", required = true) @PathParam("coopId") Integer coopId)
            throws Exception {
        if (coopId != 0) {
            throw new WebApplicationException(404);
        }
        coop = CoopFactory.getCoop();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Retrieve information about all the cameras in the specified coop.")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "Coop not found."),
            @ApiResponse(code = 500, message = "An error occurred getting info about the coop.")})
    public Collection<Camera> handleGetCameras() {
        return coop.getCameras();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Retrieve information about a specific camera in the specified coop.")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "Coop or camera not found."),
            @ApiResponse(code = 500, message = "An error occurred getting info about the coop.")})
    public Camera handleGetCamera(
            @ApiParam(value = "Coop specific camera identifier", required = true) @PathParam("id") Integer id)
            throws Exception {
        Camera camera = coop.getCamera(id);
        if (camera == null) {
            throw new WebApplicationException(404);
        }
        return camera;
    }

    @POST
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Initiates a command on a specific camera in the specified coop.")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "Coop or camera not found."),
            @ApiResponse(code = 405, message = "Camera is not controllable."),
            @ApiResponse(code = 500, message = "An error occurred getting info about the coop or performing the command.")})
    public void handleCameraCommand(@PathParam("id") Integer id, CameraCommand[] commands) throws Exception {
        Camera camera = coop.getCamera(id);
        if (camera == null) {
            throw new WebApplicationException(404);
        }

        if (!camera.isControllable()) {
            throw new WebApplicationException(405);
        }

        // TODO log this manual command
        coop.control(camera, commands);
    }

}
