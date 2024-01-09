package com.admin.admin_service.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "ce_replies")
public class Reply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    private Comment comment;
    private String userId;
    private String description;
    private Timestamp createdDate;
    private boolean status;

    public Reply() {
    }


    public Reply(int id, Comment comment, String userId, String description, Timestamp createdDate, boolean status) {
        this.id = id;
        this.comment = comment;
        this.userId = userId;
        this.description = description;
        this.createdDate = createdDate;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @JsonBackReference
    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Reply{" +
                "id=" + id +
                ", comment=" + comment +
                ", userId=" + userId +
                ", description='" + description + '\'' +
                ", createdDate=" + createdDate +
                ", status=" + status +
                '}';
    }
}
