package ar.com.svc.qr.controller.filter.authentication;

import ar.com.svc.qr.controller.service.TokenService;
import org.glassfish.jersey.internal.util.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Priority;
import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.lang.reflect.Method;
import java.util.*;

/**
 * This filter verify the access permissions for a user
 * based on username and password provided in request
 */
@Provider
@Configuration
@Priority(Priorities.AUTHENTICATION)  // needs to happen before authorization
public class AuthenticationTokenFilter implements ContainerRequestFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationTokenFilter.class);

    @Context
    private ResourceInfo resourceInfo;

    @Autowired
    private TokenService tokenService;

    private static final String HEADER_TOKEN = "token";

    private static final String HEADER_CLIENT_CODE = "clientCode";

    private static final String AUTHORIZATION_HEADER = "Authorization";

    private static final String AUTHENTICATION_SCHEME = "Basic";


    private static final Response ACCESS_DENIED = Response.status(Response.Status.UNAUTHORIZED)
            .entity("You cannot access this resource").build();

    private static final Response ACCESS_FORBIDDEN = Response.status(Response.Status.FORBIDDEN)
            .entity("Access blocked for all users !!").build();

    @Override
    public void filter(ContainerRequestContext requestContext) {

        Method method = resourceInfo.getResourceMethod();
        //Access allowed for all
        if (!method.isAnnotationPresent(PermitAll.class)) {
            //Access denied for all
            if (method.isAnnotationPresent(DenyAll.class)) {
                requestContext.abortWith(ACCESS_FORBIDDEN);
                return;
            }

            //Get request headers
            final MultivaluedMap<String, String> headers = requestContext.getHeaders();
            final List<String> tokenHeader = headers.get(HEADER_TOKEN);
            final List<String> clientCodeHeader = headers.get(HEADER_CLIENT_CODE);

            if (tokenHeader != null && !tokenHeader.isEmpty()
                    && clientCodeHeader != null && !clientCodeHeader.isEmpty()) {

                final String clientCode = clientCodeHeader.get(0);
                final String token = tokenHeader.get(0);

                if (!tokenService.validate(clientCode, token)) {
                    requestContext.abortWith(ACCESS_DENIED);
                    return;
                }
            } else {

                final List<String> authorization = headers.get(AUTHORIZATION_HEADER);

                if (authorization == null || authorization.isEmpty()) {
                    //requestContext.abortWith(ACCESS_DENIED);
                    return;
                }

                final String encodedUserPassword = authorization.get(0).replaceFirst(AUTHENTICATION_SCHEME + " ", "");

                String usernameAndPassword = new String(Base64.decode(encodedUserPassword.getBytes()));

                final StringTokenizer tokenizer = new StringTokenizer(usernameAndPassword, ":");
                final String username = tokenizer.nextToken();
                final String password = tokenizer.nextToken();

                if (method.isAnnotationPresent(RolesAllowed.class)) {
                    RolesAllowed rolesAnnotation = method.getAnnotation(RolesAllowed.class);
                    Set<String> rolesSet = new HashSet<>(Arrays.asList(rolesAnnotation.value()));

                    if (!isUserAllowed(username, password, rolesSet)) {
                        requestContext.abortWith(ACCESS_DENIED);
                    }
                }
            }
        }
    }

    private boolean isUserAllowed(final String username, final String password, final Set<String> rolesSet) {
        boolean isAllowed = false;

        /**
         * TODO: user pass db access
         */
        return isAllowed;
    }
}