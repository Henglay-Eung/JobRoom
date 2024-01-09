package com.job_room.annoouncement_service.model.feedback;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "jr_feedbacks")
public class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "user_id")
    private int userId;
    String username;
    private String content;
    private Timestamp created;
    public Feedback(){}

    public Feedback(int id, int userId, String username, String content, Timestamp created) {
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
