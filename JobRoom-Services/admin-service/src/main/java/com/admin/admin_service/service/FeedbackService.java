package com.admin.admin_service.service;

import com.admin.admin_service.model.Feedback;
import com.admin.admin_service.rest.message.BaseApiResponse;
import com.admin.admin_service.rest.message.BaseApiResponseWithPagination;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface FeedbackService {
    ResponseEntity<BaseApiResponseWithPagination<List<Feedback>>> findAllFeedback(int page);
    ResponseEntity<BaseApiResponse<Long>> countFeedback();
}
