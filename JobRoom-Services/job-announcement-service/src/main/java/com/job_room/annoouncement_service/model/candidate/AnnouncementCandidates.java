package com.job_room.annoouncement_service.model.candidate;

import java.util.List;
import java.util.Map;

public class AnnouncementCandidates {
    private int id;
    private String uuid;
    private String position;
    private String caption;
    private String createdDate;
    private String publishedDate;
    private String closedDate;
    private String thumbnail;
    private String image;
    private String type;
    private String salaryRank;
    private String location;
    private int numberOfHiring;
    private boolean isShared;
    private boolean isBanned;
    private boolean isDraft;
    private int companyId;
    private boolean status;
    private List<CandidateResponseList> candidate;

    public AnnouncementCandidates(){}
    public AnnouncementCandidates(int id, String uuid, String position, String caption, String createdDate, String publishedDate, String closedDate, String thumbnail, String image, String type, String salaryRank, String location, int numberOfHiring, boolean isShared, boolean isBanned, boolean isDraft, int companyId, boolean status, List<CandidateResponseList> candidate) {
        this.id = id;
        this.uuid = uuid;
        this.position = position;
        this.caption = caption;
        this.createdDate = createdDate;
        this.publishedDate = publishedDate;
        this.closedDate = closedDate;
        this.thumbnail = thumbnail;
        this.image = image;
        this.type = type;
        this.salaryRank = salaryRank;
        this.location = location;
        this.numberOfHiring = numberOfHiring;
        this.isShared = isShared;
        this.isBanned = isBanned;
        this.isDraft = isDraft;
        this.companyId = companyId;
        this.status = status;
        this.candidate = candidate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
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

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public int getNumberOfHiring() {
        return numberOfHiring;
    }

    public void setNumberOfHiring(int numberOfHiring) {
        this.numberOfHiring = numberOfHiring;
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

    public boolean isDraft() {
        return isDraft;
    }

    public void setDraft(boolean draft) {
        isDraft = draft;
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

    public List<CandidateResponseList> getCandidate() {
        return candidate;
    }

    public void setCandidate(List<CandidateResponseList> candidate) {
        this.candidate = candidate;
    }

    @Override
    public String toString() {
        return "AnnouncementCandidates{" +
                "id=" + id +
                ", uuid='" + uuid + '\'' +
                ", position='" + position + '\'' +
                ", caption='" + caption + '\'' +
                ", createdDate='" + createdDate + '\'' +
                ", publishedDate='" + publishedDate + '\'' +
                ", closedDate='" + closedDate + '\'' +
                ", thumbnail='" + thumbnail + '\'' +
                ", image='" + image + '\'' +
                ", type='" + type + '\'' +
                ", salaryRank='" + salaryRank + '\'' +
                ", location='" + location + '\'' +
                ", numberOfHiring=" + numberOfHiring +
                ", isShared=" + isShared +
                ", isBanned=" + isBanned +
                ", isDraft=" + isDraft +
                ", companyId=" + companyId +
                ", status=" + status +
                ", candidate=" + candidate +
                '}';
    }
}
