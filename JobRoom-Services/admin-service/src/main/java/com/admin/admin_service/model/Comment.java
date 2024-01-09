package com.admin.admin_service.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "ce_comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String userId;
    @ManyToOne
    private Post post;
    private String image;
    private String description;
    private Timestamp createdDate;
    private boolean status;
    @OneToMany()
    List<Reply> replyList;

    public Comment(){}

    public Comment(int id, String userId, Post post, String image, String description, Timestamp createdDate, boolean status, List<Reply> replyList) {
        this.id = id;
        this.userId = userId;
        this.post = post;
        this.image = image;
        this.description = description;
        this.createdDate = createdDate;
        this.status = status;
        this.replyList = replyList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

    @JsonBackReference
    public Post getPost() {
        return post;
    }

    public void setPost(Post post_id) {
        this.post = post_id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

//    @JsonManagedReference throw MappingJackson2HttpMessageConverter error, so i remove it from every class
//    @JsonManagedReference
    public List<Reply> getReplyList() {
        return replyList;
    }

    public void setReplyList(List<Reply> replyList) {
        this.replyList = replyList;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", userId='" + userId + '\'' +
                ", post=" + post +
                ", image='" + image + '\'' +
                ", description='" + description + '\'' +
                ", createdDate=" + createdDate +
                ", status=" + status +
                ", replyList=" + replyList +
                '}';
    }
}
