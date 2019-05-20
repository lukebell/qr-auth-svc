package ar.com.svc.qr.controller.endpoint;

import ar.com.svc.qr.controller.dto.ConfigDTO;
import ar.com.svc.qr.controller.service.ConfigService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.List;

@Component
@Path("/configs")
@Api(value = "/configs", description = "Configuration for QR Auth API service")
public class ConfigEndpoint {

    @Autowired
    private ConfigService configService;

    @GET
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @ApiOperation(value = "Get all Configurations service.", response = List.class, responseContainer = "List")
    public Response getAllConfig() {

        List<ConfigDTO> configs = configService.getAllConfigs();
        GenericEntity<List<ConfigDTO>> response = new GenericEntity<List<ConfigDTO>>(configs) {};

        return Response.status(Response.Status.OK.getStatusCode())
                .header("X-Total-Count", configs.size())
                .header("Access-Control-Expose-Headers", "X-Total-Count")
                .entity(response).build();
    }

    @GET
    @Path("/{id}")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @ApiOperation(value = "Get Configuration by ID service.", response = ConfigDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Invalid client code supplied")
    })
    public Response getConfigById(@PathParam("id") Long id) {
        return Response.status(Response.Status.OK.getStatusCode()).entity(configService.getConfigById(id)).build();
    }

    @PUT
    @Path("/{id}")
    @ApiOperation(value = "Update Configuration by Client Code service")
    @ApiResponses(value = {
            @ApiResponse(code = 409, message = "Configuration not exists")
    })
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response updateConfig(@ApiParam(value = "Config ID that need to be updated", required = true) @PathParam("id") Long id,
                                 @ApiParam(value = "Configuration that need to be updated", required = true) ConfigDTO configDTO) {
        return Response.status(Response.Status.OK.getStatusCode()).entity(configService.updateConfig(id, configDTO)).build();
    }

    @DELETE
    @Path("/{id}")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Invalid client code supplied")
    })
    @ApiOperation(value = "Deletes Configuration by id service")
    public Response deleteConfig(@ApiParam(value = "Id that need to be deleted", required = true) @PathParam("id") Long id) {
        return Response.status(Response.Status.OK.getStatusCode()).entity(configService.deleteConfig(id)).build();
    }

    @POST
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @ApiOperation(value = "Create Configuration service", response = Boolean.class)
    @ApiResponses(value = {
            @ApiResponse(code = 409, message = "Configuration already exists")
    })
    public Response addConfig(@ApiParam(value = "Configuration that need to be added", required = true) ConfigDTO configDTO,
                              @Context UriInfo uriInfo) {
        return Response.status(Response.Status.CREATED.getStatusCode()).entity(configService.addConfig(configDTO)).build();
    }

}