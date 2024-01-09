package com.job_room.annoouncement_service.model.announcement;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Map;

public class AnnouncementRequest {

    @JsonIgnore
    private int id;

    @NotBlank(message = "Position cannot be empty")
    private String position;


    @NotBlank(message = "Caption cannot be empty")
    private String caption;

    private Map<String,Object> form;

    @NotBlank(message = "Published date cannot be empty")
    private String publishedDate;

    @NotBlank(message = "Closed date cannot be empty")
    private String closedDate;

    @NotBlank(message = "Thumbnail cannot be empty")
    private String thumbnail;

    private String image;
    private String type;
    private String salaryRank;
    private String location;
    @NotNull(message = "number cannot empty")
    private int numberOfHiring;
    @NotNull(message = "Company Id cannot be empty")
    private int companyId;

    private boolean isShared;
    private boolean isBanned;
    private boolean isDraft;

    @JsonIgnore
    private boolean status;

    public AnnouncementRequest() {
    }

    public AnnouncementRequest(int id, @NotBlank(message = "Position cannot be empty") String position, @NotBlank(message = "Caption cannot be empty") String caption, Map<String, Object> form, @NotBlank(message = "Published date cannot be empty") String publishedDate, @NotBlank(message = "Closed date cannot be empty") String closedDate, @NotBlank(message = "Thumbnail cannot be empty") String thumbnail, String image, @NotBlank(message = "type cannot be empty") String type, @NotBlank(message = "salary rank cannot be empty") String salaryRank, @NotBlank(message = "location cannot be empty") String location, @NotNull(message = "number cannot empty") int numberOfHiring, @NotNull(message = "Company Id cannot be empty") int companyId, boolean isShared, boolean isBanned, boolean isDraft, boolean status) {
        this.id = id;
        this.position = position;
        this.caption = caption;
        this.form = form;
        this.publishedDate = publishedDate;
        this.closedDate = closedDate;
        this.thumbnail = thumbnail;
        this.image = image;
        this.type = type;
        this.salaryRank = salaryRank;
        this.location = location;
        this.numberOfHiring = numberOfHiring;
        this.companyId = companyId;
        this.isShared = isShared;
        this.isBanned = isBanned;
        this.isDraft = isDraft;
        this.status = status;
    }

    public String getSalaryRank() {
        return salaryRank;
    }

    public void setSalaryRank(String salaryRank) {
        this.salaryRank = salaryRank;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getNumberOfHiring() {
        return numberOfHiring;
    }

    public void setNumberOfHiring(int numberOfHiring) {
        this.numberOfHiring = numberOfHiring;
    }

    public boolean isDraft() {
        return isDraft;
    }

    public void setDraft(boolean draft) {
        isDraft = draft;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public Map<String, Object> getForm() {
        return form;
    }

    public void setForm(Map<String, Object> form) {
        this.form = form;
    }

    public String getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(String publishedDate) {
        this.publishedDate = publishedDate;
    }

    public String getClosedDate() {
        return closedDate;
    }

    public void setClosedDate(String closedDate) {
        this.closedDate = closedDate;
    }

    public boolean isShared() {
        return isShared;
    }

    public void setShared(boolean shared) {
        isShared = shared;
    }

    public boolean isBanned() {
        return isBanned;
    }

    public void setBanned(boolean banned) {
        isBanned = banned;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }


    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
