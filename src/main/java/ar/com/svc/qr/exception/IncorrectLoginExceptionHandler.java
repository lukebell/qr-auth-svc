package ar.com.svc.qr.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class IncorrectLoginExceptionHandler implements ExceptionMapper<UserLoginIncorrectError> {
    public Response toResponse(UserLoginIncorrectError ex) {
        return Response.status(Response.Status.UNAUTHORIZED).build();
    }
}
