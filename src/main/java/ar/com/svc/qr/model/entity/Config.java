package ar.com.svc.qr.model.entity;

import java.sql.Timestamp;

public class Config implements BaseEntity {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String clientCode;

    private String authURL;

    private String validateURL;

    private Integer size;

    private Long ttl;

    private Long createdBy;

    private Timestamp createdDate;

    public Config() {
    }

    public Config(String clientCode, String authURL, String validateURL, Integer size, Long ttl, Long createdBy) {
        this.clientCode = clientCode;
        this.authURL = authURL;
        this.validateURL = validateURL;
        this.size = size;
        this.ttl = ttl;
        this.createdBy = createdBy;
    }

    public Config(String clientCode, String authURL, String validateURL, Integer size, Long ttl) {
        this.clientCode = clientCode;
        this.authURL = authURL;
        this.validateURL = validateURL;
        this.size = size;
        this.ttl = ttl;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClientCode() {
        return clientCode;
    }

    public void setClientCode(String clientCode) {
        this.clientCode = clientCode;
    }

    public String getAuthURL() {
        return authURL;
    }

    public void setAuthURL(String authURL) {
        this.authURL = authURL;
    }

    public String getValidateURL() {
        return validateURL;
    }

    public void setValidateURL(String validateURL) {
        this.validateURL = validateURL;
    }


    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Long getTtl() {
        return ttl;
    }

    public void setTtl(Long ttl) {
        this.ttl = ttl;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

    @Override
    public String toString() {
        return "Config{" +
                "id=" + id +
                ", clientCode='" + clientCode + '\'' +
                ", authURL='" + authURL + '\'' +
                ", validateURL='" + validateURL + '\'' +
                ", size=" + size +
                ", ttl=" + ttl +
                ", createdBy=" + createdBy +
                ", createdDate=" + createdDate +
                '}';
    }
}
