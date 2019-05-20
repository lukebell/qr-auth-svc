package ar.com.svc.qr.controller.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

@XmlRootElement
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(value = "ConfigDTO", description = "Config DTO")
public class ConfigDTO {

    private Long id;

    private String clientCode;

    private String authURL;

    private String validateURL;

    private Integer size;

    private Long ttl;

    private Long createdBy;

    private Date createdDate;

    public ConfigDTO() {

    }

    public ConfigDTO(Long id, String clientCode, String authURL, String validateURL, Integer size, Long ttl, Long createdBy, Date createdDate) {
        this.id = id;
        this.clientCode = clientCode;
        this.authURL = authURL;
        this.validateURL = validateURL;
        this.size = size;
        this.ttl = ttl;
        this.createdBy = createdBy;
        this.createdDate = createdDate;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    @Override
    public String toString() {
        return "ConfigDTO{" +
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
