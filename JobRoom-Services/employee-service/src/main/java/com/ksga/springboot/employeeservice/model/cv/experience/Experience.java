package com.ksga.springboot.employeeservice.model.cv.experience;

import java.io.Serializable;

public class Experience implements Serializable {
    private String startDate;
    private String endDate;
    private String startYear;
    private String endYear;
    private String responsibility;

    public Experience() {
    }

    public Experience(String startDate, String endDate, String startYear, String endYear, String responsibility) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.startYear = startYear;
        this.endYear = endYear;
        this.responsibility = responsibility;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getStartYear() {
        return startYear;
    }

    public void setStartYear(String startYear) {
        this.startYear = startYear;
    }

    public String getEndYear() {
        return endYear;
    }

    public void setEndYear(String endYear) {
        this.endYear = endYear;
    }

    public String getResponsibility() {
        return responsibility;
    }

    public void setResponsibility(String responsibility) {
        this.responsibility = responsibility;
    }

    @Override
    public String toString() {
        return "Experience{" +
                "startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", startYear='" + startYear + '\'' +
                ", endYear='" + endYear + '\'' +
                ", responsibility='" + responsibility + '\'' +
                '}';
    }
}

