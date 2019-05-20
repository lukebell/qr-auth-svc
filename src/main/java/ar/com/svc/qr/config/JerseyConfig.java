package ar.com.svc.qr.config;

import ar.com.svc.qr.controller.endpoint.ConfigEndpoint;
import ar.com.svc.qr.controller.endpoint.DummyEndpoint;
import ar.com.svc.qr.controller.endpoint.QRCodeEndpoint;
import ar.com.svc.qr.controller.endpoint.UserEndpoint;
import ar.com.svc.qr.controller.filter.authentication.AuthenticationTokenFilter;
import ar.com.svc.qr.controller.filter.cors.CORSFilter;
import ar.com.svc.qr.controller.filter.response.LoggingResponseFilter;
import ar.com.svc.qr.controller.filter.response.NotFoundResponseFilter;
import ar.com.svc.qr.exception.*;
import io.swagger.jaxrs.config.BeanConfig;
import io.swagger.jaxrs.listing.ApiListingResource;
import io.swagger.jaxrs.listing.SwaggerSerializers;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.ws.rs.ApplicationPath;

@Configuration
@Component
@ApplicationPath("/api")
public class JerseyConfig extends ResourceConfig {

    public JerseyConfig() {

        packages(
                "ar.com.svc.qr",
                "io.swagger.jaxrs.listing"
        );

        // Encoders & Mime
        //register(GZipEncoder.class);              // GZip
        //register(DeflateEncoder.class);           // Deflate
        //register(EncodingFilter.class);
        //register(MultiPartFeature.class);         // Multipart

        // Filters
        register(CORSFilter.class);                // CORS
        register(LoggingResponseFilter.class);     // Logging prettyprint response
        register(AuthenticationTokenFilter.class); // Authentication
        register(NotFoundResponseFilter.class);    // 404 filter
        //register(HttpMethodOverrideFilter.class);
        //register(CsrfProtectionFilter.class);

        // Custom Resources
        register(ConfigEndpoint.class);
        register(QRCodeEndpoint.class);
        register(DummyEndpoint.class);
        register(UserEndpoint.class);
        register(ExceptionHandler.class);
        register(AuthExceptionHandler.class);
        register(ValidationExceptionHandler.class);
        register(NotFoundExceptionHandler.class);
        register(AlreadyExistsExceptionHandler.class);

        // Swagger
        register(ApiListingResource.class);
        register(SwaggerSerializers.class);

        property("jersey.config.server.wadl.disableWadl", true);

        // Lets us get to static content like swagger
        property(ServletProperties.FILTER_STATIC_CONTENT_REGEX, "((/swaggerui/.*)|(.*\\.html))");
        property("jersey.config.server.tracing.type", "ON_DEMAND");
        property("jersey.config.server.tracing.threshold", "VERBOSE");
        property(ServletProperties.FILTER_FORWARD_ON_404, true);

        // Swagger Bean config
        BeanConfig beanConfig = new BeanConfig();
        beanConfig.setConfigId("qr-auth-svc");
        beanConfig.setTitle("QR Code Authentication API");
        beanConfig.setVersion("0.0.1-SNAPSHOT");
        beanConfig.setContact("lucas.campana@globalmeaning.com");
        beanConfig.setSchemes(new String[]{"http", "https"});
        beanConfig.setHost("0.0.0.0" + ":" + "8090");
        beanConfig.setResourcePackage("ar.com.svc.qr");
        beanConfig.setBasePath("/api");
        beanConfig.setPrettyPrint(true);
        beanConfig.setScan(true);
    }
}
