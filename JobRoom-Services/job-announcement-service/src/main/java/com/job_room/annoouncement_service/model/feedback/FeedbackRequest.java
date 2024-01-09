package com.job_room.annoouncement_service.model.feedback;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class FeedbackRequest {
    @NotNull(message = "user id cannot be empty")
    private int userId;
    @NotBlank(message = "username cannot be empty")
    String username;
    @NotBlank(message = "content cannot be empty")
    private String content;

    public FeedbackRequest() {
    }

    public FeedbackRequest( int userId, String username, String content) {

        this.userId = userId;
        this.username = username;
        this.content = content;
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
