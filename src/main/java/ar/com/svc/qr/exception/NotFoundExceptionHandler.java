package ar.com.svc.qr.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class NotFoundExceptionHandler implements ExceptionMapper<ResourceNotFound> {
    public Response toResponse(ResourceNotFound ex) {
        return Response.status(Response.Status.NOT_FOUND).build();
    }
}
