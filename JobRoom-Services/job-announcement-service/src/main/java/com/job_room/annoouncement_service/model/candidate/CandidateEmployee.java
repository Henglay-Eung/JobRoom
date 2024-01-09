package com.job_room.annoouncement_service.model.candidate;

public class CandidateEmployee {
    private int id;

    private String fullName;

    private String gender;

    private String street;

    private String commune;

    private String district;

    private String city;

    private String email;

    private String dateOfBirth;

    private String telephone;

    private String profilePicture;

    private String coverPicture;
    public CandidateEmployee(){}

    public CandidateEmployee(int id, String fullName, String gender, String street, String commune, String district, String city, String email, String dateOfBirth, String telephone, String profilePicture, String coverPicture) {
        this.id = id;
        this.fullName = fullName;
        this.gender = gender;
        this.street = street;
        this.commune = commune;
        this.district = district;
        this.city = city;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.telephone = telephone;
        this.profilePicture = profilePicture;
        this.coverPicture = coverPicture;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCommune() {
        return commune;
    }

    public void setCommune(String commune) {
        this.commune = commune;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public String getCoverPicture() {
        return coverPicture;
    }

    public void setCoverPicture(String coverPicture) {
        this.coverPicture = coverPicture;
    }

    @Override
    public String toString() {
        return "CandidateEmployee{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                ", gender='" + gender + '\'' +
                ", street='" + street + '\'' +
                ", commune='" + commune + '\'' +
                ", district='" + district + '\'' +
                ", city='" + city + '\'' +
                ", email='" + email + '\'' +
                ", dateOfBirth='" + dateOfBirth + '\'' +
                ", telephone=" + telephone +
                ", profilePicture='" + profilePicture + '\'' +
                ", coverPicture='" + coverPicture + '\'' +
                '}';
    }
}
