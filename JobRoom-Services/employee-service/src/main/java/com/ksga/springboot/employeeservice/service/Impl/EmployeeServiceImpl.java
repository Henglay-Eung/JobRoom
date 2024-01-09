package com.ksga.springboot.employeeservice.service.Impl;

import com.ksga.springboot.employeeservice.model.cv.Cv;
import com.ksga.springboot.employeeservice.model.cv.CvDto;
import com.ksga.springboot.employeeservice.model.employee.Employee;
import com.ksga.springboot.employeeservice.model.employee.EmployeeDto;
import com.ksga.springboot.employeeservice.repository.EmployeeRepository;
import com.ksga.springboot.employeeservice.rest.message.PaginationUtils;
import com.ksga.springboot.employeeservice.service.EmployeeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
public class EmployeeServiceImpl implements EmployeeService {

    private EmployeeRepository employeeRepository;

    @Autowired
    public void setEmployeeRepository(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    private final ModelMapper mapper = new ModelMapper();

    @Override
    public Page<EmployeeDto> getAllEmployees(Pageable pageable) {
        Page<Employee> employeePage = employeeRepository.findAllByStatusIsTrue(pageable);

        PaginationUtils<EmployeeDto, Page<Employee>> pagePaginationUtils = new PaginationUtils<>(EmployeeDto.class);
        pagePaginationUtils.setData(employeePage);

        return pagePaginationUtils;
    }

    @Override
    public Page<EmployeeDto> getAllEmployeesRegardlessOfStatus(Pageable pageable) {
        Page<Employee> employeePage = employeeRepository.findAllByOrderByIdDesc(pageable);

        PaginationUtils<EmployeeDto, Page<Employee>> pagePaginationUtils = new PaginationUtils<>(EmployeeDto.class);
        pagePaginationUtils.setData(employeePage);

        return pagePaginationUtils;
    }

    @Override
    public EmployeeDto saveEmployee(EmployeeDto employeeDto) {
        employeeDto.setStatus(true);
        UUID uuid = UUID.randomUUID();
        employeeDto.setUuid("emp"+uuid);

        Employee employee = mapper.map(employeeDto, Employee.class);
        Employee employeeResult = employeeRepository.save(employee);

        return mapper.map(employeeResult , EmployeeDto.class);
    }

    @Override
    public EmployeeDto getEmployeeById(int id) {
        Employee employee = employeeRepository.findEmployeeByIdAndStatusIsTrue(id);
        if(employee!=null)
            return mapper.map(employee, EmployeeDto.class);
        else
            return null;
    }

    @Override
    public EmployeeDto updateEmployeeById(int id, EmployeeDto employeeDto) {
        Employee employee = employeeRepository.findEmployeeByIdAndStatusIsTrue(id);
        if(employee!=null) {

            Employee employeeResult = mapper.map(employeeDto, Employee.class);
            employeeResult.setId(id);
            employeeResult.setUuid(employee.getUuid());
            employeeResult.setStatus(employee.isStatus());

            Employee result =  employeeRepository.save(employeeResult);
            return mapper.map(result, EmployeeDto.class);
        }
        else {
            return null;
        }
    }

    @Override
    public EmployeeDto deleteEmployeeById(int id) {
        Employee employee = employeeRepository.findEmployeeByIdAndStatusIsTrue(id);
        if(employee!=null) {

            employee.setId(id);
            employee.setStatus(false);
            Employee employeeResult =  employeeRepository.save(employee);
            return mapper.map(employeeResult, EmployeeDto.class);

        }
        else {
            return null;
        }
    }

    @Override
    public Page<EmployeeDto> getEmployeeByName(String name, Pageable pageable) {
        Page<Employee> employeePage = employeeRepository.findAllByFullNameContainingIgnoreCase(name, pageable);

        PaginationUtils<EmployeeDto, Page<Employee>> pagePaginationUtils = new PaginationUtils<>(EmployeeDto.class);
        pagePaginationUtils.setData(employeePage);

        return pagePaginationUtils;
    }

    @Override
    public EmployeeDto getAllEmployeeByUuid(String uuid) {
        Employee employee = employeeRepository.getAllEmployeeByUuid(uuid);
        if(employee!=null){
            return mapper.map(employee,EmployeeDto.class);
        }
        return null;
    }

    @Override
    public EmployeeDto getEmployeeByEmail(String email) {
        Employee employee = employeeRepository.findEmployeeByEmail(email);
        if(employee!=null){
            return mapper.map(employee,EmployeeDto.class);
        }
        else {
            return null;
        }
    }

    @Override
    public EmployeeDto updateEmployeeStatus(boolean status, int id) {
        Employee employee = employeeRepository.findEmployeeById(id);
        if(employee!=null){
            if (employeeRepository.updateEmployeeStatus(status, id)>0) {
                employee.setStatus(status);
                return mapper.map(employee, EmployeeDto.class);
            }
        }
        return null;
    }

//    @Override
//    public Page<EmployeeDto> findEmployeeByFullNameContainingIgnoreCase(String name, Pageable pageable) {
//        Page<Employee> employees = employeeRepository.findEmployeeByFullNameContainingIgnoreCase(name,pageable);
//        PaginationUtils<EmployeeDto, Page<Employee>> paging = new PaginationUtils<>(EmployeeDto.class);
//        paging.setData(employees);
//        return paging;
//    }
}

