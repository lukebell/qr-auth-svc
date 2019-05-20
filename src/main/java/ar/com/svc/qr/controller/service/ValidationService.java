package ar.com.svc.qr.controller.service;

import ar.com.svc.qr.exception.AuthException;
import ar.com.svc.qr.model.entity.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Map;

@Service
public class ValidationService extends ExternalService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ValidationService.class);

    public boolean validate(Config config, Map<String, Object> body) {
        try {
            Response response = jerseyClient.post(config.getValidateURL(), MediaType.APPLICATION_JSON, body);
            Boolean result = (Boolean) response.readEntity(Map.class).get("valid");
            return result != null ? result : Boolean.FALSE;
        } catch (Exception e) {
            LOGGER.error("Error: " + e.getMessage());
            throw new AuthException();
        }
    }
}
