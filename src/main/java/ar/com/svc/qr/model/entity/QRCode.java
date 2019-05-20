package ar.com.svc.qr.model.entity;

import java.sql.Timestamp;

public class QRCode implements BaseEntity {

    private static final long serialVersionUID = 1L;

    private Long id;

    private Long clientCode;

    private String token;

    private String uuid;

    private Timestamp createdDate;

    public QRCode() {
    }

    public QRCode(Long clientCode, String token, String uuid) {
        this.clientCode = clientCode;
        this.token = token;
        this.uuid = uuid;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getClientCode() {
        return clientCode;
    }

    public void setClientCode(Long clientCode) {
        this.clientCode = clientCode;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

    @Override
    public String toString() {
        return "QRCode{" +
                "id=" + id +
                ", clientCode='" + clientCode + '\'' +
                ", token='" + token + '\'' +
                ", uuid='" + uuid + '\'' +
                ", createdDate=" + createdDate +
                '}';
    }
}
