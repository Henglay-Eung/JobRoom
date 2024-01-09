package com.ksga.auth.model.employee;

import javax.validation.constraints.NotBlank;

public class EmployeeRequest {
    @NotBlank(message = "fullname cannot be empty")
    private String fullName;

//    @NotBlank(message = "gender cannot be empty")
    private String gender;

//    @NotBlank(message = "street cannot be empty")
    private String street;

//    @NotBlank(message = "commune cannot be empty")
    private String commune;

//    @NotBlank(message = "district cannot be empty")
    private String district;

//    @NotBlank(message = "city cannot be empty")
    private String city;

    @NotBlank(message = "email cannot be empty")
    private String email;

    @NotBlank(message = "password cannot be empty")
    private String password;

//    @NotBlank(message = "date of birth cannot be empty")
    private String dateOfBirth;

//    @NotBlank(message = "telephone cannot be empty")
    private String telephone;

    private String profilePicture;

    private String coverPicture;

    public EmployeeRequest() {
    }

    public EmployeeRequest( String fullName, String gender, String street, String commune, String district, String city, String email, String password, String dateOfBirth, String telephone, String profilePicture, String coverPicture) {
        this.fullName = fullName;
        this.gender = gender;
        this.street = street;
        this.commune = commune;
        this.district = district;
        this.city = city;
        this.email = email;
        this.password = password;
        this.dateOfBirth = dateOfBirth;
        this.telephone = telephone;
        this.profilePicture = profilePicture;
        this.coverPicture = coverPicture;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
}
