package com.ksga.springboot.employeeservice.rest.message;

import org.springframework.http.HttpStatus;

import java.sql.Timestamp;

public class BaseApiResponse<T> {
    private String message;
    private T data;
    private HttpStatus status;
    private Timestamp timestamp;

    public BaseApiResponse() {
    }

    public BaseApiResponse(String message, T data, HttpStatus status, Timestamp timestamp) {
        this.message = message;
        this.data = data;
        this.status = status;
        this.timestamp = timestamp;
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

    @Override
    public String toString() {
        return "BaseApiResponse{" +
                "message='" + message + '\'' +
                ", data=" + data +
                ", status=" + status +
                ", timestamp=" + timestamp +
                '}';
    }
}
