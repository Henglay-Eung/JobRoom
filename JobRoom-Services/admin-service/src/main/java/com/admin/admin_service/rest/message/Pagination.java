package com.admin.admin_service.rest.message;

public class Pagination {

    private int pageNumber;
    private Long totalElements;
    private int totalPages;
    private int pageSize;

    public Pagination(){}

    public Pagination(int pageNumber, Long totalElements, int totalPages, int pageSize) {
        this.pageNumber = pageNumber;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
        this.pageSize = pageSize;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public Long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(Long totalElements) {
        this.totalElements = totalElements;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    @Override
    public String toString() {
        return "{" +
                "pageNumber=" + pageNumber +
                ", totalItems=" + totalElements +
                ", totalPages=" + totalPages +
                ", pageSize=" + pageSize +
                '}';
    }
}
