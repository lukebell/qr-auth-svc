package ar.com.svc.qr.controller.service;

import ar.com.svc.qr.controller.dto.QRCodeDTO;

import java.util.List;

public interface QRCodeService {

    QRCodeDTO generate(String clientCode, String token);

    Boolean validate(String clientCode, String token, String data, QRCodeDTO qrCode);

    List<QRCodeDTO> getAll(Long clientCode, String q);

    byte[] getQRImage(QRCodeDTO qrCodeDTO);
}

