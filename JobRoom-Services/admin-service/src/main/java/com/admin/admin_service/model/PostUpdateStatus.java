package com.admin.admin_service.model;

public class PostUpdateStatus {
    private boolean status;

    public PostUpdateStatus() {
    }

    public PostUpdateStatus(boolean status) {
        this.status = status;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "PostUpdateStatus{" +
                "status=" + status +
                '}';
    }
}
