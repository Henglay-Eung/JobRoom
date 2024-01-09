package com.admin.admin_service.service;

import com.admin.admin_service.model.PostResponse;
import com.admin.admin_service.rest.message.BaseApiResponse;
import com.admin.admin_service.rest.message.BaseApiResponseWithPage;

import java.util.List;

public interface PostService {

    BaseApiResponse<PostResponse> getPostById(int id);
    BaseApiResponseWithPage<List<PostResponse>> getAllPost();
    BaseApiResponseWithPage<List<PostResponse>> getPostByPage(String pageNumber);
    BaseApiResponse<PostResponse> deletePostById(int id);
    BaseApiResponse<PostResponse> setPostStatusToFalseById(int id);
    BaseApiResponse<PostResponse> setPostStatusToTrueById(int id);
    BaseApiResponseWithPage<List<PostResponse>> findPostByName(String caption);
}
