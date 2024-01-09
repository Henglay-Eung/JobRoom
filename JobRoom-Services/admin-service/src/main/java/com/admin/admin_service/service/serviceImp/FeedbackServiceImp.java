package com.admin.admin_service.service.serviceImp;

import com.admin.admin_service.model.Feedback;
import com.admin.admin_service.rest.message.BaseApiResponse;
import com.admin.admin_service.rest.message.BaseApiResponseWithPagination;
import com.admin.admin_service.service.FeedbackService;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class FeedbackServiceImp implements FeedbackService {
    private RestTemplate restTemplate=new RestTemplate();
    private String baseAPiAnnouncement="http://35.197.132.204:30006/api/v1/";
    @Override
    public ResponseEntity<BaseApiResponseWithPagination<List<Feedback>>> findAllFeedback(int page) {
        ParameterizedTypeReference<BaseApiResponseWithPagination<List<Feedback>>> parameterizedTypeReference =
                new ParameterizedTypeReference<BaseApiResponseWithPagination<List<Feedback>>>() {
                };

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
        ResponseEntity<BaseApiResponseWithPagination<List<Feedback>>> result = restTemplate.exchange(baseAPiAnnouncement+"feedback?"+"page="+page, HttpMethod.GET, entity, parameterizedTypeReference);
        return result;
    }

    @Override
    public ResponseEntity<BaseApiResponse<Long>> countFeedback() {
        ParameterizedTypeReference<BaseApiResponse<Long>> parameterizedTypeReference =
                new ParameterizedTypeReference<BaseApiResponse<Long>>() {
                };

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
        ResponseEntity<BaseApiResponse<Long>> result = restTemplate.exchange(baseAPiAnnouncement+"feedback/count", HttpMethod.GET, entity, parameterizedTypeReference);
        return result;
    }
}
