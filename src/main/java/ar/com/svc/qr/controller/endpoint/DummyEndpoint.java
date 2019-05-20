package ar.com.svc.qr.controller.endpoint;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;

@Component
@Path("/dummy")
@Api(value = "/dummy", description = "Dummy token and validation services")
public class DummyEndpoint {

    private static final Logger LOGGER = LoggerFactory.getLogger(DummyEndpoint.class);

    private ObjectMapper objectMapper = new ObjectMapper();

    @POST
    @Path("/token")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @ApiOperation(value = "Dummy endpoint that validates a given token.", notes = "This action can only be done by a valid token.", response = Response.class)
    public Response token(@ApiParam(value = "Token to validate", required = true) String token) throws IOException {
        return Response.status(Response.Status.OK.getStatusCode()).entity(objectMapper.readTree("{\"valid\": true}")).build();
    }

    @POST
    @Path("/validate")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @ApiOperation(value = "Dummy endpoint that validates a token and entity data.", notes = "This action can only be done by a valid token.", response = Response.class)
    public Response validation(@ApiParam(value = "Body with entity data and token to validate", required = true) JsonNode body) throws IOException {
        return Response.status(Response.Status.OK.getStatusCode()).entity(objectMapper.readTree("{\"valid\": true}")).build();
    }
}
