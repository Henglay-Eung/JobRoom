package com.job_room.annoouncement_service.model.candidate;

public class CandidateResponse {

    private int id;
    private int announcementId;
    private int companyId;
    private String name;
    private int employeeId;
    private String appliesDate;
    private String cvLink;
    private boolean status;
    public CandidateResponse(){}

    public CandidateResponse(int id, int announcementId, int companyId, String name, int employeeId, String appliesDate, String cvLink, boolean status) {
        this.id = id;
        this.announcementId = announcementId;
        this.companyId = companyId;
        this.name = name;
        this.employeeId = employeeId;
        this.appliesDate = appliesDate;
        this.cvLink = cvLink;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAnnouncementId() {
        return announcementId;
    }

    public void setAnnouncementId(int announcementId) {
        this.announcementId = announcementId;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public String getAppliesDate() {
        return appliesDate;
    }

    public void setAppliesDate(String appliesDate) {
        this.appliesDate = appliesDate;
    }

    public String getCvLink() {
        return cvLink;
    }

    public void setCvLink(String cvLink) {
        this.cvLink = cvLink;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "CandidateResponse{" +
                "id=" + id +
                ", announcementId=" + announcementId +
                ", companyId=" + companyId +
                ", employeeId=" + employeeId +
                ", appliesDate='" + appliesDate + '\'' +
                ", cvLink='" + cvLink + '\'' +
                ", status=" + status +
                '}';
    }
}
