package com.ksga.springboot.employeeservice.model.employee;

public class EmployeeUpdateStatus {
    private boolean status;

    public EmployeeUpdateStatus() {
    }

    public EmployeeUpdateStatus(boolean status) {
        this.status = status;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "EmployeeUpdateStatus{" +
                "status=" + status +
                '}';
    }
}
