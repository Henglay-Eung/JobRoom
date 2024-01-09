package com.admin.admin_service.service.serviceImp;

import com.admin.admin_service.model.AnnouncementResponse;
import com.admin.admin_service.model.Company;
import com.admin.admin_service.rest.message.BaseApiResponse;
import com.admin.admin_service.rest.message.BaseApiResponseWithPage;
import com.admin.admin_service.rest.message.BaseApiResponseWithPagination;
import com.admin.admin_service.service.CommentService;
import com.admin.admin_service.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
@Service
public class CompanyServiceImp implements CompanyService {
    private RestTemplate restTemplate = new RestTemplate();
    private String baseAPiHR="http://35.197.132.204:30005/api/v1/";
    private String baseAPiAnnouncement="http://35.197.132.204:30006/api/v1/";
    @Override
    public ResponseEntity<BaseApiResponseWithPagination<List<Company>>> getAllCompany(int pageNumber,String name) {
        ParameterizedTypeReference<BaseApiResponseWithPagination<List<Company>>> parameterizedTypeReference =
                new ParameterizedTypeReference<BaseApiResponseWithPagination<List<Company>>>() {
                };

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
        ResponseEntity<BaseApiResponseWithPagination<List<Company>>> result = restTemplate.exchange(baseAPiHR+"companies?page="+pageNumber+"&name="+name , HttpMethod.GET, entity, parameterizedTypeReference);
        return result;
    }

    @Override
    public ResponseEntity<BaseApiResponseWithPagination<List<AnnouncementResponse>>> getAnnouncementByCompany(int id, int pageNumber, String caption,String startDate,String endDate) {
        ParameterizedTypeReference<BaseApiResponseWithPagination<List<AnnouncementResponse>>> parameterizedTypeReference =
                new ParameterizedTypeReference<BaseApiResponseWithPagination<List<AnnouncementResponse>>>() {
                };

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
        ResponseEntity<BaseApiResponseWithPagination<List<AnnouncementResponse>>> result = restTemplate.exchange(baseAPiAnnouncement+"announcements/company/admin/" +id+"?position="+caption+"&page="+pageNumber+"&startDate="+startDate+"&endDate="+endDate, HttpMethod.GET, entity, parameterizedTypeReference);
        return result;
    }

    @Override
    public ResponseEntity<BaseApiResponseWithPagination<List<AnnouncementResponse>>> getActiveAnnouncementByCompany(int id,int pageNumber) {
        ParameterizedTypeReference<BaseApiResponseWithPagination<List<AnnouncementResponse>>> parameterizedTypeReference =
                new ParameterizedTypeReference<BaseApiResponseWithPagination<List<AnnouncementResponse>>>() {
                };

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
        ResponseEntity<BaseApiResponseWithPagination<List<AnnouncementResponse>>> result = restTemplate.exchange(baseAPiAnnouncement+"announcements/company/active-announcement/" +id+"?page="+pageNumber, HttpMethod.GET, entity, parameterizedTypeReference);
        return result;
    }

    @Override
    public ResponseEntity<BaseApiResponseWithPagination<List<AnnouncementResponse>>> getClosedAnnouncementByCompany(int id,int pageNumber) {
        ParameterizedTypeReference<BaseApiResponseWithPagination<List<AnnouncementResponse>>> parameterizedTypeReference =
                new ParameterizedTypeReference<BaseApiResponseWithPagination<List<AnnouncementResponse>>>() {
                };

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
        ResponseEntity<BaseApiResponseWithPagination<List<AnnouncementResponse>>> result = restTemplate.exchange(baseAPiAnnouncement+"announcements/company/closed-announcement/" +id+"?page="+pageNumber, HttpMethod.GET, entity, parameterizedTypeReference);
        return result;
    }

    @Override
    public ResponseEntity<BaseApiResponseWithPagination<AnnouncementResponse>> getAnnouncementById(int id) {
        ParameterizedTypeReference<BaseApiResponseWithPagination<AnnouncementResponse>> parameterizedTypeReference =
                new ParameterizedTypeReference<BaseApiResponseWithPagination<AnnouncementResponse>>() {
                };

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
        ResponseEntity<BaseApiResponseWithPagination<AnnouncementResponse>> result = restTemplate.exchange(baseAPiAnnouncement+"announcements/admin/" +id, HttpMethod.GET, entity, parameterizedTypeReference);
        return result;
    }

    @Override
    public ResponseEntity<BaseApiResponse<Integer>> CountCompany() {
        ParameterizedTypeReference<BaseApiResponse<Integer>> parameterizedTypeReference =
                new ParameterizedTypeReference<BaseApiResponse<Integer>>() {
                };

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
        ResponseEntity<BaseApiResponse<Integer>> result = restTemplate.exchange(baseAPiHR+"companies/count", HttpMethod.GET, entity, parameterizedTypeReference);
        return result;
    }
}
