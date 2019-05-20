package ar.com.svc.qr.controller.filter.response;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

@Provider
public class LoggingResponseFilter implements ContainerResponseFilter {

    private static final Logger logger = LoggerFactory.getLogger(LoggingResponseFilter.class);

    @Override
    public void filter(ContainerRequestContext requestContext,
                       ContainerResponseContext responseContext) throws IOException {
        String method = requestContext.getMethod();

        logger.debug("Requesting " + method + " for path " + requestContext.getUriInfo().getPath());
        Object entity = responseContext.getEntity();
        if (entity != null) {
            logger.debug("Response " + new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(entity));
        }

    }

}