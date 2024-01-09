package com.admin.admin_service.service;

import com.admin.admin_service.model.EmployeeResponse;
import com.admin.admin_service.rest.message.BaseApiResponse;
import com.admin.admin_service.rest.message.BaseApiResponseWithPage;

import java.util.List;

public interface EmployeeService {
    BaseApiResponse<EmployeeResponse> getEmployeeById(int id);
    BaseApiResponseWithPage<List<EmployeeResponse>> getAllEmployee();
    BaseApiResponseWithPage<List<EmployeeResponse>> getEmployeeByPage(String pageNumber);
    BaseApiResponse<EmployeeResponse> deleteEmployeeById(int id);
    BaseApiResponse<EmployeeResponse> setEmployeeStatusToFalseById(int id);
    BaseApiResponse<EmployeeResponse> setEmployeeStatusToTrueById(int id);
    BaseApiResponseWithPage<List<EmployeeResponse>> findEmployeeByName(String name);
}
