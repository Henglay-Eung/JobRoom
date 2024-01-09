package com.ksga.springboot.employeeservice.model.cvupload;

import com.ksga.springboot.employeeservice.model.employee.Employee;


public class CvUploadDto {
    private int id;
    private String name;
    private String file;
    private Employee employee;
    private boolean isPublic;
    private boolean status;

    public CvUploadDto() {
    }

    public CvUploadDto(int id, String name, String file, Employee employee, boolean isPublic, boolean status) {
        this.id = id;
        this.name = name;
        this.file = file;
        this.employee = employee;
        this.isPublic = isPublic;
        this.status = status;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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



    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "CvUploadDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", file='" + file + '\'' +
                ", employee=" + employee +
                ", isPublic=" + isPublic +
                ", status=" + status +
                '}';
    }
}
