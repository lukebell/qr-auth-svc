package ar.com.svc.qr.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class AlreadyExistsExceptionHandler implements ExceptionMapper<ResourceAlreadyExists> {
    public Response toResponse(ResourceAlreadyExists ex) {
        return Response.status(Response.Status.CONFLICT.getStatusCode()).build();
    }
}