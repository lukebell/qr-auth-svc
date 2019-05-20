package ar.com.svc.qr.controller.endpoint;

import ar.com.svc.qr.controller.dto.UserDTO;
import ar.com.svc.qr.controller.service.UserService;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.List;

@Component
@Path("/users")
@Api(value = "/users", description = "User Generation services")
public class UserEndpoint {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserEndpoint.class);

    @Autowired
    private UserService userService;

    @GET
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @ApiOperation(value = "Get all User service.", response = List.class, responseContainer = "List")
    public Response getAllUsers() {
        List<UserDTO> users = userService.getAllUsers();
        GenericEntity<List<UserDTO>> response = new GenericEntity<List<UserDTO>>(users) {};

        return Response.status(Response.Status.OK.getStatusCode())
                .header("X-Total-Count", users.size())
                .header("Access-Control-Expose-Headers", "X-Total-Count")
                .entity(response).build();
    }

    @GET
    @Path("/{id}")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @ApiOperation(value = "Get User by id service.", response = UserDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Invalid username supplied")
    })
    public Response getUserById(@PathParam("id") Long id) {
        return Response.status(Response.Status.OK.getStatusCode()).entity(userService.getUserById(id)).build();
    }

    @PUT
    @Path("/{id}")
    @ApiOperation(value = "Update User by id service")
    @ApiResponses(value = {
            @ApiResponse(code = 409, message = "User not exists")
    })
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response updateUser(@ApiParam(value = "Username that need to be updated", required = true) @PathParam("id") Long id,
                                 @ApiParam(value = "User that need to be updated", required = true) UserDTO userDTO) {
        return Response.status(Response.Status.OK.getStatusCode()).entity(userService.updateUser(id, userDTO)).build();
    }

    @DELETE
    @Path("/{id}")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Invalid client code supplied")
    })
    @ApiOperation(value = "Deletes User by id service")
    public Response deleteUser(@ApiParam(value = "Id that need to be deleted", required = true) @PathParam("id") Long id) {
        return Response.status(Response.Status.ACCEPTED.getStatusCode()).entity(userService.deleteUser(id)).build();
    }

    @POST
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @ApiOperation(value = "Create User service", response = Boolean.class)
    @ApiResponses(value = {
            @ApiResponse(code = 409, message = "User already exists")
    })
    public Response addUser(@ApiParam(value = "User that need to be added", required = true) UserDTO userDTO,
                              @Context UriInfo uriInfo) {
        UserDTO user = userService.addUser(userDTO);
        return Response.status(Response.Status.CREATED.getStatusCode()).entity(user).build();
    }

    @POST
    @Path("/authenticate")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @ApiOperation(value = "Validate login User service", response = Boolean.class)
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Invalid username supplied")
    })
    public Response validateUser(@ApiParam(value = "User that need to be validated", required = true) UserDTO userDTO,
                            @Context UriInfo uriInfo) {
        return Response.status(Response.Status.OK.getStatusCode()).entity(userService.validateUser(userDTO)).build();
    }

}
