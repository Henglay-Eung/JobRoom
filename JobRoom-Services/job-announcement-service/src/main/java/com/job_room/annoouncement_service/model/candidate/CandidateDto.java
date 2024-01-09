package com.job_room.annoouncement_service.model.candidate;

public class CandidateDto {
    private int id;
    private int announcementId;
    private int companyId;
    private String name;
    private int employeeId;
    private String appliesDate;
    private String cvLink;
    private String email;
    private boolean status;
    public CandidateDto(){}

    public CandidateDto(int id, int announcementId, int companyId, String name, int employeeId, String appliesDate, String cvLink, String email, boolean status) {
        this.id = id;
        this.announcementId = announcementId;
        this.companyId = companyId;
        this.name = name;
        this.employeeId = employeeId;
        this.appliesDate = appliesDate;
        this.cvLink = cvLink;
        this.email = email;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
