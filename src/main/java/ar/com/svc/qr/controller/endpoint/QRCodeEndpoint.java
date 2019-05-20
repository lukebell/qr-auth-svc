package ar.com.svc.qr.controller.endpoint;

import ar.com.svc.qr.controller.dto.QRCodeDTO;
import ar.com.svc.qr.controller.service.QRCodeService;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Component
@Path("/qr")
@Api(value = "/qr", description = "QR Code User Generation and Authentication services")
public class QRCodeEndpoint {

    private static final Logger LOGGER = LoggerFactory.getLogger(QRCodeEndpoint.class);

    @Autowired
    private QRCodeService qRCodeService;

    @POST
    @Path("/generate")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @ApiOperation(value = "Generates QR Code and UUID service.", notes = "This action can only be done by a valid token and client code.", response = QRCodeDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Created", response = QRCodeDTO.class),
            @ApiResponse(code = 400, message = "Invalid header token supplied"),
            @ApiResponse(code = 400, message = "Invalid header clientCode supplied")
    })
    public Response generateQRCode(@ApiParam(value = "Valid token to get a QR Code and UUID token", required = true) @HeaderParam("token") String token,
                                   @ApiParam(value = "Valid Client Code to get a QR Code and UUID token", required = true) @HeaderParam("clientCode") String clientCode) {
        QRCodeDTO qrCodeDTO = qRCodeService.generate(clientCode, token);
        return Response.status(Response.Status.CREATED.getStatusCode()).entity(qrCodeDTO).build();
    }

    @POST
    @Path("/validate")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @ApiOperation(value = "Validates QR Code and UUID service", notes = "This can only be done by the valid token and client code.", response = QRCodeDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Invalid header token or client code supplied"),
            @ApiResponse(code = 409, message = "Invalid QR Code, UUID  or Data object supplied")
    })
    public Response validateQRCode(@ApiParam(value = "String JSON Entity data", name = "data", required = true) @HeaderParam("data") String data,
                                   @ApiParam(value = "Valid token to get a QR Code and UUID token", required = true) @HeaderParam("token") String token,
                                   @ApiParam(value = "Valid Client Code to get a QR Code and UUID token", required = true) @HeaderParam("clientCode") String clientCode,
                                   @ApiParam(value = "QRCode to validate", required = true) QRCodeDTO qrCode) {
        /*
          We should check if it is the best approach to send entity data in the request header as json string
          https://stackoverflow.com/a/40347926
         */
        qrCode.setIsValid(qRCodeService.validate(clientCode, token, data, qrCode));
        return Response.status(Response.Status.OK.getStatusCode()).entity(qrCode).build();
    }

    @GET
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @ApiOperation(value = "Get all QR Code by client code service.", response = List.class, responseContainer = "List")
    public Response getAllQRrs(@ApiParam(value = "Valid Client Code to get a QR Code") @QueryParam("clientCode") Long clientCode,
                               @ApiParam(value = "Query to find") @QueryParam("q") String q) {
        List<QRCodeDTO> qrCodes = qRCodeService.getAll(clientCode, q);
        GenericEntity<List<QRCodeDTO>> response = new GenericEntity<List<QRCodeDTO>>(qrCodes) {};

        return Response.status(Response.Status.OK.getStatusCode())
                .header("X-Total-Count", qrCodes.size())
                .header("Access-Control-Expose-Headers", "X-Total-Count")
                .entity(response).build();
    }

    @POST
    @Path("/image")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces("image/png")
    @ApiOperation(value = "Get QR Code image service.")
    public Response getQRrImage(@ApiParam(value = "Valid QR Code to get an image", required = true) QRCodeDTO qrCode) {
        return Response.status(Response.Status.OK.getStatusCode()).entity(qRCodeService.getQRImage(qrCode)).build();
    }

}
