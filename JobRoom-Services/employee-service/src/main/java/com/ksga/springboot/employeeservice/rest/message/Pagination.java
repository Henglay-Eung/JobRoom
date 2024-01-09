package com.ksga.springboot.employeeservice.rest.message;

public class Pagination {
    private int pageNumber;
    private int pageSize;
    private int totalPages;
    private long totalElements;

    public Pagination() {
    }

    public Pagination(int pageNumber, int pageSize, int totalPages, long totalElements) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.totalPages = totalPages;
        this.totalElements = totalElements;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(long totalElements) {
        this.totalElements = totalElements;
    }

    @Override
    public String toString() {
        return "Pagination{" +
                "pageNumber=" + pageNumber +
                ", pageSize=" + pageSize +
                ", totalPages=" + totalPages +
                ", totalElements=" + totalElements +
                '}';
    }
}
