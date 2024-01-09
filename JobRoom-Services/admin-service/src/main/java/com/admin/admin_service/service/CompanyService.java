package com.admin.admin_service.service;

import com.admin.admin_service.model.AnnouncementResponse;
import com.admin.admin_service.model.Company;
import com.admin.admin_service.rest.message.BaseApiResponse;
import com.admin.admin_service.rest.message.BaseApiResponseWithPage;
import com.admin.admin_service.rest.message.BaseApiResponseWithPagination;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CompanyService {
    ResponseEntity<BaseApiResponseWithPagination<List<Company>>> getAllCompany(int pageNumber,String name);
    ResponseEntity<BaseApiResponseWithPagination<List<AnnouncementResponse>>> getAnnouncementByCompany(int id, int pageNumber, String name,String start,String end);
    ResponseEntity<BaseApiResponseWithPagination<List<AnnouncementResponse>>> getActiveAnnouncementByCompany(int id,int pageNumber);
    ResponseEntity<BaseApiResponseWithPagination<List<AnnouncementResponse>>> getClosedAnnouncementByCompany(int id,int pageNumber);
    ResponseEntity<BaseApiResponseWithPagination<AnnouncementResponse>>getAnnouncementById(int id);
    ResponseEntity<BaseApiResponse<Integer>> CountCompany();
}
