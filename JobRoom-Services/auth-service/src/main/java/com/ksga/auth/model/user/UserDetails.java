package com.ksga.auth.model.user;

import javax.persistence.*;

@Entity
@Table(name = "auth")
public class UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String email;
    private String password;
    private String role;


    private int userId;
    private boolean isBanned;
    private boolean status;

    public UserDetails() {
    }

    public UserDetails(int id, String email, String password, String role, int userId, boolean isBanned, boolean status) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.role = role;
        this.userId = userId;
        this.isBanned = isBanned;
        this.status = status;
    }

    public String getEmail() {
        return email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isBanned() {
        return isBanned;
    }

    public void setBanned(boolean banned) {
        isBanned = banned;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "UserDetails{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                ", userId=" + userId +
                ", isBanned=" + isBanned +
                ", status=" + status +
                '}';
    }
}
