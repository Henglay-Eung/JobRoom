package com.admin.admin_service.service.serviceImp;

import com.admin.admin_service.model.AnnouncementResponse;
import com.admin.admin_service.model.CompanyResponse;
import com.admin.admin_service.rest.message.BaseApiResponse;
import com.admin.admin_service.rest.message.BaseApiResponseWithPagination;
import com.admin.admin_service.service.AdminService;
import org.modelmapper.ModelMapper;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class AdminServiceImp implements AdminService {


    private ModelMapper modelMapper = new ModelMapper();

    private RestTemplate restTemplate = new RestTemplate();

    HttpHeaders headers;

    HttpEntity<String> entity;


    @Override
    public int countAllCompany() {
        ParameterizedTypeReference<BaseApiResponse<Integer>> parameterizedTypeReference =
                new ParameterizedTypeReference<BaseApiResponse<Integer>>() {
                };
        headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        entity = new HttpEntity<String>("parameters", headers);
        ResponseEntity<BaseApiResponse<Integer>> result = restTemplate.exchange("http://35.197.132.204:30005/api/v1/companies/count", HttpMethod.GET, entity, parameterizedTypeReference);
        System.out.println(result.getBody());
        if (result.getBody().getData() == null) {
            return 0;
        }

        return result.getBody().getData();
    }

    @Override
    public int countAllAnnouncement() {
        return 0;
    }

    //TODO: Select company =========================================================
    @Override
    public List<CompanyResponse> selectCompany(String name) {

        ParameterizedTypeReference<BaseApiResponse<List<CompanyResponse>>> parameterizedTypeReference =
                new ParameterizedTypeReference<BaseApiResponse<List<CompanyResponse>>>() {
                };
        headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        entity = new HttpEntity<String>("parameters", headers);
        ResponseEntity<BaseApiResponse<List<CompanyResponse>>> result = restTemplate.exchange("http://35.197.132.204:30005/api/v1/companies?name="+name, HttpMethod.GET, entity, parameterizedTypeReference);
        if (result.getBody().getData() == null) {
            return null ;
        }

        return result.getBody().getData();
    }

    @Override
    public CompanyResponse selectOneCompany(int id) {

        ParameterizedTypeReference<BaseApiResponse<CompanyResponse>> parameterizedTypeReference =
                new ParameterizedTypeReference<BaseApiResponse<CompanyResponse>>() {
                };
        headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        entity = new HttpEntity<String>("parameters", headers);
        ResponseEntity<BaseApiResponse<CompanyResponse>> result = restTemplate.exchange("http://35.197.132.204:30005/api/v1/companies/"+id, HttpMethod.GET, entity, parameterizedTypeReference);
        if (result.getBody().getData() == null) {
            return null ;
        }
        return result.getBody().getData();
    }

    @Override
    public CompanyResponse banCompany(int id) {
        ParameterizedTypeReference<BaseApiResponse<CompanyResponse>> parameterizedTypeReference =
                new ParameterizedTypeReference<BaseApiResponse<CompanyResponse>>() {
                };
        headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        entity = new HttpEntity<String>("parameters", headers);
        ResponseEntity<BaseApiResponse<CompanyResponse>> result = restTemplate.exchange("http://35.197.132.204:30005/api/v1/companies/ban/"+id, HttpMethod.POST, entity, parameterizedTypeReference);
        if (result.getBody().getData() == null) {
            return null ;
        }
        return result.getBody().getData();
    }

    @Override
    public CompanyResponse unbanCompany(int id) {
        ParameterizedTypeReference<BaseApiResponse<CompanyResponse>> parameterizedTypeReference =
                new ParameterizedTypeReference<BaseApiResponse<CompanyResponse>>() {
                };
        headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        entity = new HttpEntity<String>("parameters", headers);
        ResponseEntity<BaseApiResponse<CompanyResponse>> result = restTemplate.exchange("http://35.197.132.204:30005/api/v1/companies/unban/"+id, HttpMethod.POST, entity, parameterizedTypeReference);
        if (result.getBody().getData() == null) {
            return null ;
        }
        return result.getBody().getData();
    }

    @Override
    public List<AnnouncementResponse> selectAnnouncementByCompany(int companyId) {
        ParameterizedTypeReference<BaseApiResponse<List<AnnouncementResponse>>> parameterizedTypeReference =
                new ParameterizedTypeReference<BaseApiResponse<List<AnnouncementResponse>>>() {
                };
        headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        entity = new HttpEntity<String>("parameters", headers);
            ResponseEntity<BaseApiResponse<List<AnnouncementResponse>>> result = restTemplate.exchange("http://35.197.132.204:30006/api/v1/announcements/company/"+companyId, HttpMethod.GET, entity, parameterizedTypeReference);
        if (result.getBody().getData() == null) {
            return null ;
        }

        return result.getBody().getData();
    }

    @Override
    public AnnouncementResponse selectOneAnnouncement(int id) {

        ParameterizedTypeReference<BaseApiResponse<AnnouncementResponse>> parameterizedTypeReference =
                new ParameterizedTypeReference<BaseApiResponse<AnnouncementResponse>>() {
                };
        headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        entity = new HttpEntity<String>("parameters", headers);
        ResponseEntity<BaseApiResponse<AnnouncementResponse>> result = restTemplate.exchange("http://35.197.132.204:30006/api/v1/announcements/"+id, HttpMethod.GET, entity, parameterizedTypeReference);
        if (result.getBody().getData() == null) {
            return null ;
        }
        return result.getBody().getData();
    }

    @Override
    public AnnouncementResponse banAnnouncement(int id) {
        ParameterizedTypeReference<BaseApiResponse<AnnouncementResponse>> parameterizedTypeReference =
                new ParameterizedTypeReference<BaseApiResponse<AnnouncementResponse>>() {
                };
        headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        entity = new HttpEntity<String>("parameters", headers);
        ResponseEntity<BaseApiResponse<AnnouncementResponse>> result = restTemplate.exchange("http://35.197.132.204:30006/api/v1/announcements/ban/"+id, HttpMethod.POST, entity, parameterizedTypeReference);
        if (result.getBody().getData() == null) {
            return null ;
        }
        return result.getBody().getData();
    }

    @Override
    public AnnouncementResponse unbanAnnouncement(int id) {
        ParameterizedTypeReference<BaseApiResponse<AnnouncementResponse>> parameterizedTypeReference =
                new ParameterizedTypeReference<BaseApiResponse<AnnouncementResponse>>() {
                };
        headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        entity = new HttpEntity<String>("parameters", headers);
        ResponseEntity<BaseApiResponse<AnnouncementResponse>> result = restTemplate.exchange("http://35.197.132.204:30006/api/v1/announcements/unban/"+id, HttpMethod.POST, entity, parameterizedTypeReference);
        if (result.getBody().getData() == null) {
            return null ;
        }
        return result.getBody().getData();
    }

}