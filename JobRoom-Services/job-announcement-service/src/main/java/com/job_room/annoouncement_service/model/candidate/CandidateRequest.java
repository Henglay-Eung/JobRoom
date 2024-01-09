package com.job_room.annoouncement_service.model.candidate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class CandidateRequest {

    @NotNull(message = "Announcement Id cannot be empty")
    private int announcementId;

    @NotNull(message = "Company Id cannot be empty")
    private int companyId;
    @NotNull(message = "Name cannot be empty")
    private String name;
    @NotNull(message = "Employee Id cannot be empty")
    private int employeeId;

    @NotBlank(message = "CV Link Id cannot be empty")
    private String cvLink;


    public CandidateRequest(){}

    public CandidateRequest(int announcementId,  int companyId,String name, int employeeId, String cvLink) {
        this.announcementId = announcementId;
        this.companyId = companyId;
        this.employeeId = employeeId;
        this.cvLink = cvLink;
        this.name=name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getCvLink() {
        return cvLink;
    }

    public void setCvLink(String cvLink) {
        this.cvLink = cvLink;
    }

    @Override
    public String toString() {
        return "CandidateRequest{" +
                "announcementId=" + announcementId +
                ", companyId=" + companyId +
                ", employeeId=" + employeeId +
                ", cvLink='" + cvLink + '\'' +
                '}';
    }
}
