package com.job_room.annoouncement_service.model.candidate;

import com.job_room.annoouncement_service.model.announcement.AnnouncementResponse;

public class CandidateResponseList {
    private int id;
    private int announcementId;
    private int companyId;
    private int employeeId;
    private CandidateEmployee employee;
    private String appliesDate;
    private String cvLink;
    private boolean status;
    public CandidateResponseList(){}

    public CandidateResponseList(int id, int announcementId, int companyId, int employeeId, CandidateEmployee employee, String appliesDate, String cvLink, boolean status) {
        this.id = id;
        this.announcementId = announcementId;
        this.companyId = companyId;
        this.employeeId = employeeId;
        this.employee = employee;
        this.appliesDate = appliesDate;
        this.cvLink = cvLink;
        this.status = status;
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

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public CandidateEmployee getEmployee() {
        return employee;
    }

    public void setEmployee(CandidateEmployee employee) {
        this.employee = employee;
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

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }
}
