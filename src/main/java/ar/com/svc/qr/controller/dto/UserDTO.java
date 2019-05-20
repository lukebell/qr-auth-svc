package ar.com.svc.qr.controller.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;

import javax.xml.bind.annotation.XmlRootElement;
import java.sql.Timestamp;
import java.util.Date;

@XmlRootElement
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(value = "UserDTO", description = "User DTO")
public class UserDTO {

    private Long id;

    private String fullName;

    private String username;

    private String password;

    private String email;

    private int role;

    private Date createdDate;

    public UserDTO() {

    }

    public UserDTO(Long id, String fullName, String username, String email, int role, Timestamp createdDate) {
        this.id = id;
        this.fullName = fullName;
        this.username = username;
        this.email = email;
        this.role = role;
        this.createdDate = createdDate;
    }

    public UserDTO(Long id, int role) {
        this.id = id;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
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

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", role=" + role +
                ", createdDate=" + createdDate +
                '}';
    }
}
