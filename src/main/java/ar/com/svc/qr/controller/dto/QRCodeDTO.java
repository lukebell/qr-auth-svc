package ar.com.svc.qr.controller.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

@XmlRootElement
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(value = "QRCodeDTO", description = "QR Code DTO")
public class QRCodeDTO {

    private String qrCode;

    private String uuid;

    private Boolean isValid;

    private Date createdDate;

    public QRCodeDTO() {
    }

    public QRCodeDTO(String qrCode, String uuid, Date createdDate) {
        this.qrCode = qrCode;
        this.uuid = uuid;
        this.createdDate = createdDate;
    }

    public QRCodeDTO(String qrCode, String uuid, Boolean isValid) {
        this.qrCode = qrCode;
        this.uuid = uuid;
        this.isValid = isValid;
    }

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Boolean isValid() {
        return isValid;
    }

    public void setIsValid(Boolean isValid) {
        this.isValid = isValid;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    @Override
    public String toString() {
        return "QRCodeDTO{" +
                "qrCode='" + qrCode + '\'' +
                ", uuid='" + uuid + '\'' +
                ", isValid=" + isValid +
                ", createdDate=" + createdDate +
                '}';
    }
}
