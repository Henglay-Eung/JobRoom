package com.admin.admin_service.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.sql.Timestamp;
import java.util.List;

public class CommentResponse {
    private int id;
//    private String userId;
    private Post post;
    private String image;
    private String description;
    private Timestamp createdDate;
    List<ReplyDto> replyList;
    private boolean status;
    private UserResponse userResponse;
    public CommentResponse() {
    }

//    public CommentResponse(int id, String userId, Post post, String image, String description, Timestamp createdDate, List<Reply> replyList, boolean status) {
//        this.id = id;
//        this.userId = userId;
//        this.post = post;
//        this.image = image;
//        this.description = description;
//        this.createdDate = createdDate;
//        this.replyList = replyList;
//        this.status = status;
//    }


//    public CommentResponse(int id, String userId, Post post, String image, String description, Timestamp createdDate, List<Reply> replyList, boolean status, UserResponse userResponse) {
//        this.id = id;
//        this.userId = userId;
//        this.post = post;
//        this.image = image;
//        this.description = description;
//        this.createdDate = createdDate;
//        this.replyList = replyList;
//        this.status = status;
//        this.userResponse = userResponse;
//    }

    public CommentResponse(int id, Post post, String image, String description, Timestamp createdDate, List<ReplyDto> replyList, boolean status, UserResponse userResponse) {
        this.id = id;
        this.post = post;
        this.image = image;
        this.description = description;
        this.createdDate = createdDate;
        this.replyList = replyList;
        this.status = status;
        this.userResponse = userResponse;
    }

    public UserResponse getUserResponse() {
        return userResponse;
    }

    public void setUserResponse(UserResponse userResponse) {
        this.userResponse = userResponse;
    }

//    @JsonManagedReference
    public List<ReplyDto> getReplyList() {
        return replyList;
    }

    public void setReplyList(List<ReplyDto> replyList) {
        this.replyList = replyList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
//
//
//    public String getUserId() {
//        return userId;
//    }
//
//    public void setUserId(String userId) {
//        this.userId = userId;
//    }

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

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "CommentResponse{" +
                "id=" + id +
                ", " + post +
                ", image='" + image + '\'' +
                ", description='" + description + '\'' +
                ", createdDate=" + createdDate +
                ", replyList=" + replyList +
                ", status=" + status +
                ", " + userResponse +
                '}';
    }
}
