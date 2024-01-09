package com.admin.admin_service.service;

import com.admin.admin_service.model.CommentResponse;
import com.admin.admin_service.rest.message.BaseApiResponse;
import com.admin.admin_service.rest.message.BaseApiResponseWithPage;

import java.util.List;

public interface CommentService {

    BaseApiResponse<CommentResponse> getCommentById(int id);
    BaseApiResponseWithPage<List<CommentResponse>> getAllComment();
    BaseApiResponseWithPage<List<CommentResponse>> getCommentByPage(String pageNumber);
    BaseApiResponse<CommentResponse> deleteCommentById(int id);
    BaseApiResponse<CommentResponse> setCommentStatusToFalseById(int id);
    BaseApiResponse<CommentResponse> setCommentStatusToTrueById(int id);
    BaseApiResponseWithPage<List<CommentResponse>> findCommentByName(String name);

}
