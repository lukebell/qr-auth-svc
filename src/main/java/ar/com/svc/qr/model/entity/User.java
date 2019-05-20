package ar.com.svc.qr.model.entity;

import ar.com.svc.qr.controller.constant.UserRoles;

import java.sql.Timestamp;

public class User implements BaseEntity {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String username;

    private String fullName;

    private String password;

    private String email;

    private UserRoles role;

    private Timestamp createdDate;

    public User() {

    }

    public User(String fullName, String email, UserRoles role) {
        this.fullName = fullName;
        this.email = email;
        this.role = role;
    }

    public User(String username, String fullName, String password, String email, UserRoles role) {
        this.username = username;
        this.fullName = fullName;
        this.password = password;
        this.email = email;
        this.role = role;
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserRoles getRole() {
        return role;
    }

    public void setRole(UserRoles role) {
        this.role = role;
    }

    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", fullName='" + fullName + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", role=" + role +
                ", createdDate=" + createdDate +
                '}';
    }
}
