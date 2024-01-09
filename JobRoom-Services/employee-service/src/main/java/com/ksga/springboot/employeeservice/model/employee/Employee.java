package com.ksga.springboot.employeeservice.model.employee;


import javax.persistence.*;

@Entity
@Table(name = "ce_employees")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String fullName;

    private String gender;

    private String uuid;

    private String street;

    private String commune;

    private String district;

    private String city;

    @Column(unique = true)
    private String email;

    private String password;

    private String dateOfBirth;

    private String telephone;

    private String profilePicture;

    private String coverPicture;

    private boolean status;

    public Employee() {
    }

    public Employee(int id, String fullName, String gender, String uuid, String street, String commune, String district, String city, String email, String password, String dateOfBirth, String telephone, String profilePicture, String coverPicture, boolean status) {
        this.id = id;
        this.fullName = fullName;
        this.gender = gender;
        this.uuid = uuid;
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
        this.status = status;
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

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
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

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                ", gender='" + gender + '\'' +
                ", uuid='" + uuid + '\'' +
                ", street='" + street + '\'' +
                ", commune='" + commune + '\'' +
                ", district='" + district + '\'' +
                ", city='" + city + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", dateOfBirth='" + dateOfBirth + '\'' +
                ", telephone='" + telephone + '\'' +
                ", profilePicture='" + profilePicture + '\'' +
                ", coverPicture='" + coverPicture + '\'' +
                ", status=" + status +
                '}';
    }
}
