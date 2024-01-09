package com.ksga.springboot.employeeservice.model.cv.education;

import java.io.Serializable;

public class Education implements Serializable {
    private String startYear;
    private String endYear;
    private String description;

    public Education() {
    }

    public Education(String startYear, String endYear, String description) {
        this.startYear = startYear;
        this.endYear = endYear;
        this.description = description;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    @Override
    public String toString() {
        return "Education{" +
                "startYear='" + startYear + '\'' +
                ", endYear='" + endYear + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
