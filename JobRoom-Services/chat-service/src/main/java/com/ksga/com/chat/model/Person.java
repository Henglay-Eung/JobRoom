package com.ksga.com.chat.model;

import javax.persistence.*;

@Entity
@Table(name = "tb_user")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String sessionId;
    private String username;

    public Person() {

    }

    public Person(int id, String sessionId, String username) {
        this.id = id;
        this.sessionId = sessionId;
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }


    @Override
    public String toString() {
        return "User [sessionId=" + sessionId + ", username=" + username + "]";
    }

}
