package com.ksga.auth.model.user;

public class UserDetailsRequest {

    private String email;
    private String password;

    public UserDetailsRequest() {
    }

    public UserDetailsRequest(String email, String password) {
        this.email = email;
        this.password = password;
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
}
