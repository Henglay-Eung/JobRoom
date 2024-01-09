package com.admin.admin_service.service.serviceImp;

import com.admin.admin_service.model.*;
import com.admin.admin_service.rest.message.BaseApiResponse;
import com.admin.admin_service.rest.message.BaseApiResponseWithPage;
import com.admin.admin_service.service.EmployeeService;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class EmployeeServiceImp implements EmployeeService {
    private RestTemplate restTemplate = new RestTemplate();
    HttpHeaders headers;
    HttpEntity<String> entity;

    @Override
    public BaseApiResponse<EmployeeResponse> getEmployeeById(int id) {
        ParameterizedTypeReference<BaseApiResponse<EmployeeResponse>> parameterizedTypeReference =
                new ParameterizedTypeReference<BaseApiResponse<EmployeeResponse>>() {
                };
        headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        entity = new HttpEntity<String>("parameters", headers);
        ResponseEntity<BaseApiResponse<EmployeeResponse>> result = restTemplate.exchange("http://35.197.132.204:30004/api/v1/employees/"+ String.valueOf(id), HttpMethod.GET, entity, parameterizedTypeReference);
        if (result.getBody().getData() == null) {
            return null ;
        }
        System.out.println("Single employee " + result.getBody());
        return result.getBody();
    }

    @Override
    public BaseApiResponseWithPage<List<EmployeeResponse>> getAllEmployee() {
        ParameterizedTypeReference<BaseApiResponseWithPage<List<EmployeeResponse>>> parameterizedTypeReference =
                new ParameterizedTypeReference<BaseApiResponseWithPage<List<EmployeeResponse>>>() {
                };
        headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);
        entity = new HttpEntity<String>("parameters", headers);

        ResponseEntity<BaseApiResponseWithPage<List<EmployeeResponse>>> result = restTemplate.exchange("http://35.197.132.204:30004/api/v1/employees/all?size=5", HttpMethod.GET, entity, parameterizedTypeReference);
        System.out.println("console log of get all Employee " + result.getBody());
        if (result.getBody().getData() == null) {
            return null ;
        }
        return result.getBody();
    }

    @Override
    public BaseApiResponseWithPage<List<EmployeeResponse>> getEmployeeByPage(String pageNumber) {
        ParameterizedTypeReference<BaseApiResponseWithPage<List<EmployeeResponse>>> parameterizedTypeReference =
                new ParameterizedTypeReference<BaseApiResponseWithPage<List<EmployeeResponse>>>() {
                };
        headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);
        entity = new HttpEntity<String>("parameters", headers);

        ResponseEntity<BaseApiResponseWithPage<List<EmployeeResponse>>> result = restTemplate.exchange("http://35.197.132.204:30004/api/v1/employees/all?page={pageNumber}&size=5", HttpMethod.GET, entity, parameterizedTypeReference, pageNumber);
        System.out.println("console log of getall Employee " + result.getBody());
        if (result.getBody().getData() == null) {
            return null ;
        }
        return result.getBody();
    }

    @Override
    public BaseApiResponse<EmployeeResponse> deleteEmployeeById(int id) {
        return null;
    }

    @Override
    public BaseApiResponse<EmployeeResponse> setEmployeeStatusToFalseById(int id) {
        ParameterizedTypeReference<BaseApiResponse<EmployeeResponse>> parameterizedTypeReference =
                new ParameterizedTypeReference<BaseApiResponse<EmployeeResponse>>() {
                };
        headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        EmployeeUpdateStatus employeeUpdateStatus = new EmployeeUpdateStatus(false);
        HttpEntity<EmployeeUpdateStatus> requestUpdate = new HttpEntity<EmployeeUpdateStatus>(employeeUpdateStatus, headers);
        ResponseEntity<BaseApiResponse<EmployeeResponse>> result = restTemplate.exchange("http://35.197.132.204:30004/api/v1/employees/status/"+ String.valueOf(id), HttpMethod.PUT, requestUpdate, parameterizedTypeReference);
        if (result.getBody().getData() == null) {
            return null ;
        }
        return result.getBody();
    }

    @Override
    public BaseApiResponse<EmployeeResponse> setEmployeeStatusToTrueById(int id) {
        ParameterizedTypeReference<BaseApiResponse<EmployeeResponse>> parameterizedTypeReference =
                new ParameterizedTypeReference<BaseApiResponse<EmployeeResponse>>() {
                };
        headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        EmployeeUpdateStatus employeeUpdateStatus = new EmployeeUpdateStatus(true);
        HttpEntity<EmployeeUpdateStatus> requestUpdate = new HttpEntity<EmployeeUpdateStatus>(employeeUpdateStatus, headers);
        ResponseEntity<BaseApiResponse<EmployeeResponse>> result = restTemplate.exchange("http://35.197.132.204:30004/api/v1/employees/status/"+ String.valueOf(id), HttpMethod.PUT, requestUpdate, parameterizedTypeReference);
        if (result.getBody().getData() == null) {
            return null ;
        }
        return result.getBody();
    }

    @Override
    public BaseApiResponseWithPage<List<EmployeeResponse>> findEmployeeByName(String name) {
        ParameterizedTypeReference<BaseApiResponseWithPage<List<EmployeeResponse>>> parameterizedTypeReference =
                new ParameterizedTypeReference<BaseApiResponseWithPage<List<EmployeeResponse>>>() {
                };
        headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        entity = new HttpEntity<String>("parameters", headers);
        ResponseEntity<BaseApiResponseWithPage<List<EmployeeResponse>>> result = restTemplate.exchange("http://35.197.132.204:30004/api/v1/search/employees?name={name}&size=5", HttpMethod.GET, entity, parameterizedTypeReference, name);
        System.out.println("console log of search employee " + result.getBody());
        if (result.getBody().getData() == null) {
            return null ;
        }
        return result.getBody();
    }
}
