package com.ksga.com.chat.model;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "tb_chats")
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int senderId;
    private String senderName;
    private int receiverId;
    private String receiverName;

    private String receiverUsername;

    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    private List<Message> message;

    private String roomNumber;
    private String dataImage;
    private Timestamp date;

    public Chat() {
    }

    public Chat(int id, int senderId, String senderName, int receiverId, String receiverName, String receiverUsername, List<Message> message, String roomNumber, String dataImage, Timestamp date) {
        this.id = id;
        this.senderId = senderId;
        this.senderName = senderName;
        this.receiverId = receiverId;
        this.receiverName = receiverName;
        this.receiverUsername = receiverUsername;
        this.message = message;
        this.roomNumber = roomNumber;
        this.dataImage = dataImage;
        this.date = date;
    }

    public String getReceiverUsername() {
        return receiverUsername;
    }

    public void setReceiverUsername(String receiverUsername) {
        this.receiverUsername = receiverUsername;
    }

    public String getDataImage() {
        return dataImage;
    }

    public void setDataImage(String dataImage) {
        this.dataImage = dataImage;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public int getId() {
        return id;
    }

    public int getSenderId() {
        return senderId;
    }

    public void setSenderId(int senderId) {
        this.senderId = senderId;
    }

    public int getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(int receiverId) {
        this.receiverId = receiverId;
    }

    public List<Message> getMessage() {
        return message;
    }

    public void setMessage(List<Message> message) {
        this.message = message;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }


    @Override
    public String toString() {
        return "Chat{" +
                "id=" + id +
                ", senderId=" + senderId +
                ", senderName='" + senderName + '\'' +
                ", receiverId=" + receiverId +
                ", receiverName='" + receiverName + '\'' +
                ", message=" + message +
                ", roomNumber='" + roomNumber + '\'' +
                ", dataImage='" + dataImage + '\'' +
                ", date=" + date +
                '}';
    }
}
