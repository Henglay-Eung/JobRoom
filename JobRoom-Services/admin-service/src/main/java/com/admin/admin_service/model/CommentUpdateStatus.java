package com.admin.admin_service.model;

public class CommentUpdateStatus {
    private boolean status;

    public CommentUpdateStatus() {
    }

    public CommentUpdateStatus(boolean status) {
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
        return "CommentUpdateStatus{" +
                "status=" + status +
                '}';
    }
}
