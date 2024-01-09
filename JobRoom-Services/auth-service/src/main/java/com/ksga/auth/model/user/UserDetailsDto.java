package com.ksga.auth.model.user;


public class UserDetailsDto {

    private int id;

    private String email;
    private String password;
    private String role;

    private int userId;
    private boolean isBanned;
    private boolean status;

    public UserDetailsDto() {
    }

    public UserDetailsDto(int id, String email, String password, String role, int userId, boolean isBanned, boolean status) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.role = role;
        this.userId = userId;
        this.isBanned = isBanned;
        this.status = status;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}

