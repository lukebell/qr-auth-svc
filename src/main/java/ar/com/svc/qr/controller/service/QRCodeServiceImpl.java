package ar.com.svc.qr.controller.service;

import ar.com.svc.qr.controller.dto.QRCodeDTO;
import ar.com.svc.qr.controller.validator.Validator;
import ar.com.svc.qr.exception.AuthException;
import ar.com.svc.qr.model.entity.Config;
import ar.com.svc.qr.model.entity.QRCode;
import ar.com.svc.qr.model.repository.ConfigRepository;
import ar.com.svc.qr.model.repository.QRCodeRepository;
import ar.com.svc.qr.util.QRCodeUtils;
import com.google.zxing.ChecksumException;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.WriterException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class QRCodeServiceImpl implements QRCodeService {

    private static final Logger LOGGER = LoggerFactory.getLogger(QRCodeServiceImpl.class);

    private static final String QR_DELIMITER_CHAR = ":";

    private static final String BODY_TOKEN = "token";

    @Autowired
    Validator validator;

    @Autowired
    ValidationService validationService;

    @Autowired
    QRCodeRepository qrCodeRepository;

    @Autowired
    ConfigRepository configRepository;

    @Override
    public synchronized QRCodeDTO generate(String clientCode, String token) {
        QRCodeDTO qrCodeDTO = new QRCodeDTO();
        try {
            Config config = configRepository.findByClientCode(clientCode);
            validator.checkConfigExists(config);
            String uUID = getValidUUID(config.getId());
            qrCodeRepository.createOrUpdate(new QRCode(config.getId(), token, uUID));
            String codeMessage = clientCode + QR_DELIMITER_CHAR + token + QR_DELIMITER_CHAR + uUID;
            qrCodeDTO.setQrCode(QRCodeUtils.generateQRCodeBase64(codeMessage, config.getSize()));
            qrCodeDTO.setUuid(uUID);
        } catch (WriterException | IOException | NoSuchAlgorithmException e) {
            LOGGER.error(e.getMessage());
            throw new AuthException();
        }
        return qrCodeDTO;
    }

    @Override
    public Boolean validate(String clientCode, String token, String data, QRCodeDTO qrCode) {
        Config config = configRepository.findByClientCode(clientCode);
        validator.checkConfigExists(config);
        validator.qrCodeValidationValidator(data, qrCode);
        String uUid;
        try {
            Map<String, Object> body = QRCodeUtils.json2map(data);
            if (StringUtils.isNotBlank(qrCode.getUuid())) {  // checks by UUID
                uUid = qrCode.getUuid();
            } else { // checks by QR Code
                String qrCodeText = QRCodeUtils.getQRCodeData(qrCode.getQrCode());
                if (StringUtils.isBlank(qrCodeText)
                        || StringUtils.containsNone(qrCodeText, QR_DELIMITER_CHAR)
                        || qrCodeText.split(QR_DELIMITER_CHAR).length != 3) {
                    return Boolean.FALSE;
                }

                String[] qrCodeValues = qrCodeText.split(QR_DELIMITER_CHAR);
                if (!qrCodeValues[0].equalsIgnoreCase(clientCode)
                        || !qrCodeValues[1].equalsIgnoreCase(token)) {
                    return Boolean.FALSE;
                }
                uUid = qrCodeValues[2];
            }
            // Sends to validation URL
            return validateToken(config, token, uUid, body);
        } catch (IOException e) {
            LOGGER.error("Error: Entity data must contain a valid JSON Object. " + e.getMessage());
            throw new AuthException();
        } catch (FormatException | ChecksumException | NotFoundException e) {
            LOGGER.error("Error: " + e.getMessage());
            throw new AuthException();
        }
    }

    /**
     * Generates a UUID and checks if exists by client code in the database
     *
     * @param clientCode The Client Code
     * @return UUID
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     */
    private String getValidUUID(Long clientCode) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        String uUID = QRCodeUtils.generateUUID();
        QRCode qrCode = qrCodeRepository.findByClientCodeAndUUID(clientCode, uUID);
        if (qrCode != null) {
            getValidUUID(clientCode);
        }
        return uUID;
    }

    /**
     * Checks DB QR Code given and if exists sends data to validate over validate URL
     *
     * @param config The client code config
     * @param token The token
     * @param uUID The UUID
     * @param body The Body with entity data
     * @return Valid or not
     */
    private Boolean validateToken(Config config, String token, String uUID, Map<String, Object> body) {
        QRCode qrCode = qrCodeRepository.findByClientCodeAndTokenAndUUID(config.getId(), token, uUID);
        if (qrCode == null) {
            return Boolean.FALSE;
        }
        body.put(BODY_TOKEN, token);
        return validationService.validate(config, body);
    }

    /**
     * Finds All QR Codes
     * @return List of QRCodes
     */
    public List<QRCodeDTO> getAll(Long clientCode, String query) {
        List<QRCodeDTO> qrCodeDTOS = new ArrayList<>();
        if(clientCode == null) {
            List<QRCode> qrList = qrCodeRepository.findAll(query);
            qrList.forEach(qr -> {
                Config config = configRepository.findById(qr.getId());
                setQR(qrCodeDTOS, qr, config);
            });
        } else {
            List<QRCode> qrList = qrCodeRepository.findByClientCode(clientCode, query);
            Config config = configRepository.findById(clientCode);
            qrList.forEach(qr -> {
                setQR(qrCodeDTOS, qr, config);
            });
        }

        return qrCodeDTOS;
    }

    private void setQR(List<QRCodeDTO> qrCodeDTOS, QRCode qr, Config config) {
        String codeMessage = config.getClientCode() + QR_DELIMITER_CHAR + qr.getToken() + QR_DELIMITER_CHAR + qr.getUuid();
        try {
            qrCodeDTOS.add(new QRCodeDTO(
                    QRCodeUtils.generateQRCodeBase64(codeMessage, config.getSize()),
                    qr.getUuid(),
                    qr.getCreatedDate()
            ));
        } catch (IOException | WriterException e) {
            LOGGER.error("Error: " + e.getMessage());
            throw new AuthException();
        }
    }

    public byte[] getQRImage(QRCodeDTO qrCodeDTO) {
        try {
            return QRCodeUtils.getImage(QRCodeUtils.b64DecodeImage(qrCodeDTO.getQrCode()));
        } catch (IOException e) {
            LOGGER.error("Error: " + e.getMessage());
            throw new AuthException();
        }
    }
}
