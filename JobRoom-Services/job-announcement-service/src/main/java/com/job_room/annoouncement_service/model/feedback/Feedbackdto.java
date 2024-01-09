package com.job_room.annoouncement_service.model.feedback;

import java.sql.Timestamp;

public class Feedbackdto {
    private int id;
    private int userId;
    String username;
    private String content;
    private Timestamp created;
    public Feedbackdto() {
    }

    public Feedbackdto(int id, int userId, String username, String content, Timestamp created) {
        this.id = id;
        this.userId = userId;
        this.username = username;
        this.content = content;
        this.created = created;
    }

    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
