package com.ksga.springboot.employeeservice.model.cv.reference;

import java.io.Serializable;

public class ReferenceCv implements Serializable {
    private String name;
    private String phone;
    private String position;
    private String email;

    public ReferenceCv(String name, String phone, String position, String email) {
        this.name = name;
        this.phone = phone;
        this.position = position;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "ReferenceCv{" +
                "name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", position='" + position + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
