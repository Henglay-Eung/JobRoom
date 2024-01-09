package com.ksga.springboot.employeeservice.service;

import com.ksga.springboot.employeeservice.model.employee.Employee;
import com.ksga.springboot.employeeservice.model.employee.EmployeeDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

public interface EmployeeService {
    //TODO: get all employees
    Page<EmployeeDto> getAllEmployees(Pageable pageable);

    //TODO: get all employees regardless of status
    Page<EmployeeDto> getAllEmployeesRegardlessOfStatus(Pageable pageable);

    //TODO: save employee
    EmployeeDto saveEmployee(EmployeeDto employeeDto);

    //TODO: get employee by id
    EmployeeDto getEmployeeById(int id);

    //TODO: update employee by id
    EmployeeDto updateEmployeeById(int id, EmployeeDto employeeDto);

    //TODO: delete employee
    EmployeeDto deleteEmployeeById(int id);

    //TODO: get employee by name
    Page<EmployeeDto> getEmployeeByName(String name, Pageable pageable);

    //TODO: get employee by email
    EmployeeDto getEmployeeByEmail(String email);

    //TODO: get employee by uuid
    EmployeeDto getAllEmployeeByUuid(@Param("uuid") String uuid);

    EmployeeDto updateEmployeeStatus(boolean status, int id);

//    Page<EmployeeDto> findEmployeeByFullNameContainingIgnoreCase(String name, Pageable pageable);
}
