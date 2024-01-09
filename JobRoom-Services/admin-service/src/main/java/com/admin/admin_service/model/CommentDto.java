package com.admin.admin_service.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.sql.Timestamp;
import java.util.List;

public class CommentDto {
    private int id;
    private String userId;
    private Post post;
    private String image;
    private String description;
    private Timestamp createdDate;
    private boolean status;
    List<ReplyDto> replyList;
    private UserResponse userResponse;

    public CommentDto() {
    }


    public CommentDto(int id, String userId, Post post, String image, String description, Timestamp createdDate, boolean status, List<ReplyDto> replyList, UserResponse userResponse) {
        this.id = id;
        this.userId = userId;
        this.post = post;
        this.image = image;
        this.description = description;
        this.createdDate = createdDate;
        this.status = status;
        this.replyList = replyList;
        this.userResponse = userResponse;
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

    @JsonBackReference
    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
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

    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

    public boolean isStatus() {
        return status;
    }

//    @JsonManagedReference
    public List<ReplyDto> getReplyList() {
        return replyList;
    }

    public void setReplyList(List<ReplyDto> replyList) {
        this.replyList = replyList;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public UserResponse getUserResponse() {
        return userResponse;
    }

    public void setUserResponse(UserResponse userResponse) {
        this.userResponse = userResponse;
    }

    @Override
    public String toString() {
        return "CommentDto{" +
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
