package ar.com.svc.qr.util;

import org.springframework.stereotype.Component;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;
import java.util.Map;

@Component
public class JerseyClient {

    private Client client = ClientBuilder.newClient();

    public Response post(String uri, String contentType, Map<String, Object> body) {
        return client
                .target(uri)
                .request(contentType)
                .post(Entity.entity(body, contentType));
    }

    public Response postJSON(String uri, String contentType, String body) {
        return client
                .target(uri)
                .request(contentType)
                .post(Entity.json(body));
    }

    public Response postXML(String uri, String contentType, String body) {
        return client
                .target(uri)
                .request(contentType)
                .post(Entity.xml(body));
    }

    public Response get(String uri, String contentType) {
        return client
                .target(uri)
                .request(contentType)
                .get(Response.class);
    }

    public Response get(String uri, String contentType, String id) {
        return client
                .target(uri)
                .path(id)
                .request(contentType)
                .get(Response.class);
    }

}