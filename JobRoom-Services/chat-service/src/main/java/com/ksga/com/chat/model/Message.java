package com.ksga.com.chat.model;

import com.ksga.com.chat.model.responce.File;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.Column;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
public class Message implements Serializable {
    private String text;
    private Date date;
    private boolean reply;
    private String type;

    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    private List<File> files;
    //private User user;
    private String user;

    public Message() {
    }

    public Message(String text, Date date, boolean reply, String type, List<File> files,String user) {
        this.text = text;
        this.date = date;
        this.reply = reply;
        this.type = type;
        this.files = files;
        this.user = user;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isReply() {
        return reply;
    }

    public void setReply(boolean reply) {
        this.reply = reply;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<File> getFiles() {
        return files;
    }

    public void setFiles(List<File> files) {
        this.files = files;
    }



    @Override
    public String toString() {
        return "Message{" +
                "text='" + text + '\'' +
                ", date=" + date +
                ", reply=" + reply +
                ", type='" + type + '\'' +
                ", files='" + files + '\'' +
                '}';
    }
}
