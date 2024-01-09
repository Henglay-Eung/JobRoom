package com.admin.admin_service.rest.message;

import org.springframework.http.HttpStatus;

import java.sql.Timestamp;

public class BaseApiResponseWithPage<T> {
    private String message;
    private T data;
    private HttpStatus status;
    private Timestamp timestamp;
    private Pagination pagination;

    public BaseApiResponseWithPage() {
    }

    public BaseApiResponseWithPage(String message, T data, HttpStatus status, Timestamp timestamp, Pagination pagination) {
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

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }

    @Override
    public String toString() {
        return "BaseApiResponseWithPage{" +
                "message='" + message + '\'' +
                ", data=" + data +
                ", status=" + status +
                ", timestamp=" + timestamp +
                ", pagination=" + pagination +
                '}';
    }
}
