package com.job_room.schedule_service.model;

public class User {
    private String url = "https://gateway.kshrd-ite.com/root/post/oauth2/token";
    private String client_id = "6y1yGsCwJCn9mmbrs6Vh7LFW7H8dFO5m";
    private String redirectUri = "http://35.197.132.204:31000/home";
    private String username = "jobroom";
    private String password = "jobroom";
    private String client_secret = "VbI7nYEG2lD1ct65rILNp9sdh9aALIar";
    private String provision_key = "AU3p0FF7yU4mA8thdUGG8xdpOV45Iwjb";
    private String authenticated_userid = "jobroom";
    private String scope = "email";
    private String grant_type = "password";

    public User() {
    }

    public User(String url, String client_id, String redirectUri, String username, String password, String client_secret, String provision_key, String authenticated_userid, String scope, String grant_type) {
        this.url = url;
        this.client_id = client_id;
        this.redirectUri = redirectUri;
        this.username = username;
        this.password = password;
        this.client_secret = client_secret;
        this.provision_key = provision_key;
        this.authenticated_userid = authenticated_userid;
        this.scope = scope;
        this.grant_type = grant_type;
    }

    public String getProvision_key() {
        return provision_key;
    }

    public void setProvision_key(String provision_key) {
        this.provision_key = provision_key;
    }

    public String getGrant_type() {
        return grant_type;
    }

    public void setGrant_type(String grant_type) {
        this.grant_type = grant_type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }

    public String getClient_secret() {
        return client_secret;
    }

    public void setClient_secret(String client_secret) {
        this.client_secret = client_secret;
    }

    public String getRedirectUri() {
        return redirectUri;
    }

    public void setRedirectUri(String redirectUri) {
        this.redirectUri = redirectUri;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }



    public String getAuthenticated_userid() {
        return authenticated_userid;
    }

    public void setAuthenticated_userid(String authenticated_userid) {
        this.authenticated_userid = authenticated_userid;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }
}
