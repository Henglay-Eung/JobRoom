package com.ksga.springboot.employeeservice.model.cv;

public class CVUpdatePublic {
    private boolean isPublic;

    public CVUpdatePublic(boolean isPublic) {
        this.isPublic = isPublic;
    }

    public CVUpdatePublic() {
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
