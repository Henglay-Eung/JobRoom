package com.admin.admin_service.service.serviceImp;

import com.admin.admin_service.model.CommentResponse;
import com.admin.admin_service.model.CommentUpdateStatus;
import com.admin.admin_service.rest.message.BaseApiResponse;
import com.admin.admin_service.rest.message.BaseApiResponseWithPage;
import com.admin.admin_service.service.CommentService;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class CommentServiceImp implements CommentService {
    private RestTemplate restTemplate = new RestTemplate();
    HttpHeaders headers;
    HttpEntity<String> entity;

    @Override
    public BaseApiResponse<CommentResponse> getCommentById(int id) {
        ParameterizedTypeReference<BaseApiResponse<CommentResponse>> parameterizedTypeReference =
                new ParameterizedTypeReference<BaseApiResponse<CommentResponse>>() {
                };
        headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        entity = new HttpEntity<String>("parameters", headers);
        ResponseEntity<BaseApiResponse<CommentResponse>> result = restTemplate.exchange("http://35.197.132.204:31007/api/v1/comments/"+ String.valueOf(id), HttpMethod.GET, entity, parameterizedTypeReference);
        if (result.getBody().getData() == null) {
            return null ;
        }
        System.out.println("Single comment" + result.getBody());
        return result.getBody();
    }

    @Override
    public BaseApiResponseWithPage<List<CommentResponse>> getAllComment() {

            ParameterizedTypeReference<BaseApiResponseWithPage<List<CommentResponse>>> parameterizedTypeReference =
                    new ParameterizedTypeReference<BaseApiResponseWithPage<List<CommentResponse>>>() {
                    };
            headers = new HttpHeaders();
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            headers.setContentType(MediaType.APPLICATION_JSON);
            entity = new HttpEntity<String>("parameters", headers);

            ResponseEntity<BaseApiResponseWithPage<List<CommentResponse>>> result = restTemplate.exchange("http://35.197.132.204:31007/api/v1/comments/all?size=10", HttpMethod.GET, entity, parameterizedTypeReference);
            System.out.println("console log of getall comment" + result.getBody());
            if (result.getBody().getData() == null) {
                return null ;
            }
            return result.getBody();

    }


    @Override
    public BaseApiResponseWithPage<List<CommentResponse>> getCommentByPage(String pageNumber) {
        ParameterizedTypeReference<BaseApiResponseWithPage<List<CommentResponse>>> parameterizedTypeReference =
                new ParameterizedTypeReference<BaseApiResponseWithPage<List<CommentResponse>>>() {
                };
        headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        entity = new HttpEntity<String>("parameters", headers);
        ResponseEntity<BaseApiResponseWithPage<List<CommentResponse>>> result = restTemplate.exchange("http://35.197.132.204:31007/api/v1/comments/all?page={pageNumber}&size=5", HttpMethod.GET, entity, parameterizedTypeReference, pageNumber);

        if (result.getBody().getData() == null) {
            return null ;
        }

        return result.getBody();
    }

    @Override
    public BaseApiResponse<CommentResponse> deleteCommentById(int id) {
        ParameterizedTypeReference<BaseApiResponse<CommentResponse>> parameterizedTypeReference =
                new ParameterizedTypeReference<BaseApiResponse<CommentResponse>>() {
                };
        headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        entity = new HttpEntity<String>("parameters", headers);
        ResponseEntity<BaseApiResponse<CommentResponse>> result = restTemplate.exchange("http://35.197.132.204:31007/api/v1/comments/"+ String.valueOf(id), HttpMethod.DELETE, entity, parameterizedTypeReference);
        if (result.getBody().getData() == null) {
            return null ;
        }
        return result.getBody();
    }

    @Override
    public BaseApiResponse<CommentResponse> setCommentStatusToFalseById(int id) {
        ParameterizedTypeReference<BaseApiResponse<CommentResponse>> parameterizedTypeReference =
                new ParameterizedTypeReference<BaseApiResponse<CommentResponse>>() {
                };
        headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        CommentUpdateStatus commentUpdateStatus = new CommentUpdateStatus(false);
        HttpEntity<CommentUpdateStatus> requestUpdate = new HttpEntity<CommentUpdateStatus>(commentUpdateStatus, headers);
        ResponseEntity<BaseApiResponse<CommentResponse>> result = restTemplate.exchange("http://35.197.132.204:31007/api/v1/comments/status/"+ String.valueOf(id), HttpMethod.PUT, requestUpdate, parameterizedTypeReference);
        if (result.getBody().getData() == null) {
            return null ;
        }
        return result.getBody();
    }

    @Override
    public BaseApiResponse<CommentResponse> setCommentStatusToTrueById(int id) {
        ParameterizedTypeReference<BaseApiResponse<CommentResponse>> parameterizedTypeReference =
                new ParameterizedTypeReference<BaseApiResponse<CommentResponse>>() {
                };
        headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        CommentUpdateStatus commentUpdateStatus = new CommentUpdateStatus(true);
        HttpEntity<CommentUpdateStatus> requestUpdate = new HttpEntity<CommentUpdateStatus>(commentUpdateStatus, headers);
        ResponseEntity<BaseApiResponse<CommentResponse>> result = restTemplate.exchange("http://35.197.132.204:31007/api/v1/comments/status/"+ String.valueOf(id), HttpMethod.PUT, requestUpdate, parameterizedTypeReference);
        if (result.getBody().getData() == null) {
            return null ;
        }
        return result.getBody();
    }

    @Override
    public BaseApiResponseWithPage<List<CommentResponse>> findCommentByName(String name) {
        ParameterizedTypeReference<BaseApiResponseWithPage<List<CommentResponse>>> parameterizedTypeReference =
                new ParameterizedTypeReference<BaseApiResponseWithPage<List<CommentResponse>>>() {
                };
        headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        entity = new HttpEntity<String>("parameters", headers);
        ResponseEntity<BaseApiResponseWithPage<List<CommentResponse>>> result = restTemplate.exchange("http://35.197.132.204:31007/api/v1/comments/search?username={name}&size=5", HttpMethod.GET, entity, parameterizedTypeReference, name);
        System.out.println("console log of search employee " + result.getBody());
        if (result.getBody().getData() == null) {
            return null ;
        }
        return result.getBody();
    }


}
