package com.ksga.springboot.employeeservice.model.cvupload;

public class CVUpdateUploadPublic {
    private boolean isPublic;

    public CVUpdateUploadPublic(boolean isPublic) {
        this.isPublic = isPublic;
    }

    public CVUpdateUploadPublic() {
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }

    @Override
    public String toString() {
        return "CVUpdatePublic{" +
                "isPublic=" + isPublic +
                '}';
    }
}
