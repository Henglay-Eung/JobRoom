package com.ksga.springboot.employeeservice.model.cvupload;

import com.ksga.springboot.employeeservice.model.employee.EmployeeFilter;

public class CvUploadRequest {
    private String name;
    private String file;
    private EmployeeFilter employee;

    public CvUploadRequest(String name, String file, EmployeeFilter employee) {
        this.name = name;
        this.file = file;
        this.employee = employee;
    }

    public CvUploadRequest() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public EmployeeFilter getEmployee() {
        return employee;
    }

    public void setEmployee(EmployeeFilter employee) {
        this.employee = employee;
    }

    @Override
    public String toString() {
        return "CvUploadRequest{" +
                "name='" + name + '\'' +
                ", file='" + file + '\'' +
                ", employee=" + employee +
                '}';
    }
}
