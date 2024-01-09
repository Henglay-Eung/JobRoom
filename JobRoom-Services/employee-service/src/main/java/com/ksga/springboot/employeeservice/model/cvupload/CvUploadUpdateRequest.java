package com.ksga.springboot.employeeservice.model.cvupload;

public class CvUploadUpdateRequest {
    private String name;
    private String file;

    public CvUploadUpdateRequest() {
    }

    public CvUploadUpdateRequest(String name, String file) {
        this.name = name;
        this.file = file;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    @Override
    public String toString() {
        return "CvUploadUpdateRequest{" +
                "name='" + name + '\'' +
                ", file='" + file + '\'' +
                '}';
    }
}
