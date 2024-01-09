package com.ksga.springboot.employeeservice.model.cv.certifications;

import java.io.Serializable;

public class Certification implements Serializable {
    private String description;

    public Certification() {
    }

    public Certification(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Certification{" +
                "description='" + description + '\'' +
                '}';
    }
}
