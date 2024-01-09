package com.admin.admin_service.model;


import com.fasterxml.jackson.annotation.JsonManagedReference;


import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

public class PostResponse {
    private int id;
    private String caption;
    //    private String userId;
    private UserResponse userResponse;
    //    private String images;
    private String[] images;
    private boolean isShared;
    private int interest;
    private boolean isJobAnnouncement;
    private Timestamp createdDate;
    List<CommentDto> commentList;
    private boolean status;

    public PostResponse() {
    }

//    public PostResponse(int id, String caption, UserResponse userResponse, String[] images, boolean isShared, int interest, boolean isJobAnnouncement, Timestamp createdDate, List<Comment> commentList, boolean status) {
//        this.id = id;
//        this.caption = caption;
//        this.userResponse = userResponse;
//        this.images = images;
//        this.isShared = isShared;
//        this.interest = interest;
//        this.isJobAnnouncement = isJobAnnouncement;
//        this.createdDate = createdDate;
//        this.commentList = commentList;
//        this.status = status;
//    }


    public PostResponse(int id, String caption, UserResponse userResponse, String[] images, boolean isShared, int interest, boolean isJobAnnouncement, Timestamp createdDate, List<CommentDto> commentList, boolean status) {
        this.id = id;
        this.caption = caption;
        this.userResponse = userResponse;
        this.images = images;
        this.isShared = isShared;
        this.interest = interest;
        this.isJobAnnouncement = isJobAnnouncement;
        this.createdDate = createdDate;
        this.commentList = commentList;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public UserResponse getUserResponse() {
        return userResponse;
    }

    public void setUserResponse(UserResponse userResponse) {
        this.userResponse = userResponse;
    }

    public String[] getImages() {
        return images;
    }

    public void setImages(String[] images) {
        this.images = images;
    }

    public boolean isShared() {
        return isShared;
    }

    public void setShared(boolean shared) {
        isShared = shared;
    }

    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

    public int getInterest() {
        return interest;
    }

    public void setInterest(int interest) {
        this.interest = interest;
    }

    public boolean isJobAnnouncement() {
        return isJobAnnouncement;
    }

    public void setJobAnnouncement(boolean jobAnnouncement) {
        isJobAnnouncement = jobAnnouncement;
    }

//    @JsonManagedReference
//    public List<Comment> getCommentList() {
//        return commentList;
//    }
//
//    public void setCommentList(List<Comment> commentList) {
//        this.commentList = commentList;
//    }

//    @JsonManagedReference
    public List<CommentDto> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<CommentDto> commentList) {
        this.commentList = commentList;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                ", caption='" + caption + '\'' +
                ", " + userResponse +
                ", images=" + Arrays.toString(images) +
                ", isShared=" + isShared +
                ", interest=" + interest +
                ", isJobAnnouncement=" + isJobAnnouncement +
                ", createdDate=" + createdDate +
                ", commentList=" + commentList +
                ", status=" + status +
                '}';
    }
}
