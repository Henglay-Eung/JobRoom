package com.ksga.springboot.employeeservice.model.cv.personaldata;

import java.io.Serializable;

public class PersonalData implements Serializable {
    private String dateOfBirth;
    private String Gender;
    private String maritalStatus;
    private String nationality;
    private String placeOfBirth;

    public PersonalData() {
    }

    public PersonalData(String dateOfBirth, String gender, String maritalStatus, String nationality, String placeOfBirth) {
        this.dateOfBirth = dateOfBirth;
        Gender = gender;
        this.maritalStatus = maritalStatus;
        this.nationality = nationality;
        this.placeOfBirth = placeOfBirth;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getPlaceOfBirth() {
        return placeOfBirth;
    }

    public void setPlaceOfBirth(String placeOfBirth) {
        this.placeOfBirth = placeOfBirth;
    }

    @Override
    public String toString() {
        return "PersonalData{" +
                "dateOfBirth='" + dateOfBirth + '\'' +
                ", Gender='" + Gender + '\'' +
                ", maritalStatus='" + maritalStatus + '\'' +
                ", nationality='" + nationality + '\'' +
                ", placeOfBirth='" + placeOfBirth + '\'' +
                '}';
    }
}
