package ar.com.svc.qr.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class ValidationExceptionHandler implements ExceptionMapper<ValidationError> {
    public Response toResponse(ValidationError ex) {
        return Response.status(Response.Status.BAD_REQUEST.getStatusCode()).build();
    }
}