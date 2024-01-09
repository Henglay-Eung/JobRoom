package com.ksga.springboot.employeeservice.model.employee;

public class EmployeeFilter {
    private int id;

    public EmployeeFilter() {
    }

    public EmployeeFilter(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
