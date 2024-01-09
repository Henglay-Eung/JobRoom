package com.ksga.com.chat.model.responce;

import java.io.Serializable;

public class File implements Serializable {

    private String url;
    private String type;
    private String icon;

    public File() {
    }

    public File(String url, String type, String icon) {
        this.url = url;
        this.type = type;
        this.icon = icon;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "File{" +
                "url='" + url + '\'' +
                ", type='" + type + '\'' +
                ", icon='" + icon + '\'' +
                '}';
    }
}
