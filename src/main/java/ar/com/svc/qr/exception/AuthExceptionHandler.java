package ar.com.svc.qr.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class AuthExceptionHandler implements ExceptionMapper<AuthException> {

    @Override
    public Response toResponse(AuthException exception) {
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }
}
