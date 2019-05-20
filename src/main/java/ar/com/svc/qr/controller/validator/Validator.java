package ar.com.svc.qr.controller.validator;

import ar.com.svc.qr.controller.dto.ConfigDTO;
import ar.com.svc.qr.controller.dto.QRCodeDTO;
import ar.com.svc.qr.exception.ValidationError;
import ar.com.svc.qr.model.entity.Config;
import ar.com.svc.qr.util.QRCodeUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.ParseException;

@Component
public class Validator {

    private static final Logger LOGGER = LoggerFactory.getLogger(Validator.class);

    public void tokenValidator(String clientCode, String token) {
        validateStringParam(clientCode);
        validateStringParam(token);
    }

    public void addConfigValidator(ConfigDTO config) {

    }

    public void checkConfigExists(Config config) {
        if(config == null) {
            LOGGER.error("Error: " + "Configuration not found");
            throw new ValidationError();
        }
    }

    public void qrCodeValidationValidator(String data, QRCodeDTO qrCode) {
        if(StringUtils.isBlank(data)) {
            LOGGER.error("Error: Entity data is required");
            throw new ValidationError();
        }
        if(StringUtils.isBlank(qrCode.getQrCode()) && StringUtils.isBlank(qrCode.getUuid())) {
            LOGGER.error("Error: UUID or QR Code is required");
            throw new ValidationError();
        }
    }

    private void validateStringParam(String value) {
        if(StringUtils.isBlank(value)) {
            LOGGER.error("Error: Request value is required [" + value + "]");
            throw new ValidationError();
        }
    }
}
