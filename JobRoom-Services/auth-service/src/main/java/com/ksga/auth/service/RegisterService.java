package com.ksga.auth.service;

import com.ksga.auth.model.company.CompanyRequest;
import com.ksga.auth.model.employee.EmployeeRequest;
import com.ksga.auth.model.user.UserDetailsDto;

public interface RegisterService {


    public UserDetailsDto registerCompany(CompanyRequest companyRequest);

    public UserDetailsDto registerEmployee(EmployeeRequest employeeRequest);

    public UserDetailsDto login(String email,String password);

    public UserDetailsDto changePasswordForCompany(CompanyRequest companyRequest);


}
