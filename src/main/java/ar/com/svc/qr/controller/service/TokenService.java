package ar.com.svc.qr.controller.service;

import ar.com.svc.qr.controller.validator.Validator;
import ar.com.svc.qr.exception.AuthException;
import ar.com.svc.qr.model.entity.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;

@Service
public class TokenService extends ExternalService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TokenService.class);

    private static final String BODY_TOKEN = "token";

    @Autowired
    Validator validator;

    public boolean validate(String clientCode, String token) {
        try {
            validator.tokenValidator(clientCode, token);
            Config config = configRepository.findByClientCode(clientCode);
            if(config == null) {
                LOGGER.warn("Warning: Client Code: " + clientCode + " doesn't exists.");
                return Boolean.FALSE;
            }
            Map<String, Object> body = new HashMap<>();
            body.put(BODY_TOKEN, token);
            Response response = jerseyClient.post(config.getAuthURL(), MediaType.APPLICATION_JSON, body);
            Boolean result = (Boolean) response.readEntity(Map.class).get("valid");
            return result != null ? result : Boolean.FALSE;
        } catch (Exception e) {
            LOGGER.error("Error: " + e.getMessage());
            throw new AuthException();
        }
    }
}
