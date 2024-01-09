package com.admin.admin_service.rest.message;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;

import java.sql.Timestamp;

public class BaseApiResponseWithPagination<T> {


    private String message;
    private T data;
    private HttpStatus status;
    private Timestamp timestamp;
    private PaginationJobRoom pagination;
    public BaseApiResponseWithPagination() {
    }

    public BaseApiResponseWithPagination(String message, T data, HttpStatus status, Timestamp timestamp, PaginationJobRoom pagination) {
        this.message = message;
        this.data = data;
        this.status = status;
        this.timestamp = timestamp;
        this.pagination = pagination;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public PaginationJobRoom getPagination() {
        return pagination;
    }

    public void setPagination(PaginationJobRoom pagination) {
        this.pagination = pagination;
    }
}
