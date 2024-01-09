package com.admin.admin_service.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.vladmihalcea.hibernate.type.array.StringArrayType;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

@Entity
@Table(name = "ce_posts")
@TypeDef(name = "string-array", typeClass = StringArrayType.class)
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(
            columnDefinition = "text"
    )
    private String caption;
    private String userId;
    @Type(type = "string-array")
    @Column(
            name = "images",
            columnDefinition = "text[]"
    )
    private String[] images;
    private boolean isShared;
    private int interest;
    @ApiModelProperty(required = false)
    private boolean isJobAnnouncement;
    @ApiModelProperty(required = false)
    private Timestamp createdDate;
    @ApiModelProperty(required = false)
    private boolean status;
    @OneToMany(cascade = CascadeType.ALL)
    List<Comment> commentList;

    public Post() {
    }

    public Post(int id, String caption, String userId, String[] images, boolean isShared, int interest, boolean isJobAnnouncement, Timestamp createdDate, boolean status, List<Comment> commentList) {
        this.id = id;
        this.caption = caption;
        this.userId = userId;
        this.images = images;
        this.isShared = isShared;
        this.interest = interest;
        this.isJobAnnouncement = isJobAnnouncement;
        this.createdDate = createdDate;
        this.status = status;
        this.commentList = commentList;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getInterest() {
        return interest;
    }

    public void setInterest(int interest) {
        this.interest = interest;
    }

//    @JsonManagedReference
    public List<Comment> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<Comment> commentList) {
        this.commentList = commentList;
    }

    public boolean isJobAnnouncement() {
        return isJobAnnouncement;
    }

    public void setJobAnnouncement(boolean jobAnnouncement) {
        isJobAnnouncement = jobAnnouncement;
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", caption='" + caption + '\'' +
                ", userId='" + userId + '\'' +
                ", images=" + Arrays.toString(images) +
                ", isShared=" + isShared +
                ", interest=" + interest +
                ", isJobAnnouncement=" + isJobAnnouncement +
                ", createdDate=" + createdDate +
                ", status=" + status +
                ", commentList=" + commentList +
                '}';
    }
}
