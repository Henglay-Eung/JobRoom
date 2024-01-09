package com.admin.admin_service.service.serviceImp;

import com.admin.admin_service.model.PostResponse;
import com.admin.admin_service.model.PostUpdateStatus;
import com.admin.admin_service.rest.message.BaseApiResponse;
import com.admin.admin_service.rest.message.BaseApiResponseWithPage;
import com.admin.admin_service.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class PostServiceImp implements PostService {

    private ModelMapper modelMapper = new ModelMapper();
    private RestTemplate restTemplate = new RestTemplate();
    HttpHeaders headers;
    HttpEntity<String> entity;

    //TODO : get post by post id
    @Override
    public BaseApiResponse<PostResponse> getPostById(int id) {
        ParameterizedTypeReference<BaseApiResponse<PostResponse>> parameterizedTypeReference =
                new ParameterizedTypeReference<BaseApiResponse<PostResponse>>() {
                };
        headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        entity = new HttpEntity<String>("parameters", headers);
        ResponseEntity<BaseApiResponse<PostResponse>> result = restTemplate.exchange("http://35.197.132.204:31007/api/v1/posts/"+ String.valueOf(id), HttpMethod.GET, entity, parameterizedTypeReference);
        if (result.getBody().getData() == null) {
            return null ;
        }
        System.out.println("Single post" + result.getBody());
        return result.getBody();
    }

    //TODO get all post
    @Override
    public BaseApiResponseWithPage<List<PostResponse>> getAllPost() {
        ParameterizedTypeReference<BaseApiResponseWithPage<List<PostResponse>>> parameterizedTypeReference =
                new ParameterizedTypeReference<BaseApiResponseWithPage<List<PostResponse>>>() {
                };
        headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        entity = new HttpEntity<String>("parameters", headers);
        ResponseEntity<BaseApiResponseWithPage<List<PostResponse>>> result = restTemplate.exchange("http://35.197.132.204:31007/api/v1/posts/all?size=5", HttpMethod.GET, entity, parameterizedTypeReference);
        System.out.println("console log" + result.getBody());
        if (result.getBody().getData() == null) {
            return null ;
        }
        return result.getBody();

    }


    //TODO : get post by page
    @Override
    public BaseApiResponseWithPage<List<PostResponse>> getPostByPage(String pageNumber) {

            ParameterizedTypeReference<BaseApiResponseWithPage<List<PostResponse>>> parameterizedTypeReference =
                    new ParameterizedTypeReference<BaseApiResponseWithPage<List<PostResponse>>>() {
                    };
            headers = new HttpHeaders();
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            entity = new HttpEntity<String>("parameters", headers);
            ResponseEntity<BaseApiResponseWithPage<List<PostResponse>>> result = restTemplate.exchange("http://35.197.132.204:31007/api/v1/posts/all?page={pageNumber}&size=5", HttpMethod.GET, entity, parameterizedTypeReference, pageNumber);

            if (result.getBody().getData() == null) {
                return null ;
            }

            return result.getBody();
    }

    //TODO: Delete post by post id
    @Override
    public BaseApiResponse<PostResponse> deletePostById(int id) {
        ParameterizedTypeReference<BaseApiResponse<PostResponse>> parameterizedTypeReference =
                new ParameterizedTypeReference<BaseApiResponse<PostResponse>>() {
                };
        headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        entity = new HttpEntity<String>("parameters", headers);
        ResponseEntity<BaseApiResponse<PostResponse>> result = restTemplate.exchange("http://35.197.132.204:31007/api/v1/posts/"+ String.valueOf(id), HttpMethod.DELETE, entity, parameterizedTypeReference);
        if (result.getBody().getData() == null) {
            return null ;
        }
        return result.getBody();
    }

    @Override
    public BaseApiResponse<PostResponse> setPostStatusToFalseById(int id) {
        ParameterizedTypeReference<BaseApiResponse<PostResponse>> parameterizedTypeReference =
                new ParameterizedTypeReference<BaseApiResponse<PostResponse>>() {
                };
        headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        PostUpdateStatus postUpdateStatus = new PostUpdateStatus(false);
        HttpEntity<PostUpdateStatus> requestUpdate = new HttpEntity<PostUpdateStatus>(postUpdateStatus, headers);
        ResponseEntity<BaseApiResponse<PostResponse>> result = restTemplate.exchange("http://35.197.132.204:31007/api/v1/posts/status/"+ String.valueOf(id), HttpMethod.PUT, requestUpdate, parameterizedTypeReference);
        if (result.getBody().getData() == null) {
            return null ;
        }
        return result.getBody();
    }

    @Override
    public BaseApiResponse<PostResponse> setPostStatusToTrueById(int id) {
        ParameterizedTypeReference<BaseApiResponse<PostResponse>> parameterizedTypeReference =
                new ParameterizedTypeReference<BaseApiResponse<PostResponse>>() {
                };
        headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        PostUpdateStatus postUpdateStatus = new PostUpdateStatus(true);
        HttpEntity<PostUpdateStatus> requestUpdate = new HttpEntity<PostUpdateStatus>(postUpdateStatus, headers);
        ResponseEntity<BaseApiResponse<PostResponse>> result = restTemplate.exchange("http://35.197.132.204:31007/api/v1/posts/status/"+ String.valueOf(id), HttpMethod.PUT, requestUpdate, parameterizedTypeReference);
        if (result.getBody().getData() == null) {
            return null ;
        }
        return result.getBody();
    }

    @Override
    public BaseApiResponseWithPage<List<PostResponse>> findPostByName(String name) {
        ParameterizedTypeReference<BaseApiResponseWithPage<List<PostResponse>>> parameterizedTypeReference =
                new ParameterizedTypeReference<BaseApiResponseWithPage<List<PostResponse>>>() {
                };
        headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        entity = new HttpEntity<String>("parameters", headers);
        ResponseEntity<BaseApiResponseWithPage<List<PostResponse>>> result = restTemplate.exchange("http://35.197.132.204:31007/api/v1/posts/search?username={name}&size=5", HttpMethod.GET, entity, parameterizedTypeReference, name);
        System.out.println("console log of search " + result.getBody());
        if (result.getBody().getData() == null) {
            return null ;
        }
        return result.getBody();
    }


}
